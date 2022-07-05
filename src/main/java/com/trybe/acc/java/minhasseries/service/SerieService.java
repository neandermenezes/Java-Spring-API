package com.trybe.acc.java.minhasseries.service;

import com.trybe.acc.java.minhasseries.domain.Tempo;
import com.trybe.acc.java.minhasseries.expection.SerieExistenteException;
import com.trybe.acc.java.minhasseries.expection.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.repository.SerieRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SerieService {

  @Autowired
  SerieRepository serieRepository;

  public List<Serie> getSeries() {
    return serieRepository.findAll();
  }

  /**
   * Connects with database to return a show by id.
   * @param id specifies the id.
   * @return show entity.
   */
  public Serie getSerieById(Integer id) {
    Serie serie = serieRepository.findById(id)
        .orElseThrow(() -> new SerieNaoEncontradaException("Série não encontrada"));

    return serie;
  }

  /**
   * Connects with database to create a new show.
   * @param serie show entity.
   * @return created show.
   */
  public Serie createSerie(Serie serie) {
    Boolean serieExistente = serieRepository.existsByNome(serie.getNome());

    if (serieExistente) {
      throw new SerieExistenteException("Série Existente");
    }

    return serieRepository.save(serie);
  }

  /**
   * Connects with database to delete a show.
   * @param id specifies the show.
   */
  public void deleteSerie(Integer id) {
    Serie serie = getSerieById(id);

    serieRepository.delete(serie);
  }

  /**
   * Connects with database to add an episode to a show.
   * @param id specifies the show.
   * @param episodio episode entity.
   * @return show with updated episodes.
   */
  public Serie addEpisodio(Integer id, Episodio episodio) {
    Serie serie = getSerieById(id);

    System.out.print(serie.getEpisodios());

    serie.adicionarEpisodio(episodio);

    System.out.println(serie.getEpisodios());

    return serieRepository.save(serie);
  }

  /**
   * Connects with database to get all episodes from a show.
   * @param id specifies the show.
   * @return show's episodes.
   */
  public List<Episodio> getEpisodiosDaSerie(Integer id) {
    Serie serie = getSerieById(id);

    return serie.getEpisodios();
  }

  /**
   * Connects with database to calculate total time from all shows' episodes.
   * @return integer that representes total time.
   */
  public Tempo getTempoTotalEpisodios() {
    List<Serie> series = serieRepository.findAll();

    int tempo = 0;

    for (int i = 0; i < series.size(); i += 1) {
      List<Episodio> episodios = series.get(i).getEpisodios();

      int tempoEpisodio = episodios.stream().map((episodio -> episodio.getDuracaoEmMinutos()))
          .reduce(0, (total, minutos) -> total + minutos);

      tempo += tempoEpisodio;
    }

    return new Tempo(tempo);
  }
}
