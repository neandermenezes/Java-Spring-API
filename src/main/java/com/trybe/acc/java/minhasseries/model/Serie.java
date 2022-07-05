package com.trybe.acc.java.minhasseries.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Serie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;

  @OneToMany(mappedBy = "Episodio", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Episodio> episodios;

  public Serie() {
    this.episodios = new ArrayList<Episodio>();
  }

}
