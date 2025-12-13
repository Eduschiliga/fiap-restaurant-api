package br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest;

import br.com.fiap.restauranteapi.application.ports.inbound.auth.ForAuthenticatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.LoginInput;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.LoginOutput;
import br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.model.dto.login.LoginDTO;
import br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.model.dto.login.TokenDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    private final ForAuthenticatingUser forAuthenticatingUser;

    public AuthController(ForAuthenticatingUser forAuthenticatingUser) {
        this.forAuthenticatingUser = forAuthenticatingUser;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginDTO data) {
        // 1. Converte DTO do REST para Input do Domínio
        LoginInput input = new LoginInput(data.login(), data.password());

        // 2. Chama o Caso de Uso
        LoginOutput output = forAuthenticatingUser.login(input);

        // 3. Converte Output do Domínio para DTO do REST
        return ResponseEntity.ok(new TokenDTO(output.token()));
    }
}
