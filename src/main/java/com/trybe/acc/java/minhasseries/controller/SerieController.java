package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.domain.Tempo;
import com.trybe.acc.java.minhasseries.expection.DataError;
import com.trybe.acc.java.minhasseries.expection.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SerieService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
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

  @GetMapping("/series/{serie_id}/episodios")
  public ResponseEntity<List<Episodio>> getEpisodiosDaSerie(@PathVariable("serie_id") Integer id) {
    List<Episodio> episodios = serieService.getEpisodiosDaSerie(id);

    return ResponseEntity.ok().body(episodios);
  }

  @GetMapping("/series/tempo")
  public ResponseEntity<Tempo> getTempoTotalEpisodios() {
    Tempo tempo = serieService.getTempoTotalEpisodios();

    return ResponseEntity.ok().body(tempo);
  }

  @PostMapping("/series")
  public ResponseEntity<Serie> createSerie(@RequestBody Serie serie) {
    Serie novaSerie = serieService.createSerie(serie);

    return ResponseEntity.ok().body(novaSerie);
  }

  @CircuitBreaker(name = "addEpisodio", fallbackMethod = "addEpisodioFallback")
  @PostMapping("/series/{serie_id}/episodios")
  public ResponseEntity<Serie> addEpisodio(@PathVariable("serie_id") Integer id, @RequestBody Episodio episodio) {
    Serie serieAtualizada = serieService.addEpisodio(id, episodio);

    return ResponseEntity.ok().body(serieAtualizada);
  }

  public ResponseEntity<DataError> addEpisodioFallback(@PathVariable("serie_id") Integer id, @RequestBody Episodio episodio, Exception exc) {
    if (exc.getClass() == SerieNaoEncontradaException.class) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(exc.getMessage()));
    }

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new DataError("Serviço temporariamente indisponível"));
  }

  @DeleteMapping("/series/{serie_id}")
  public ResponseEntity deleteSerie(@PathVariable("serie_id") Integer id) {
    serieService.deleteSerie(id);

    return ResponseEntity.ok().build();
  }

}
