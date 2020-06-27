package com.alesevero.forum.controller;

import com.alesevero.forum.controller.dto.TopicoDTO;
import com.alesevero.forum.controller.form.TopicoForm;
import com.alesevero.forum.model.Curso;
import com.alesevero.forum.model.Topico;
import com.alesevero.forum.repository.Cursos;
import com.alesevero.forum.repository.Topicos;
import jdk.jfr.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private Topicos topicos;
    private Cursos cursos;

    @Autowired
    public TopicoController(Topicos topicos, Cursos cursos) {
        this.topicos = topicos;
        this.cursos = cursos;
    }

    @GetMapping(value = "/", produces = "application/json;charset=UTF-8")
    public List<TopicoDTO> list(String nomeCurso) {
        return nomeCurso != null
                ? TopicoDTO.convert(topicos.findByCursoNome(nomeCurso))
                : TopicoDTO.convert(topicos.findAll());
    }

    @PostMapping(value = "/", consumes = "application/json;charset=UTF-8")
    public ResponseEntity cadastrar(@RequestBody @Valid TopicoForm topicoForm,
                                    UriComponentsBuilder uriBuilder) {
        Topico topico = topicos.save(topicoForm.converter(cursos));
        return ResponseEntity.created(
                uriBuilder
                        .path("/topicos/{id}")
                        .buildAndExpand(topico.getId())
                        .toUri())
                .body(new TopicoDTO(topico));
    }
}
