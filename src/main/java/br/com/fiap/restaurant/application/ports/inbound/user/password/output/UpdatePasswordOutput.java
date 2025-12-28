package br.com.fiap.restaurant.application.ports.inbound.user.password.output;

import br.com.fiap.restaurant.application.domain.user.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;

import java.time.LocalDateTime;

public record UpdatePasswordOutput(
        UserId userId,
        String name,
        String login,
        String email,
        String password,
        Address address,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static UpdatePasswordOutput from(User user) {
        return new UpdatePasswordOutput(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress(),
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}