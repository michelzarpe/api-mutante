package com.michelzarpelon.mutante.service;

import com.michelzarpelon.mutante.config.exception.CalculateException;
import com.michelzarpelon.mutante.config.exception.DataIntegrityException;
import com.michelzarpelon.mutante.dto.StatusDto;
import com.michelzarpelon.mutante.model.ILineageRepository;
import com.michelzarpelon.mutante.model.Lineage;
import com.michelzarpelon.mutante.service.impl.StatusService;
import java.util.Arrays;
import java.util.List;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class StatusServiceTests {

    @InjectMocks
    StatusService statusService;

    @MockBean
    ILineageRepository iLineageRepository;

    @BeforeAll
    void setUp() {
        statusService = new StatusService();
    }


    @DisplayName("return lineage status")
    @Test
    void getStatusLineage() {

        List<Lineage> lineageList = Arrays.asList(
                new Lineage(1L, "CTGATACTATGCTATTGTAGATGGCTCCAATCACTG", false),
                new Lineage(2L, "CTGTTACTATGCTATTGTAGATGGCCCCTATCACTG", true),
                new Lineage(3L, "CTGATACTTTGCTATTGTAGATGGCTCCTATCACTG", false)
        );

        when(iLineageRepository.findAll()).thenReturn(lineageList);

        var expected = new StatusDto(1, 2, 0.5D);

        var result = statusService.getStatusLineage();

        assertEquals(expected.getRatio(),result.getRatio());
		assertEquals(expected.getCount_human_dna(),result.getCount_human_dna());
		assertEquals(expected.getCount_mutant_dna(),result.getCount_mutant_dna());
    }

    @Test()
    void getStatusLineageWithException() {

        when(iLineageRepository.findAll()).thenThrow(new CalculateException(""));

        CalculateException thrown =
                assertThrows(CalculateException.class,
                        () -> statusService.getStatusLineage(),
                        "Erro ao realizar calculo da proporção");
        assertTrue(thrown.getMessage().contains("Erro ao realizar calculo da proporção"));
    }

    @Test
    void testGetRatioWithZero(){
        var resutl = statusService.getRatio(0,0);
        assertEquals(0,resutl);
    }

    @Test
    void testGetRatioDifZero(){
        var resutl = statusService.getRatio(1,2);
        assertEquals(0.5D,resutl);
    }

}
