package com.trybe.acc.java.minhasseries.controller;

import com.trybe.acc.java.minhasseries.expection.DataError;
import com.trybe.acc.java.minhasseries.expection.EpisodioExistenteException;
import com.trybe.acc.java.minhasseries.expection.SerieExistenteException;
import com.trybe.acc.java.minhasseries.expection.SerieNaoEncontradaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class SerieAdvice {

  @ExceptionHandler({SerieExistenteException.class, EpisodioExistenteException.class})
  public ResponseEntity<DataError> conflictError(RuntimeException error) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new DataError(error.getMessage()));
  }

  @ExceptionHandler(SerieNaoEncontradaException.class)
  public ResponseEntity<DataError> serieNaoEncontradaError(RuntimeException error) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DataError(error.getMessage()));
  }
}
