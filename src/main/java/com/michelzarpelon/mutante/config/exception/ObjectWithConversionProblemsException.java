package com.michelzarpelon.mutante.config.exception;

public class ObjectWithConversionProblemsException extends RuntimeException {
    private static final long serialVersionUID = 1L;


    public ObjectWithConversionProblemsException(String mensagem) {
        super(mensagem);
    }

    public ObjectWithConversionProblemsException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
