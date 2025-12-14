package br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.security.service;

import br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.security.model.AddressDetails;
import br.com.fiap.restauranteapi.infrastructure.adapters.inbound.rest.security.model.UserDetailsImpl;
import br.com.fiap.restauranteapi.infrastructure.adapters.outbound.persistence.entity.UserJPAEntity;
import br.com.fiap.restauranteapi.infrastructure.adapters.outbound.persistence.repository.UserJPARepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserJPARepository userRepository;

    public SecurityUserDetailsService(UserJPARepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UserJPAEntity user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new UserDetailsImpl(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getLogin(),
                user.getPassword(),
                user.getAddress() != null ?
                new AddressDetails(
                        user.getAddress().getAddressId(),
                        user.getAddress().getStreet(),
                        user.getAddress().getNumber(),
                        user.getAddress().getComplement(),
                        user.getAddress().getCity(),
                        user.getAddress().getState(),
                        user.getAddress().getZipCode(),
                        user.getAddress().getActive(),
                        user.getAddress().getCreatedAt(),
                        user.getAddress().getUpdatedAt(),
                        user.getAddress().getDeletedAt()
                ) : null,
                user.getUserType(),
                user.getActive(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()

        );
    }
}