package com.trybe.acc.java.minhasseries.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private String nome;

  @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Episodio> episodios;

  public Serie() {

  }

  public Serie(String nome) {
    super();
    this.nome = nome;
    this.episodios = new ArrayList<Episodio>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public List<Episodio> getEpisodios() {
    return episodios;
  }

  public void setEpisodios(List<Episodio> episodios) {
    this.episodios = episodios;
  }

  public void adicionarEpisodio(Episodio episodio) {
    this.episodios.add(episodio);
  }
}
