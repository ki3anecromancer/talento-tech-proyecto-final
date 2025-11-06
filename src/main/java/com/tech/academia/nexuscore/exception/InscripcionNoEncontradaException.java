package com.tech.academia.nexuscore.exception;

public class InscripcionNoEncontradaException extends RuntimeException {

  public InscripcionNoEncontradaException(String message) {
    super(message);
  }

  public InscripcionNoEncontradaException(Long id) {
    super("No se encontró una inscripción con la id: " + id);
  }
}
