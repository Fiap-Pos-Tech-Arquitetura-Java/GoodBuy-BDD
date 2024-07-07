package br.com.fiap.postech.goodbuy.model;

import java.util.List;
import java.util.UUID;

public record ShopCart(
        UUID id,
        List<Item> itens
) {

}
