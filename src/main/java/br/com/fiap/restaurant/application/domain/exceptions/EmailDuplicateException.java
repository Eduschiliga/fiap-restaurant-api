package br.com.fiap.restaurant.application.domain.exceptions;

public class EmailDuplicateException extends RuntimeException {
    public EmailDuplicateException(String message) {
        super(message, null, true, false);
    }
}
