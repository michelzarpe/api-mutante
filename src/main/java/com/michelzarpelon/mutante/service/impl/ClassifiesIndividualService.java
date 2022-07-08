package com.michelzarpelon.mutante.service.impl;

import com.michelzarpelon.mutante.config.exception.DataIntegrityException;
import com.michelzarpelon.mutante.config.exception.ObjectWithConversionProblemsException;
import com.michelzarpelon.mutante.enums.Base;
import com.michelzarpelon.mutante.repository.ILineageRepository;
import com.michelzarpelon.mutante.model.Lineage;
import com.michelzarpelon.mutante.service.IClassifiesIndividualService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassifiesIndividualService implements IClassifiesIndividualService {

    private static final Logger LOGGER = Logger.getLogger(ClassifiesIndividualService.class.getName());

    @Autowired
    private ILineageRepository iLineageRepository;

    @Override
    public boolean isMutant(String[] dna) {
        try {

            LOGGER.info("Processando objeto em isMutant [" + dna + "]");

            hasNitrogenBase(dna);

            isAPossibleSquareMatrix(dna);

            boolean isMutant = hasFourIdenticalLettersHorizontally(dna) || hasFourIdenticalLettersVertically(dna) || hasFourIdenticalLettersDiagonally(dna);

            save(new Lineage(null, arrayToString(dna), isMutant));

            return isMutant;
        } catch (DataIntegrityException e) {
            LOGGER.severe("Erro ao salvar objeto [" + dna + "]");
            throw new ObjectWithConversionProblemsException("Não foi possível salvar o objeto");
        } catch (Exception e) {
            LOGGER.severe("Erro ao processar objeto [" + dna + "]");
            throw new ObjectWithConversionProblemsException("Não foi possível processar objeto");
        }
    }

    @Override
    public boolean hasFourIdenticalLettersVertically(String[] dna) {
        LOGGER.info("Processando objeto [" + dna + "] em hasFourIdenticalLettersVertically");
        String dnaColumn = "";
        final String matriz[][] = arrayToTwoDimensionallyArray(dna);
        final Integer height = matriz.length;
        final Integer width = matriz[0].length;

        for (int column = 0; column < width; column++) {
            for (int line = 0; line < height; line++) {
                dnaColumn = dnaColumn + matriz[line][column];
            }
            if (containRepeatedCharacters(dnaColumn)) {
                return true;
            }
            dnaColumn = "";
        }
        return false;
    }

    @Override
    public boolean hasFourIdenticalLettersHorizontally(String[] dna) {
        LOGGER.info("Processando objeto [" + dna + "] em hasFourIdenticalLettersHorizontally");
        return containRepeatedCharacters(dna);
    }

    @Override
    public boolean containRepeatedCharacters(String dna) {
        LOGGER.info("Processando objeto [" + dna + "] em containRepeatedCharacters");
        //TODO FAZER EM REGEX
        return dna.toUpperCase(Locale.ROOT).contains(Base.AAAA.name()) ||
                dna.contains(Base.TTTT.name()) ||
                dna.contains(Base.CCCC.name()) ||
                dna.contains(Base.GGGG.name());
    }

    public boolean containRepeatedCharacters(String[] dna) {
        LOGGER.info("Processando objeto [" + dna + "] em containRepeatedCharacters");
        for (String item : dna) {
            if (containRepeatedCharacters(item))
                return true;
        }
        return false;
    }

    @Override
    public boolean hasFourIdenticalLettersDiagonally(String[] dna) {
        LOGGER.info("Processando objeto [" + dna + "] em hasFourIdenticalLettersDiagonally");
        String dnaDiagonally = "";
        final String matriz[][] = arrayToTwoDimensionallyArray(dna);
        final Integer height = dna.length;
        final Integer width = dna[0].length();
        final Integer diagonalAmout = (height + width - 1);

        //https://www.geeksforgeeks.org/zigzag-or-diagonal-traversal-of-matrix/
        for (int diagonalLine = 1; diagonalLine <= diagonalAmout; diagonalLine++) {

            int start_col = Math.max(0, diagonalLine - height);

            int totDiagonalElements1 = Math.min(Math.min(diagonalLine, (width - start_col)), height);

            for (int j = 0; j < totDiagonalElements1; j++) {
                dnaDiagonally = dnaDiagonally + matriz[Math.min(height, diagonalLine) - j - 1][start_col + j];
            }

            if (containRepeatedCharacters(dnaDiagonally))
                return true;

            dnaDiagonally = "";
        }
        return false;
    }

    @Override
    public String[][] arrayToTwoDimensionallyArray(String[] dna) {
        LOGGER.info("Convertendo objeto [" + dna + "] em arrayToTwoDimensionallyArray");
        Integer position = 0;
        final Integer height = dna.length;
        final Integer width = dna[0].length();
        final String concatDna = arrayToString(dna);

        String[][] matrizDna = new String[height][width];

        for (int line = 0; line < height; line++) {
            for (int column = 0; column < width; column++) {
                matrizDna[line][column] = String.valueOf(concatDna.charAt(position));
                position++;
            }
        }
        return matrizDna;
    }

    @Override
    public String arrayToString(String[] dna) {
        return String.join("", dna).toUpperCase(Locale.ROOT);
    }

    @Override
    public void save(Lineage lineage) {
        try {
            iLineageRepository.save(lineage);
        } catch (Exception e) {
            LOGGER.severe("Erro ao salvar objeto [" + lineage + "]");
            throw new DataIntegrityException("Não foi possível salvar o objeto " + e.getMessage());
        }

    }

    @Override
    public boolean hasNitrogenBase(String[] dna) {

        LOGGER.info("Tem base nitrogenada [" + dna + " hasNitrogenBase");

        String concatDna = arrayToString(dna);

        concatDna = concatDna
                .replace("A", "")
                .replace("T", "")
                .replace("C", "")
                .replace("G", "");

        if (concatDna.length() > 0) {
            LOGGER.info("Caracteres não validos [" + concatDna + " hasNitrogenBase");
            throw new ObjectWithConversionProblemsException("Caracteres não validos");
        }

        return true;
    }

    @Override
    public boolean isAPossibleSquareMatrix(String[] dna) {

        var lengthItensList = new ArrayList<Integer>();

        var dnaList = Arrays.stream(dna).toList();

        dnaList.forEach(s -> {lengthItensList.add(s.length());});

        var distinctElements = lengthItensList.stream().distinct();

        var countDistinctElements = distinctElements.count();

        if (countDistinctElements > 1)
            throw new ObjectWithConversionProblemsException("Tamanho dos indices não compativeis");

        var elementFirst = lengthItensList.stream().findFirst();

        if (elementFirst.get() != dna.length)
            throw new ObjectWithConversionProblemsException("Não é uma matrix quadrada");

        return true;
    }
}
