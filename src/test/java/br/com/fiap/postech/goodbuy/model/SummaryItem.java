package br.com.fiap.postech.goodbuy.model;

import java.util.UUID;

public record SummaryItem(
        UUID id,
        String nome,
        Long quantidade,
        Double preco,
        Double valorTotal
) {

}
