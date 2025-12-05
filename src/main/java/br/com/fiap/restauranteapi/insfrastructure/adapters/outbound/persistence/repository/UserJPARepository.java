package br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.repository;

import br.com.fiap.restauranteapi.application.domain.user.User;
import br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, String> {
    Optional<UserJPAEntity> findByLogin(String login);
}
