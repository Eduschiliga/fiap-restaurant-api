package br.com.fiap.restauranteapi.application.ports.inbound.auth;

import br.com.fiap.restauranteapi.application.domain.user.User;

public interface ForAuthenticatingUser {
    LoginOutput login(LoginInput input);
}
