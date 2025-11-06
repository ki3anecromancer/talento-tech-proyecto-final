package com.tech.academia.nexuscore.exception;

public class ModuloNoEncontradoException extends RuntimeException {

  public ModuloNoEncontradoException(String message) {
    super(message);
  }

  public ModuloNoEncontradoException(Long id) {
    super("No se encontró un módulo con la id: " + id);
  }
}
