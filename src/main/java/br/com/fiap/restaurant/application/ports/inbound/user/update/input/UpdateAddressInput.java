package br.com.fiap.restaurant.application.ports.inbound.user.update.input;

import br.com.fiap.restaurant.application.domain.user.address.AddressId;

public record UpdateAddressInput(
        AddressId addressId,
        String street,
        String number,
        String complement,
        String city,
        String state,
        String zipCode
) {
}
