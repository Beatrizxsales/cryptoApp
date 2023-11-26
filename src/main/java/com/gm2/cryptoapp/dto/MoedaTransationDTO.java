package com.gm2.cryptoapp.dto;

import java.math.BigDecimal;

public class MoedaTransationDTO {
    private String nome;
    private BigDecimal quantidade;

    public MoedaTransationDTO(String nome, BigDecimal quantidade){
        this.nome = nome;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }
}
