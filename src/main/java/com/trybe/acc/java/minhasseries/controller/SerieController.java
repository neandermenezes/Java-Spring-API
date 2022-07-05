package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

  @Autowired
  private SerieService serieService;

  @GetMapping("/series")
  public List<Serie> getSeries() {
    return serieService.getSeries();
  }

  @PostMapping("/series")
  public Serie createSerie(@RequestBody Serie serie) {
    return serieService.createSerie(serie);
  }
}
