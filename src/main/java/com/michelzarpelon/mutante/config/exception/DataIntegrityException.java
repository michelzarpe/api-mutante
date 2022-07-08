package com.michelzarpelon.mutante.config.exception;

public class DataIntegrityException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataIntegrityException(String mensagem) {
        super(mensagem);
    }
}
