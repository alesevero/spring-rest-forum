package com.alesevero.forum.config.dto;

public class ErroDeFormulario {

    private String campo;
    private String mensagem;

    public ErroDeFormulario(String campo, String mensagem) {
        this.campo = campo;
        this.mensagem = mensagem;
    }

    public String getCampo() {
        return campo;
    }

    public String getMensagem() {
        return mensagem;
    }
}
