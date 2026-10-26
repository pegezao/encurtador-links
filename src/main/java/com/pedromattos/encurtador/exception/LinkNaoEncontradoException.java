package com.pedromattos.encurtador.exception;

public class LinkNaoEncontradoException extends RuntimeException {
    public LinkNaoEncontradoException(String codigo) {
        super("Link nao encontrado: " + codigo);
    }
}
