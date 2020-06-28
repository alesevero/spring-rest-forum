package com.alesevero.forum.config.security;

import com.alesevero.forum.repository.Usuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    private Usuarios usuarios;

    @Autowired
    public AutenticacaoService(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return usuarios.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "usuário não existe"));
    }

}
