package br.com.fiap.restaurant.application.domain.exceptions;

public class UserOrPasswordInvalidException extends RuntimeException {
    public UserOrPasswordInvalidException(String message) {
        super(message, null, true, false);
    }
}
