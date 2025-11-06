package com.tech.academia.nexuscore.exception;

public class UsuarioNoEncontradoException extends RuntimeException {

  public UsuarioNoEncontradoException(String message) {
    super(message);
  }

  public UsuarioNoEncontradoException(Long id) {
    super("No se encontró un usuario con la id: " + id);
  }
}
