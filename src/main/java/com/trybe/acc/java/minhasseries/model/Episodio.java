package com.trybe.acc.java.minhasseries.model;

import javax.persistence.*;

@Entity
public class Episodio {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String numero;

  private Integer duracaoEmMinutos;

  @ManyToOne
  @JoinColumn(name = "id_episodio")
  private Serie serie;

}
