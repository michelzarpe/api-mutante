package com.michelzarpelon.mutante.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class DNADto implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Informar DNA")
    @NotEmpty(message = "Informar DNA")
    private String[] dna;

    public DNADto() {
    }

    public DNADto(String[] dna) {
        this.dna = dna;
    }

    public String[] getDna() {
        return dna;
    }

    public void setDna(String[] dna) {
        this.dna = dna;
    }
}
