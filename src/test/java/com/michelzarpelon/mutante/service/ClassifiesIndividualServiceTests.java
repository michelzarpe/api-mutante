package com.michelzarpelon.mutante.service;

import com.michelzarpelon.mutante.config.exception.DataIntegrityException;
import com.michelzarpelon.mutante.config.exception.ObjectWithConversionProblemsException;
import com.michelzarpelon.mutante.repositories.ILineageRepository;
import com.michelzarpelon.mutante.model.Lineage;
import com.michelzarpelon.mutante.service.impl.ClassifiesIndividualService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class ClassifiesIndividualServiceTests {

    @InjectMocks
    ClassifiesIndividualService classifiesIndividualService;

    @MockBean
    ILineageRepository iLineageRepository;

    @BeforeAll
    void setUp() {
        classifiesIndividualService = new ClassifiesIndividualService();
    }

    @DisplayName("convert array to string")
    @Test
    void arrayToString() {
        String[] dnaMutant = {"CTGAGA", "CTATGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
        String result = "CTGAGACTATGCTATTGTAGAGGGCCCCTATCACTG";
        when(iLineageRepository.save(any())).thenReturn(new Lineage());
        assertEquals(result, classifiesIndividualService.arrayToString(dnaMutant));
    }

    @DisplayName("is not mutant")
    @Test
    void isNotMutant() {
        String[] dnaMutant = {"CTGATA", "CTTTGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
        when(iLineageRepository.save(any())).thenReturn(new Lineage());
        assertEquals(false, classifiesIndividualService.isMutant(dnaMutant));
    }

    @DisplayName("is mutant with exception")
    @Test()
    void isMutanteWithException() {
        String[] dnaMutant = null;
        ObjectWithConversionProblemsException thrown =
                assertThrows(ObjectWithConversionProblemsException.class,
                        () -> classifiesIndividualService.isMutant(dnaMutant),
                        "Não foi possível processar objeto");
        assertTrue(thrown.getMessage().contains("Não foi possível processar objeto"));
    }

    @DisplayName("is mutant with DataIntegrityException")
    @Test()
    void isMutanteWithDataIntegrityException() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};

        when(iLineageRepository.save(any())).thenThrow(new DataIntegrityException("Não foi possível salvar o objeto"));

        ObjectWithConversionProblemsException thrown =
                assertThrows(ObjectWithConversionProblemsException.class,
                        () -> classifiesIndividualService.isMutant(dnaMutant),
                        "Não foi possível salvar o objeto");
        assertTrue(thrown.getMessage().contains("Não foi possível salvar o objeto"));
    }

    @DisplayName("is mutant by diagonal")
    @Test
    void isMutantByDiagonal() {
        String[] dnaMutant = {"CTGATA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
        when(iLineageRepository.save(any())).thenReturn(new Lineage());
        assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
    }

    @DisplayName("is mutant by horizontal")
    @Test
    void isMutantByHorizontal() {
        String[] dnaMutant = {"CTGTTA", "CTATGC", "TATTGT", "AGATGG", "CCCCTA", "TCACTG"};
        when(iLineageRepository.save(any())).thenReturn(new Lineage());
        assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
    }

    @DisplayName("is mutant by Vertical")
    @Test
    void isMutantByVertical() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
        when(iLineageRepository.save(any())).thenReturn(new Lineage());
        assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
    }

    @DisplayName("has nitrogen base")
    @Test
    void hasNitrogenBaseSuccess() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
        assertEquals(true, classifiesIndividualService.hasNitrogenBase(dnaMutant));
    }

    @DisplayName("has no nitrogen base")
    @Test
    void hasNotNitrogenBaseSuccess() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATfGT", "AGATGG", "CTCCTA", "TCACTG"};

        ObjectWithConversionProblemsException thrown =
                assertThrows(ObjectWithConversionProblemsException.class,
                        () -> classifiesIndividualService.hasNitrogenBase(dnaMutant),
                        "Caracteres não validos");
        assertTrue(thrown.getMessage().contains("Caracteres não validos"));
    }

    @DisplayName("is a possible square matrix")
    @Test
    void isAPossibleSquareMatrixWithSuccess() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
        assertEquals(true, classifiesIndividualService.isAPossibleSquareMatrix(dnaMutant));
    }

    @DisplayName("is not a possibility of square matrix by index different from the default")
    @Test
    void isNotAPossibleSquareMatrixWithSuccessByIndexDifferent() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCAG"};

        ObjectWithConversionProblemsException thrown =
                assertThrows(ObjectWithConversionProblemsException.class,
                        () -> classifiesIndividualService.isAPossibleSquareMatrix(dnaMutant),
                        "Tamanho dos indices não compativeis");
        assertTrue(thrown.getMessage().contains("Tamanho dos indices não compativeis"));
    }

    @DisplayName("not a possibility of square matrix by different row column")
    @Test
    void isNotAPossibleSquareMatrixWithSuccessByDiffereRowColumn() {
        String[] dnaMutant = {"CTGTGA", "CTATGC", "AGATGG", "CTCCTA", "TCAGAC"};

        ObjectWithConversionProblemsException thrown =
                assertThrows(ObjectWithConversionProblemsException.class,
                        () -> classifiesIndividualService.isAPossibleSquareMatrix(dnaMutant),
                        "Não é uma matrix quadrada");
        assertTrue(thrown.getMessage().contains("Não é uma matrix quadrada"));
    }

}
