package br.com.fiap.restaurant.application.ports.inbound.user.password;

import br.com.fiap.restaurant.application.ports.inbound.user.password.input.UpdatePasswordInput;
import br.com.fiap.restaurant.application.ports.inbound.user.password.output.UpdatePasswordOutput;

public interface ForUpdatingPassword {
    UpdatePasswordOutput updatePassword(UpdatePasswordInput input);
    void validateNewPassword(UpdatePasswordInput input, String actualPassword);
}
