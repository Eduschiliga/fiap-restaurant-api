package br.com.fiap.restauranteapi.application.service;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.ForAuthenticatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.LoginInput;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.LoginOutput;
import br.com.fiap.restauranteapi.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restauranteapi.application.ports.outbound.token.TokenGateway;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements ForAuthenticatingUser {
    private final UserRepository userRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenGateway tokenGateway;

    public AuthService(UserRepository userRepository,
                       PasswordEncoderPort passwordEncoder,
                       TokenGateway tokenGateway) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenGateway = tokenGateway;
    }

    @Override
    public LoginOutput login(LoginInput input) {
        User user = userRepository.findByLogin(input.login())
                .orElseThrow(() -> new IllegalArgumentException("Usuário ou senha inválidos"));

        // 2. Verifica a senha (usando a porta de saída)
        if (!passwordEncoder.matches(input.password(), user.getPassword())) {
            throw new IllegalArgumentException("Usuário ou senha inválidos");
        }

        // 3. Gera o token (usando a porta de saída)
        String token = tokenGateway.generate(user);

        return new LoginOutput(token);
    }

    @Override
    public User validateToken(String token) {
        // 1. Valida a assinatura do token e extrai o login (via Output Port)
        String login = tokenGateway.validate(token);

        if (login == null || login.isEmpty()) {
            return null;
        }

        // 2. Recupera o usuário do banco (via Output Port)
        return userRepository.findByLogin(login).orElse(null);
    }
}
