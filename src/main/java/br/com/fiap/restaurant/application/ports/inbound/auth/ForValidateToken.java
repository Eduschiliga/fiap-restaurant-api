package br.com.fiap.restaurant.application.ports.inbound.auth;

public interface ForValidateToken {
    void validateToken(String token);
}
