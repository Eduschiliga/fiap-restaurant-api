package br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.model.dto.update;

public record UpdatePasswordDTO(
        String newPassword,
        String oldPassword) {
}
