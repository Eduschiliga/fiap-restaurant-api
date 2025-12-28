package br.com.fiap.restaurant.application.ports.inbound.user.list;

import br.com.fiap.restaurant.application.domain.pagination.Pagination;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUserOutput;
import br.com.fiap.restaurant.application.ports.inbound.user.list.output.ListUsersByNameOutput;

import java.util.List;

public interface ForListingUsersByName {
    Pagination<ListUserOutput> findAllByName(final Integer page, final Integer perPage, String name);
}
