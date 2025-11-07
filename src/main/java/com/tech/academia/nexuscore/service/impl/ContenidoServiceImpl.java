package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.exception.ModuloNoEncontradoException;
import com.tech.academia.nexuscore.mapper.ContenidoMapper;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Modulo;
import com.tech.academia.nexuscore.repository.ContenidoRepository;
import com.tech.academia.nexuscore.repository.ModuloRepository;
import com.tech.academia.nexuscore.validation.ContenidoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContenidoServiceImpl {

  private final ContenidoRepository contenidoRepository;
  private final ModuloRepository moduloRepository;
  private final ContenidoMapper contenidoMapper;
  private final ContenidoValidator contenidoValidator;

  // Crear contenido en modulo
  public ContenidoResponseDTO crearContenidoEnModulo(ContenidoCreateRequestDTO createDto) {

    Modulo modulo = moduloRepository.findById(createDto.idModulo()).orElseThrow(() ->
        new ModuloNoEncontradoException(createDto.idModulo()));

    contenidoValidator.validarCoherenciaCreacion(createDto);

    Contenido contenido = contenidoMapper.createDtoToContenido(createDto, modulo);

    Contenido guardado = contenidoRepository.save(contenido);

    modulo.getContenidos().add(guardado);
    moduloRepository.save(modulo);

    return contenidoMapper.contenidoToResponseDto(guardado);
  }

  // obtenerContenidoPorId

  // actualizarContenido

  // eliminarContenido

  // obtenerContenidosPorModulo
}
