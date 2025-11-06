package com.tech.academia.nexuscore.exception;

import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Usuario;

public class ProgresoContenidoNoInicializadoException extends RuntimeException {

  public ProgresoContenidoNoInicializadoException(String message) {
    super(message);
  }

  public ProgresoContenidoNoInicializadoException(Usuario usuario, Contenido contenido) {
    super(String.format("No existe progreso del usuario '%s' en el contenido '%s'.",
        usuario.getNombreUsuario(), contenido.getTitulo()));
  }
}
