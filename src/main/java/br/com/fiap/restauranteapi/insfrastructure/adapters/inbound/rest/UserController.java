package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest;

import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserInput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.CreateUserOutput;
import br.com.fiap.restauranteapi.application.ports.inbound.create.ForCreatingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.delete.ForDeletingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.get.ForGettingUserById;
import br.com.fiap.restauranteapi.application.ports.inbound.list.ForListingUser;
import br.com.fiap.restauranteapi.application.ports.inbound.update.ForUpdatingUser;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.mapper.UserMapper;
import br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping
@RestController("api/users")
public class UserController {
    private final ForCreatingUser forCreatingUser;
    private final ForUpdatingUser forUpdatingUser;
    private final ForDeletingUserById forDeletingUserById;
    private final ForListingUser forListingUser;
    private final ForGettingUserById forGettingUserById;
    private final UserMapper userMapper;

    public UserController(
            ForCreatingUser forCreatingUser,
            ForUpdatingUser forUpdatingUser,
            ForDeletingUserById forDeletingUserById,
            ForListingUser forListingUser,
            ForGettingUserById forGettingUserById,
            UserMapper userMapper
    ) {
        this.forCreatingUser = forCreatingUser;
        this.forUpdatingUser = forUpdatingUser;
        this.forDeletingUserById = forDeletingUserById;
        this.forListingUser = forListingUser;
        this.forGettingUserById = forGettingUserById;
        this.userMapper = userMapper;
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(
            @RequestBody UserDTO userDTO
    ) {
        CreateUserInput useCaseInput = userMapper.fromDTO(userDTO);
        CreateUserOutput useCaseOutput = forCreatingUser.create(useCaseInput);

        URI uri = URI.create("/users/" + useCaseOutput.userId());
        return ResponseEntity.created(uri).body(userMapper.toDTO(useCaseOutput));
    }
}
