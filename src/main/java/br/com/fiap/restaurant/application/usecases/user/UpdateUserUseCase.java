package br.com.fiap.restaurant.application.usecases.user;

import br.com.fiap.restaurant.application.domain.user.address.Address;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.ports.inbound.user.update.UpdateUserInputPort;
import br.com.fiap.restaurant.application.ports.inbound.user.update.input.UpdateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.user.update.output.UpdateUserOutput;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

import java.util.Objects;

@Named
public class UpdateUserUseCase implements UpdateUserInputPort {

    private final UserRepositoryPort userRepositoryPort;
    private final FindUserUseCase findUserUseCase;

    @Inject
    public UpdateUserUseCase(final UserRepositoryPort userRepositoryPort, FindUserUseCase findUserUseCase) {
        this.userRepositoryPort = Objects.requireNonNull(userRepositoryPort);
        this.findUserUseCase = Objects.requireNonNull(findUserUseCase);
    }

    @Override
    @Transactional
    public UpdateUserOutput updateUser(UpdateUserInput input) {
        User user = findUserUseCase.findUserDomainById(input.userId());

        Address address;
        if (user.getAddress() == null) {
            address = Address.with(
                    null,
                    input.address().street(),
                    input.address().number(),
                    input.address().complement(),
                    input.address().city(),
                    input.address().state(),
                    input.address().zipCode(),
                    null,
                    null
            );
        } else {
            address = user.getAddress().update(
                    input.address().street(),
                    input.address().number(),
                    input.address().complement(),
                    input.address().city(),
                    input.address().state(),
                    input.address().zipCode()
            );
        }

        user.update(
                input.name(),
                input.email(),
                input.login(),
                address
        );

        user = userRepositoryPort.update(user);

        return UpdateUserOutput.from(user);
    }
}
