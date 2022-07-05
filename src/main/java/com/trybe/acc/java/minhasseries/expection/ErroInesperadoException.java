package com.trybe.acc.java.minhasseries.expection;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ErroInesperadoException extends RuntimeException {

  public ErroInesperadoException(String message) {
    super(message);
  }
}
