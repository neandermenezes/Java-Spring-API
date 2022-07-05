package com.trybe.acc.java.minhasseries.service;

import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SerieService {

  @Autowired
  private SerieRepository serieRepository;

  public List<Serie> getSeries() {
    return serieRepository.findAll();
  }

  public Serie createSerie(Serie serie) {
    return serieRepository.save(serie);
  }
}
