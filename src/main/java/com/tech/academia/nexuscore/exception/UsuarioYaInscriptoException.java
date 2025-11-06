package com.tech.academia.nexuscore.exception;

public class UsuarioYaInscriptoException extends RuntimeException {

  public UsuarioYaInscriptoException(String message) {
    super(message);
  }

  public UsuarioYaInscriptoException(String nombreUsuario, String tituloCurso) {
    super(String.format("El usuario '%s' ya se encuentra inscripto al curso '%s'",
        nombreUsuario, tituloCurso));
  }
}
