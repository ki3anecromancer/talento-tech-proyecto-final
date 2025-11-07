package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.ContenidoUpdateRequestDTO;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Modulo;
import org.springframework.stereotype.Component;

@Component
public class ContenidoMapper {

  // ContenidoCreateRequestDTO + Modulo -> Contenido
  public Contenido createDtoToContenido(ContenidoCreateRequestDTO createDto, Modulo modulo) {

    return new Contenido(
        null,
        createDto.titulo(),
        createDto.tipo(),
        createDto.urlArchivo(),
        createDto.duracionMinutos(),
        modulo,
        null
    );
  }

  // Contenido -> ContenidoResponseDTO
  public ContenidoResponseDTO contenidoToResponseDto(Contenido contenido) {

    return new ContenidoResponseDTO(
        contenido.getId(),
        contenido.getTitulo(),
        contenido.getTipo(),
        contenido.getUrlArchivo(),
        contenido.getDuracionMinutos(),
        contenido.getModulo().getId()
    );
  }

  // Actualizar contenido
  public void actualizarContenido(Contenido contenido, ContenidoUpdateRequestDTO requestDto) {

    contenido.setTitulo(requestDto.titulo());
    contenido.setTipo(requestDto.tipo());
    contenido.setUrlArchivo(requestDto.urlArchivo());
    contenido.setDuracionMinutos(requestDto.duracionMinutos());
  }
}
