package br.com.fiap.postech.goodbuy.model;

import java.util.List;
import java.util.UUID;

public record Summary(
        UUID id,
        Double custoTotal,
        List<SummaryItem> itens
) {

}
