package br.com.fiap.restauranteapi.application.ports.inbound.auth;

public record LoginInput(String login, String password) {}