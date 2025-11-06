package com.tech.academia.nexuscore.exception;

public class CursoNoEncontradoException extends RuntimeException {

  public CursoNoEncontradoException(String message) {
    super(message);
  }

  public CursoNoEncontradoException(Long id) {
    super("No se encontró un curso con la id: " + id);
  }
}
