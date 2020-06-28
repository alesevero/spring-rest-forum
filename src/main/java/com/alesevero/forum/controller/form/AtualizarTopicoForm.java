package com.alesevero.forum.controller.form;

import com.alesevero.forum.model.Topico;

public class AtualizarTopicoForm {
    private String titulo;
    private String mensagem;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Topico atualizar(Topico topico) {
        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);
        return topico;
    }

}
