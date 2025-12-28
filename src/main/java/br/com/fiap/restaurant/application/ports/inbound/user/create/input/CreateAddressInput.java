package br.com.fiap.restaurant.application.ports.inbound.user.create.input;

public record CreateAddressInput(
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode
) {
}
