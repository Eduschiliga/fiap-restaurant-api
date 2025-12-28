package br.com.fiap.restaurant.application.ports.inbound.user.create.input;


import br.com.fiap.restaurant.application.domain.user.UserType;

public record CreateUserInput(
        String name,
        String login,
        String email,
        String password,
        CreateAddressInput address,
        UserType userType
) {
}
