package br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.config;

import br.com.fiap.restauranteapi.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import br.com.fiap.restauranteapi.application.ports.outbound.token.TokenGateway;
import br.com.fiap.restauranteapi.application.service.AuthService;
import br.com.fiap.restauranteapi.application.service.UsersService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfig {

    @Bean
    public UsersService usersService(UserRepository userRepository, PasswordEncoderPort passwordEncoder) {
        return new UsersService(userRepository, passwordEncoder);
    }

    @Bean
    public AuthService authService(
            UserRepository userRepository,
            PasswordEncoderPort passwordEncoder,
            TokenGateway tokenGateway
    ) {
        return new AuthService(userRepository, passwordEncoder, tokenGateway);
    }
}
