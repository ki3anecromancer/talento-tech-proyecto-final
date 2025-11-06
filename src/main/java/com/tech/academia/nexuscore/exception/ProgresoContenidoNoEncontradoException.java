package com.tech.academia.nexuscore.exception;

import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Usuario;

public class ProgresoContenidoNoEncontradoException extends RuntimeException {

  public ProgresoContenidoNoEncontradoException(String message) {
    super(message);
  }

  public ProgresoContenidoNoEncontradoException(Usuario usuario, Contenido contenido) {
    super(String.format("No se encontró progreso en el contenido '%s' para el usuario '%s'.",
        contenido.getTitulo(), usuario.getNombreUsuario()));
  }
}
