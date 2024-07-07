package br.com.fiap.postech.goodbuy.model;

import java.util.UUID;

public record Payment(
        UUID id,
        String method,
        Summary summary
) {

}
