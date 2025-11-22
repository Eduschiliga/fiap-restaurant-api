package br.com.fiap.restauranteapi.application.domain.user;

import java.util.UUID;

public record UserId(String value) {

    public static UserId from(final String value) {
        try {
            return new UserId(UUID.fromString(value).toString());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid category id");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
