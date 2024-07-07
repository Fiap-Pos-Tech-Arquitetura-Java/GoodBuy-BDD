package br.com.fiap.postech.goodbuy.model;

import java.util.UUID;

public record Item(

    UUID id,
    String nome,
    Double preco,
    String descricao,
    String categoria,
    String urlImagem,
    Long quantidade
) {
}
