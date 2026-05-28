package br.com.serratec.trabalhofinalapi.controller;

import br.com.serratec.trabalhofinalapi.handler.AutenticacaoException;

public final class AuthValidator {

    private static final String TOKEN_PREFIX = "Bearer ";

    private AuthValidator() {
    }

    public static void validarToken(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new AutenticacaoException("Header Authorization nao informado.");
        }

        if (!authorization.startsWith(TOKEN_PREFIX)) {
            throw new AutenticacaoException("Token invalido. Use o formato Bearer <token>.");
        }

        String token = authorization.substring(TOKEN_PREFIX.length()).trim();
        if (token.isEmpty()) {
            throw new AutenticacaoException("Token invalido.");
        }
    }
}
