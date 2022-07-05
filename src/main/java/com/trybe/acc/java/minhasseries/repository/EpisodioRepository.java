package com.trybe.acc.java.minhasseries.repository;

import com.trybe.acc.java.minhasseries.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Integer> {

  boolean existsByNome(String nome);
}
