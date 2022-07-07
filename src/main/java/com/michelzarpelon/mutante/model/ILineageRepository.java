package com.michelzarpelon.mutante.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ILineageRepository extends JpaRepository<Lineage,Long> {

    @Transactional(readOnly = true)
    public Lineage findLineageByDna(String dna);

}
