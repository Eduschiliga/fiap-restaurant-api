package br.com.fiap.restauranteapi.application.ports.inbound.password;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.ports.inbound.auth.GetUserByTokenOutput;

public record UpdatePasswordInput(
        GetUserByTokenOutput user,
        String newPassword,
        String oldPassword
) {
}
