package com.alesevero.forum.repository;

import com.alesevero.forum.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
}
