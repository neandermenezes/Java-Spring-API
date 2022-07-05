package com.trybe.acc.java.minhasseries.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SerieExistenteException extends RuntimeException {

  public SerieExistenteException(String message) {
    super(message);
  }
}
