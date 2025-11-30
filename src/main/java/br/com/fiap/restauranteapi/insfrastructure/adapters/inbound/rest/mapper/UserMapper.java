package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.mapper;

import br.com.fiap.restauranteapi.application.domain.address.AddressId;
import br.com.fiap.restauranteapi.application.domain.user.UserId;
import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserOutput;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(CreateUserOutput output);

    CreateUserInput fromDTO(UserDTO dto);

    default String map(UserId value) {
        return value != null ? value.toString() : null;
    }

    default UserId mapUserId(String value) {
        return value != null ? UserId.from(value) : null;
    }

    default String map(AddressId value) {
        return value != null ? value.toString() : null;
    }

    default AddressId mapAddressId(String value) {
        return value != null ? AddressId.from(value) : null;
    }
}
