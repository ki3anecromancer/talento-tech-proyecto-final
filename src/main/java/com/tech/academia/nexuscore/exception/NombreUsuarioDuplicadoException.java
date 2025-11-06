package com.tech.academia.nexuscore.exception;

public class NombreUsuarioDuplicadoException extends RuntimeException {

  public NombreUsuarioDuplicadoException(String message) {
    super(message);
  }

  public NombreUsuarioDuplicadoException() {
    super("El nombre de usuario ya está registrado y no puede ser usado");
  }
}
