package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SerieController {

  @Autowired
  SerieService serieService;

  @GetMapping("/series")
  public ResponseEntity<List<Serie>> getSeries() {
    List<Serie> series = serieService.getSeries();

    return ResponseEntity.ok().body(series);
  }

  @PostMapping("/series")
  public ResponseEntity<Serie> createSerie(@RequestBody Serie serie) {
    Serie novaSerie = serieService.createSerie(serie);

    return ResponseEntity.ok().body(novaSerie);
  }
}
