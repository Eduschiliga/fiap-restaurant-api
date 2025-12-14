package br.com.fiap.restauranteapi.application.ports.inbound.get;

import br.com.fiap.restauranteapi.application.domain.user.User;

public interface ForGettingUserDomainById {
    User findUserDomainById(String userId);
}
