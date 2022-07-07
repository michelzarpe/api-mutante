package com.michelzarpelon.mutante.config.exception;

public class CalculateException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    public CalculateException(String mensagem) {
        super(mensagem);
    }

    public CalculateException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
