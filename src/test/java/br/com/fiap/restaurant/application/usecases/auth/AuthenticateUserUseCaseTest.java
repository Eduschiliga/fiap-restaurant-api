package br.com.fiap.restaurant.application.usecases.auth;

import br.com.fiap.restaurant.application.domain.exceptions.TokenInvalidException;
import br.com.fiap.restaurant.application.domain.exceptions.UserNotFoundException;
import br.com.fiap.restaurant.application.domain.exceptions.UserOrPasswordInvalidException;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;
import br.com.fiap.restaurant.application.ports.inbound.auth.input.AuthenticateUserInput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.AuthenticateUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.auth.output.GetUserByTokenOutput;
import br.com.fiap.restaurant.application.ports.outbound.password.PasswordEncoderPort;
import br.com.fiap.restaurant.application.ports.outbound.repository.UserRepositoryPort;
import br.com.fiap.restaurant.application.ports.outbound.token.TokenGeneratorPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticateUserUseCaseTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private PasswordEncoderPort passwordEncoder;
    @Mock
    private TokenGeneratorPort tokenGeneratorPort;

    @InjectMocks
    private AuthenticateUserUseCase authenticateUserUseCase;

    @Test
    void shouldLoginSuccessfully() {
        AuthenticateUserInput input = new AuthenticateUserInput("john", "123456");
        User user = User.with(new UserId("1"), "John", "john@test.com", "john", "encoded", null, UserType.CLIENT, true, null, null, null);

        when(userRepositoryPort.findByLogin(input.login())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(input.password(), user.getPassword())).thenReturn(true);
        when(tokenGeneratorPort.generate(user)).thenReturn("valid_token");

        AuthenticateUserOutput output = authenticateUserUseCase.login(input);

        assertNotNull(output);
        assertEquals("valid_token", output.token());
    }

    @Test
    void shouldThrowExceptionWhenPasswordInvalid() {
        AuthenticateUserInput input = new AuthenticateUserInput("john", "wrongpass");
        User user = User.with(new UserId("1"), "John", "john@test.com", "john", "encoded", null, UserType.CLIENT, true, null, null, null);

        when(userRepositoryPort.findByLogin(input.login())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(input.password(), user.getPassword())).thenReturn(false);

        assertThrows(UserOrPasswordInvalidException.class, () -> authenticateUserUseCase.login(input));
    }

    @Test
    void shouldGetUserByTokenSuccessfully() {
        String token = "valid";
        String login = "john";
        User user = User.with(new UserId("1"), "John", "john@test.com", login, "encoded", null, UserType.CLIENT, true, null, null, null);

        when(tokenGeneratorPort.getSubjectByToken(token)).thenReturn(login);
        when(userRepositoryPort.findByLogin(login)).thenReturn(Optional.of(user));

        GetUserByTokenOutput output = authenticateUserUseCase.getUserByToken(token);

        assertNotNull(output);
        assertEquals(login, output.login());
    }

    @Test
    void shouldThrowExceptionWhenTokenIsInvalid() {
        String token = "invalid";
        when(tokenGeneratorPort.getSubjectByToken(token)).thenReturn(null);

        assertThrows(TokenInvalidException.class, () -> authenticateUserUseCase.getUserByToken(token));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundDuringLogin() {
        AuthenticateUserInput input = new AuthenticateUserInput("nonexistent", "123456");

        when(userRepositoryPort.findByLogin(input.login())).thenReturn(Optional.empty());

        assertThrows(UserOrPasswordInvalidException.class, () -> authenticateUserUseCase.login(input));
    }

    @Test
    void shouldThrowExceptionWhenTokenSubjectIsInvalid() {
        String token = "invalid_token";
        when(tokenGeneratorPort.getSubjectByToken(token)).thenReturn(null);

        assertThrows(TokenInvalidException.class, () -> authenticateUserUseCase.getUserByToken(token));
    }

    @Test
    void shouldThrowExceptionWhenTokenSubjectIsEmpty() {
        String token = "empty_subject_token";
        when(tokenGeneratorPort.getSubjectByToken(token)).thenReturn("");

        assertThrows(TokenInvalidException.class, () -> authenticateUserUseCase.getUserByToken(token));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFoundByToken() {
        String token = "valid_token";
        String login = "john";

        when(tokenGeneratorPort.getSubjectByToken(token)).thenReturn(login);
        when(userRepositoryPort.findByLogin(login)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authenticateUserUseCase.getUserByToken(token));
    }
}