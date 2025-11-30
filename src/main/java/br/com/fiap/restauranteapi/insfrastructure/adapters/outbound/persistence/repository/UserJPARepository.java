package br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.repository;

import br.com.fiap.restauranteapi.insfrastructure.adapters.outbound.persistence.entity.UserJPAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<UserJPAEntity, String> {
}
