package com.alesevero.forum.repository;

import com.alesevero.forum.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface Topicos extends JpaRepository<Topico, Long> {
    List<Topico> findByCursoNome(String nomeCurso);
}
