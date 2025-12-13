package br.com.fiap.restauranteapi.application.ports.inbound.password;

import br.com.fiap.restauranteapi.application.domain.address.Address;
import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.domain.user.UserType;

import java.time.LocalDateTime;

public record UpdatePasswordOutput(
        UserId userId,
        String name,
        String login,
        String email,
        String password,
        Address address,
        Boolean active,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime deletedAt
) {
    public static UpdatePasswordOutput from(User user) {
        return new UpdatePasswordOutput(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress(),
                user.getActive(),
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }

}