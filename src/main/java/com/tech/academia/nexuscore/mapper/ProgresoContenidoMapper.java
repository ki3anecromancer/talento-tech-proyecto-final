package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.ContenidoReferenceDTO;
import com.tech.academia.nexuscore.dto.ProgresoContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioReferenceDTO;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.ProgresoContenido;
import com.tech.academia.nexuscore.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class ProgresoContenidoMapper {

  // ProgresoContenido + Usuario + Contenido -> ProgresoContenidoResponseDTO
  public ProgresoContenidoResponseDTO progresoToContenidoResponseDto(
      ProgresoContenido progresoContenido, Usuario usuario, Contenido contenido) {

    return new ProgresoContenidoResponseDTO(
        progresoContenido.getId(),
        new UsuarioReferenceDTO(usuario.getId(), usuario.getNombreUsuario()),
        new ContenidoReferenceDTO(contenido.getId(), contenido.getTitulo()),
        progresoContenido.getCompletado(),
        progresoContenido.getFechaCompletado()
    );
  }
}
