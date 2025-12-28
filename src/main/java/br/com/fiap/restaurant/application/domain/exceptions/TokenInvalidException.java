package br.com.fiap.restaurant.application.domain.exceptions;

public class TokenInvalidException extends RuntimeException {
    public TokenInvalidException(String message) {
        super(message, null, true, false);
    }
}
