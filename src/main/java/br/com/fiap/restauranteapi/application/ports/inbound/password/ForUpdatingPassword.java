package br.com.fiap.restauranteapi.application.ports.inbound.password;

public interface ForUpdatingPassword {
    UpdatePasswordOutput updatePassword(UpdatePasswordInput input);
}
