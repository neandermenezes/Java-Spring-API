package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.domain.Tempo;
import com.trybe.acc.java.minhasseries.expection.DataError;
import com.trybe.acc.java.minhasseries.expection.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.service.SerieService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class SerieController {

  @Autowired
  SerieService serieService;

  /**
   * Get request for available shows.
   * @return A list of shows.
   */
  @GetMapping("/series")
  public ResponseEntity<List<Serie>> getSeries() {
    List<Serie> series = serieService.getSeries();

    return ResponseEntity.ok().body(series);
  }

  /**
   * Get request for available episodes of a specific show.
   * @param id specifies the show.
   * @return A list of episodes of a certain show.
   */
  @GetMapping("/series/{serie_id}/episodios")
  public ResponseEntity<List<Episodio>> getEpisodiosDaSerie(@PathVariable("serie_id") Integer id) {
    List<Episodio> episodios = serieService.getEpisodiosDaSerie(id);

    return ResponseEntity.ok().body(episodios);
  }

  /**
   * Get request for total sum of time from all available episodes from all shows.
   * @return an Integer that represents the total time.
   */
  @GetMapping("/series/tempo")
  public ResponseEntity<Tempo> getTempoTotalEpisodios() {
    Tempo tempo = serieService.getTempoTotalEpisodios();

    return ResponseEntity.ok().body(tempo);
  }

  /**
   * Post request to create a new show.
   * @param serie Serie entity.
   * @return created show.
   */
  @PostMapping("/series")
  public ResponseEntity<Serie> createSerie(@RequestBody Serie serie) {
    Serie novaSerie = serieService.createSerie(serie);

    return ResponseEntity.ok().body(novaSerie);
  }

  /**
   * Post request to add an episode to a certain show.
   * @param id Specifies the show.
   * @param episodio Episode entity.
   * @return Updated show with the included episode.
   */
  @CircuitBreaker(name = "addEpisodio", fallbackMethod = "addEpisodioFallback")
  @PostMapping("/series/{serie_id}/episodios")
  public ResponseEntity<Serie> addEpisodio(
      @PathVariable("serie_id") Integer id,
      @RequestBody Episodio episodio
  ) {
    Serie serieAtualizada = serieService.addEpisodio(id, episodio);

    return ResponseEntity.ok().body(serieAtualizada);
  }

  /**
   * Circuit breaker for adding episode.
   * @param id specifies the show.
   * @param episodio episode entity.
   * @param exc error entity.
   * @return response with status and error message.
   */
  public ResponseEntity<DataError> addEpisodioFallback(
      @PathVariable("serie_id") Integer id,
      @RequestBody Episodio episodio,
      Exception exc
  ) {
    if (exc.getClass() == SerieNaoEncontradaException.class) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(exc.getMessage()));
    }

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new DataError("Serviço temporariamente indisponível"));
  }

  /**
   * Delete request for a certain show.
   * @param id Specifies the show.
   * @return Status 200.
   */
  @DeleteMapping("/series/{serie_id}")
  public ResponseEntity deleteSerie(@PathVariable("serie_id") Integer id) {
    serieService.deleteSerie(id);

    return ResponseEntity.ok().build();
  }

}
