package br.com.fiap.restaurant.application.ports.inbound.user.update.input;

import br.com.fiap.restaurant.application.domain.user.UserType;

public record UpdateUserInput(
        String userId,
        String name,
        String login,
        String email,
        UpdateAddressInput address,
        UserType userType
) {

}
