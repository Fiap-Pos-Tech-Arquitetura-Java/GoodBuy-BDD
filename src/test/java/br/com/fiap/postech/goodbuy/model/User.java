package br.com.fiap.postech.goodbuy.model;

import br.com.fiap.postech.goodbuy.security.enums.UserRole;

import java.util.UUID;

public record User(
        UUID id,
        String login,
        String name,
        String cpf,
        String password,
        UserRole role
) {
}
