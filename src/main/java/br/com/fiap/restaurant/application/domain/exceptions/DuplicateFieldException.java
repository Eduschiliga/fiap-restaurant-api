package br.com.fiap.restaurant.application.domain.exceptions;

public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(String message) {
        super(message, null, true, false);
    }
}
