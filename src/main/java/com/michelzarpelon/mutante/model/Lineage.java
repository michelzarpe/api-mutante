package com.michelzarpelon.mutante.model;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity(name = "lineage")
public class Lineage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty
    private String dna;

    private Boolean isMutant;

    public Lineage() {
    }

    public Lineage(Long id, String dna, Boolean isMutant) {
        this.id = id;
        this.dna = dna;
        this.isMutant = isMutant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDna() {
        return dna;
    }

    public void setDna(String dna) {
        this.dna = dna;
    }

    public Boolean getMutant() {
        return isMutant;
    }

    public void setMutant(Boolean mutant) {
        isMutant = mutant;
    }

    @Override
    public String toString() {
        return "Lineage{" +
                "id=" + id +
                ", dna='" + dna + '\'' +
                ", isMutant=" + isMutant +
                '}';
    }
}
