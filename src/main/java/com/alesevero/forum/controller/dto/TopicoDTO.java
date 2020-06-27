package com.alesevero.forum.controller.dto;

import com.alesevero.forum.model.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicDTO {

    private Long id;
    private String title;
    private String message;
    private LocalDateTime createdAt;

    public TopicDTO(Topico topico) {
        this.id = topico.getId();
        this.title = topico.getTitulo();
        this.message = topico.getMensagem();
        this.createdAt = topico.getDataCriacao();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static List<TopicDTO> convert(List<Topico> topicos) {
        return topicos.stream().map(TopicDTO::new).collect(Collectors.toList());
    }
}
