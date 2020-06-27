package com.alesevero.forum.repository;

import com.alesevero.forum.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Cursos extends JpaRepository<Curso, Long> {
    Curso findByNome(String nomeCurso);
}
