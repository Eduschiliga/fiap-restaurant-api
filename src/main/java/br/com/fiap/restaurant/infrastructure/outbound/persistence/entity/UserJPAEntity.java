package br.com.fiap.restaurant.infrastructure.outbound.persistence.entity;


import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;
import br.com.fiap.restaurant.application.domain.user.UserType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class UserJPAEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id", nullable = false, unique = true, length = 36)
    private String userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private AddressJPAEntity address;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public AddressJPAEntity getAddress() {
        return address;
    }

    public void setAddress(AddressJPAEntity address) {
        this.address = address;
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();

        setCreatedAt(now);
        setUpdatedAt(now);
    }

    public UserJPAEntity() {

    }

    public UserJPAEntity(
            String userId,
            String name,
            String email,
            String login,
            String password,
            AddressJPAEntity address,
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

    public static UserJPAEntity of(final User user) {
        return new UserJPAEntity(
                user.getUserId() != null ? user.getUserId().value() : null,
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                AddressJPAEntity.of(user.getAddress()),
                user.getUserType(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

    public User toUser() {
        return User.with(
                UserId.from(userId),
                name,
                email,
                login,
                password,
                address != null ? address.toAddress() : null,
                userType,
                createdAt,
                updatedAt
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserJPAEntity that = (UserJPAEntity) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    // Getters and Setters

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUserId() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
