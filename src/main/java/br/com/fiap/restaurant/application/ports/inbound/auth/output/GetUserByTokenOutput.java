package br.com.fiap.restaurant.application.ports.inbound.auth.output;

import br.com.fiap.restaurant.application.domain.user.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;
import br.com.fiap.restaurant.application.ports.inbound.user.get.output.GetAddressOutput;

import java.time.LocalDateTime;

public record GetUserByTokenOutput(
        UserId userId,
        String name,
        String email,
        String login,
        String password,
        GetAddressOutput address,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static GetUserByTokenOutput from(User user) {
        Address address = user.getAddress();
        return new GetUserByTokenOutput(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                address != null ? new GetAddressOutput(
                        address.getAddressId(),
                        address.getStreet(),
                        address.getNumber(),
                        address.getComplement(),
                        address.getCity(),
                        address.getState(),
                        address.getZipCode(),
                        address.getCreatedAt(),
                        address.getUpdatedAt()
                ) : null,
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
