package br.com.fiap.restauranteapi.application.ports.inbound.auth;

public interface ForGettingUserByToken {

    GetUserByTokenOutput getUserByToken(String token);
}
