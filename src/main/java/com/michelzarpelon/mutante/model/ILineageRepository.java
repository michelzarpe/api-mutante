package com.michelzarpelon.mutante.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILineageRepository extends JpaRepository<Lineage,Long> {
}
