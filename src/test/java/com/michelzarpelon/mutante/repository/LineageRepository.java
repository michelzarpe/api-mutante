package com.michelzarpelon.mutante.repository;

import com.michelzarpelon.mutante.model.Lineage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LineageRepository {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ILineageRepository lineageRepository;

    @DisplayName("should return dna")
    @Test
    public void findLineageByDna(){
        String dnaMutant = "CTGTTACTATGCTATTGTAGATGGCCCCTATCACTG";
        entityManager.persist(new Lineage(null, "CTGTTACTATGCTATTGTAGATGGCCCCTATCACTG", true));
        entityManager.persist(new Lineage(null, "CTGATACTTTGCTATTGTAGATGGCTCCTATCACTG", false));
        entityManager.persist(new Lineage(null, "CTGATACTATGCTATTGTAGATGGCTCCAATCACTT", false));
        entityManager.flush();
        Lineage lineage = lineageRepository.findLineageByDna(dnaMutant);
        assertEquals(dnaMutant,lineage.getDna());
    }

}
