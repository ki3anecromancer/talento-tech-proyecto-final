package com.tech.academia.nexuscore.exception;

import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Usuario;

public class InscripcionNoEncontradaException extends RuntimeException {

  public InscripcionNoEncontradaException(String message) {
    super(message);
  }

  public InscripcionNoEncontradaException(Long id) {
    super("No se encontró una inscripción con la id: " + id);
  }

  public InscripcionNoEncontradaException(Usuario usuario, Curso curso) {
    super(String.format("El usuario '%s' no se encuentra inscrito al curso '%s'.",
        usuario.getNombreUsuario(), curso.getTitulo()));
  }
}
