package com.pedromattos.encurtador.exception;

public class LinkExpiradoException extends RuntimeException {
    public LinkExpiradoException(String codigo) {
        super("Link expirado: " + codigo);
    }
}
