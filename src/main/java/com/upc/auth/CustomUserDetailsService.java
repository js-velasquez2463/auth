package com.upc.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Aquí inyectarías tu repositorio o servicio de acceso a datos (por ejemplo, UserRepository)

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO: Aquí implementarías la lógica para buscar el usuario por su nombre de usuario
        CustomUserDetails user = new CustomUserDetails("user", "password", true, null);

        if (user == null) {
            throw new UsernameNotFoundException("No se encontró el usuario con el nombre de usuario: " + username);
        }

        // Aquí convertirías el usuario a un objeto que implemente UserDetails
        return user;
    }
}
