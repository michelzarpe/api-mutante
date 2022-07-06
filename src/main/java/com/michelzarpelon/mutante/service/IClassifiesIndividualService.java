package com.michelzarpelon.mutante.service;

public interface IClassifiesIndividualService {

    public boolean isMutant(String[] dna);

    public boolean hasFourIdenticalLettersVertically(String[] dna);

    public boolean hasFourIdenticalLettersHorizontally(String[] dna);

    public boolean containRepeatedCharacters(String dna);

    public boolean hasFourIdenticalLettersDiagonally(String[] dna);

    public String[][] arrayToTwoDimensionallyArray(String[] dna);

    public String arrayToString(String[] dna);


}
