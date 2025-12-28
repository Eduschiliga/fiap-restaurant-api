package br.com.fiap.restaurant.application.ports.outbound.repository;

import br.com.fiap.restaurant.application.domain.pagination.Pagination;
import br.com.fiap.restaurant.application.domain.user.User;
import br.com.fiap.restaurant.application.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepositoryPort {
    boolean existsByEmail(String email);

    User create(User aUser);

    User update(User aUser);

    Optional<User> findById(UserId anId);

    List<User> findAllByName(String name);

    Pagination<User> find(int page, int size);

    void deleteById(UserId anId);

    Optional<User> findByLogin(String login);
}
