package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.update;

public record UpdatePasswordDTO(
        String newPassword,
        String oldPassword) {
}
