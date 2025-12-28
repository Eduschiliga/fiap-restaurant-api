package br.com.fiap.restaurant.application.ports.inbound.user.list;

import br.com.fiap.restaurant.application.domain.pagination.Pagination;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;

public interface ForListingUser {

    Pagination<ListUserOutput> listUsers(final Integer page, final Integer perPage);

}
