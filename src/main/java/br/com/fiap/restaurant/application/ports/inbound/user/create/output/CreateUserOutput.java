package br.com.fiap.restaurant.application.ports.inbound.user.create.output;

import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;

import java.time.LocalDateTime;

public record CreateUserOutput(
        UserId userId,
        String name,
        String email,
        String login,
        String password,
        CreateAddressOutput address,
        UserType userType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static CreateUserOutput from(User user) {
        return new CreateUserOutput(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress() != null ?CreateAddressOutput.from(user.getAddress()) : null,
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
