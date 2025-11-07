package com.tech.academia.nexuscore.exception;

public class ContenidoNoEncontradoException extends RuntimeException {

  public ContenidoNoEncontradoException(String message) {
    super(message);
  }

  public ContenidoNoEncontradoException(Long id) {
    super("No se encontró un contenido con la id: " + id);
  }
}
