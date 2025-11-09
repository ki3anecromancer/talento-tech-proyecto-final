package com.tech.academia.nexuscore.exception;

public class AccesoDenegadoException extends RuntimeException {

  public AccesoDenegadoException(String message) {
    super(message);
  }

  public AccesoDenegadoException() {
    super("No tiene los permisos suficientes para ejecutar la acción.");
  }
}
