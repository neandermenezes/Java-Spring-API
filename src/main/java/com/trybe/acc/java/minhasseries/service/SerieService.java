package com.trybe.acc.java.minhasseries.service;

import com.trybe.acc.java.minhasseries.domain.Tempo;
import com.trybe.acc.java.minhasseries.expection.SerieExistenteException;
import com.trybe.acc.java.minhasseries.expection.SerieNaoEncontradaException;
import com.trybe.acc.java.minhasseries.model.Episodio;
import com.trybe.acc.java.minhasseries.model.Serie;
import com.trybe.acc.java.minhasseries.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SerieService {

  @Autowired
  SerieRepository serieRepository;

  public List<Serie> getSeries() {
    return serieRepository.findAll();
  }

  public Serie getSerieById(Integer id) {
    Serie serie = serieRepository.findById(id).orElseThrow(() -> new SerieNaoEncontradaException("Série não encontrada"));

    return serie;
  }
  public Serie createSerie(Serie serie) {
    Boolean serieExistente = serieRepository.existsByNome(serie.getNome());

    if(serieExistente) {
      throw new SerieExistenteException("Série Existente");
    }

    return serieRepository.save(serie);
  }

  public void deleteSerie(Integer id) {
    Serie serie = getSerieById(id);

    serieRepository.delete(serie);
  }

  public Serie addEpisodio(Integer id, Episodio episodio) {
    Serie serie = getSerieById(id);

    System.out.print(serie.getEpisodios());

    serie.adicionarEpisodio(episodio);

    System.out.println(serie.getEpisodios());

    return serieRepository.save(serie);
  }

  public List<Episodio> getEpisodiosDaSerie(Integer id) {
    Serie serie = getSerieById(id);

    System.out.print(serie.getEpisodios());
    if (serie == null) {
      throw new SerieNaoEncontradaException("Série não encontrada");
    }
    System.out.print(serie.getEpisodios());
    return serie.getEpisodios();
  }

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
