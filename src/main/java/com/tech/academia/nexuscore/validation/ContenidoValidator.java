package com.tech.academia.nexuscore.validation;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.exception.IncoherenciaContenidoException;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.enums.TipoContenido;
import org.springframework.stereotype.Component;

@Component
public class ContenidoValidator {

  public void validarCoherenciaCreacion(ContenidoCreateRequestDTO dto) {

    if (dto.tipo() == TipoContenido.VIDEO || dto.tipo() == TipoContenido.AUDIO) {
      if (dto.urlArchivo() == null || dto.urlArchivo().isBlank()) {
        throw new IncoherenciaContenidoException("El contenido tipo " + dto.tipo() + " requiere una URL de archivo.");
      }
      if (dto.duracionMinutos() == null) {
        throw new IncoherenciaContenidoException("El contenido tipo " + dto.tipo() + " requiere la duración en minutos.");
      }
    }

    if (dto.tipo() == TipoContenido.TEXTO) {
      if (dto.duracionMinutos() != null) {
        throw new IncoherenciaContenidoException("El contenido tipo TEXTO no debe incluir duración en minutos.");
      }
    }
  }

  public void validarCoherenciaActualizacion(Contenido contenido) {

    if (contenido.getTipo() == TipoContenido.VIDEO || contenido.getTipo() == TipoContenido.AUDIO) {
      if (contenido.getUrlArchivo() == null || contenido.getUrlArchivo().isBlank()) {
        throw new IncoherenciaContenidoException("El contenido tipo " + contenido.getTipo() + " requiere una URL.");
      }
      if (contenido.getDuracionMinutos() == null) {
        throw new IncoherenciaContenidoException("El contenido tipo " + contenido.getTipo() + " requiere la duración.");
      }
    }

    if (contenido.getTipo() == TipoContenido.TEXTO && contenido.getDuracionMinutos() != null) {
      throw new IncoherenciaContenidoException("El contenido tipo TEXTO no debe incluir duración.");
    }
  }
}
