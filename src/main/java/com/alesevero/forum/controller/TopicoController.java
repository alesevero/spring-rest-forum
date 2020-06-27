package com.alesevero.forum.controller;

import com.alesevero.forum.controller.dto.TopicDTO;
import com.alesevero.forum.model.Curso;
import com.alesevero.forum.model.Topico;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class Topics {

    @GetMapping("/topics")
    public List<TopicDTO> list() {
        return TopicDTO.convert(
                Arrays.asList(new Topico("Dúvida", "Dúvida",
                        new Curso("Spring", "Programação"))));
    }
}
