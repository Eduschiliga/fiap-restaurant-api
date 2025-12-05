package br.com.fiap.restauranteapi.insfrastructure.adapters.inbound.rest.security.service;

import br.com.fiap.restauranteapi.application.ports.outbound.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public SecurityUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User Login not found"));

        // Adaptamos o User do domínio para o User do Spring Security
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                new ArrayList<>() // Aqui iriam as Roles/Authorities
        );
    }
}
