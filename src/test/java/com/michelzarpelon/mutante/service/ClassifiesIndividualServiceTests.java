package com.michelzarpelon.mutante.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.michelzarpelon.mutante.config.exception.ObjectWithConversionProblemsException;
import com.michelzarpelon.mutante.service.impl.ClassifiesIndividualService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClassifiesIndividualServiceTests {

	@InjectMocks
	ClassifiesIndividualService classifiesIndividualService;

	@BeforeAll
	void setUp(){
		classifiesIndividualService = new ClassifiesIndividualService();
	}

	@DisplayName("convert array to string")
	@Test
	void arrayToString() {
		String[] dnaMutant = {"CTGAGA", "CTATGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
		String result = "CTGAGACTATGCTATTGTAGAGGGCCCCTATCACTG";
		assertEquals(result, classifiesIndividualService.arrayToString(dnaMutant));
	}

	@DisplayName("is not mutant")
	@Test
	void isNotMutant() {
		String[] dnaMutant = {"CTGATA", "CTTTGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
		assertEquals(false, classifiesIndividualService.isMutant(dnaMutant));
	}

	@DisplayName("is not mutant")
	@Test()
	void isMutanteWithException() {
		String[] dnaMutant = null;
		ObjectWithConversionProblemsException thrown =
				assertThrows(ObjectWithConversionProblemsException.class,
				() -> classifiesIndividualService.isMutant(dnaMutant),
				"Não Foi possível processar objeto");
		assertTrue(thrown.getMessage().contains("Não Foi possível processar objeto"));
	}

	@DisplayName("is mutant by diagonal")
	@Test
	void isMutantByDiagonal() {
		String[] dnaMutant = {"CTGATA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
		assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
	}

	@DisplayName("is mutant by horizontal")
	@Test
	void isMutantByHorizontal() {
		String[] dnaMutant = {"CTGTTA", "CTATGC", "TATTGT", "AGATGG", "CCCCTA", "TCACTG"};
		assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
	}

	@DisplayName("is mutant by horizontal")
	@Test
	void isMutantByVertical() {
		String[] dnaMutant = {"CTGTGA", "CTATGC", "TATTGT", "AGATGG", "CTCCTA", "TCACTG"};
		assertEquals(true, classifiesIndividualService.isMutant(dnaMutant));
	}
}
