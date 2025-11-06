package com.tech.academia.nexuscore.exception;

public class EmailDuplicadoException extends RuntimeException {

  public EmailDuplicadoException(String message) {
    super(message);
  }

  public EmailDuplicadoException() {
    super("El email ya está registrado y no puede ser usado");
  }
}
