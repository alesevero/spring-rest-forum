package com.alesevero.forum.controller;

import com.alesevero.forum.controller.dto.DetalhesDoTopicoDTO;
import com.alesevero.forum.controller.dto.TopicoDTO;
import com.alesevero.forum.controller.form.AtualizarTopicoForm;
import com.alesevero.forum.controller.form.TopicoForm;
import com.alesevero.forum.model.Topico;
import com.alesevero.forum.repository.Cursos;
import com.alesevero.forum.repository.Topicos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

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
    @Cacheable(value = "lista-de-topicos")
    public Page<TopicoDTO> list
            (@RequestParam(required = false) String nomeCurso,
             @PageableDefault(sort = "id",
                     direction = Sort.Direction.ASC,
                     page = 0,
                     size = 5) Pageable paginacao) {
        return nomeCurso != null
                ? TopicoDTO
                .converter(topicos.findByCursoNome(nomeCurso, paginacao))
                : TopicoDTO.converter(topicos.findAll(paginacao));
    }

    @PostMapping(value = "/", consumes = "application/json;charset=UTF-8")
    @Transactional
    @CacheEvict(value = "lista-de-topicos", allEntries = true)
    public ResponseEntity cadastrar
            (@RequestBody @Valid TopicoForm topicoForm,
             UriComponentsBuilder uriBuilder) {
        Topico topico = topicos.save(topicoForm.converter(cursos));
        return ResponseEntity.created(
                uriBuilder
                        .path("/topicos/{id}")
                        .buildAndExpand(topico.getId())
                        .toUri())
                .body(new TopicoDTO(topico));
    }

    @GetMapping(value = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity detalhar(@PathVariable Long id) {
        return topicos.findById(id)
                .map(DetalhesDoTopicoDTO::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(value = "/{id}")
    @Transactional
    @CacheEvict(value = "lista-de-topicos", allEntries = true)
    public ResponseEntity atualizar
            (@PathVariable Long id,
             @RequestBody AtualizarTopicoForm form) {
        return topicos.findById(id)
                .map(form::atualizar)
                .map(TopicoDTO::new)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "lista-de-topicos", allEntries = true)
    public ResponseEntity remover(@PathVariable Long id) {
        topicos.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
