package br.com.fiap.restaurant.application.domain.user;

import br.com.fiap.restaurant.application.domain.user.address.Address;

import java.time.LocalDateTime;

public class User {
    private UserId userId;
    private String name;
    private String email;
    private String login;
    private String password;
    private Address address;
    private UserType userType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User(
            UserId userId,
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.address = address;
        this.userType = userType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User with(
            UserId userId,
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        return new User(
                userId,
                name,
                email,
                login,
                password,
                address,
                userType,
                createdAt,
                updatedAt
        );
    }

    public static User newUser(
            String name,
            String email,
            String login,
            String password,
            Address address,
            UserType userType
    ) {
        LocalDateTime now = LocalDateTime.now();

        return new User(
                new UserId(null),
                name,
                email,
                login,
                password,
                address,
                userType,
                now,
                now
        );
    }

    public User update(String name, String email, String login, String street, String number, String complement, String city, String state, String zipCode) {
        this.name = name;
        this.email = email;
        this.login = login;
        this.updatedAt = LocalDateTime.now();

        if (this.address == null) {
            this.address = Address.with(null, street, number, complement, city, state, zipCode, null, null);
        } else {
            this.address.update(street, number, complement, city, state, zipCode);
        }
        return this;
    }

    public User updatePassword(String password) {
        this.password = password;
        this.updatedAt = LocalDateTime.now();
        return this;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Address getAddress() {
        return address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserType getUserType() {
        return userType;
    }
}
