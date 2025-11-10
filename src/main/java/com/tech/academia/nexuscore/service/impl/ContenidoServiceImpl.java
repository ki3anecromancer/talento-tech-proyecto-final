package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.dto.ContenidoUpdateRequestDTO;
import com.tech.academia.nexuscore.exception.AccesoDenegadoException;
import com.tech.academia.nexuscore.exception.ContenidoNoEncontradoException;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.ModuloNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.mapper.ContenidoMapper;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Modulo;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.model.enums.Rol;
import com.tech.academia.nexuscore.repository.ContenidoRepository;
import com.tech.academia.nexuscore.repository.ModuloRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.service.ContenidoService;
import com.tech.academia.nexuscore.validation.ContenidoValidator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ContenidoServiceImpl implements ContenidoService {

  private final ContenidoRepository contenidoRepository;
  private final ModuloRepository moduloRepository;
  private final ContenidoMapper contenidoMapper;
  private final ContenidoValidator contenidoValidator;
  private final UsuarioRepository usuarioRepository;

  // Crear contenido en modulo
  @Override
  public ContenidoResponseDTO crearContenidoEnModulo(Long idUsuario, ContenidoCreateRequestDTO createDto) {

    Modulo modulo = moduloRepository.findById(createDto.idModulo()).orElseThrow(() ->
        new ModuloNoEncontradoException(createDto.idModulo()));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = modulo.getCurso().getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    contenidoValidator.validarCoherenciaCreacion(createDto);

    Contenido contenido = contenidoMapper.createDtoToContenido(createDto, modulo);

    Contenido guardado = contenidoRepository.save(contenido);

    modulo.getContenidos().add(guardado);
    moduloRepository.save(modulo);

    return contenidoMapper.contenidoToResponseDto(guardado);
  }

  // Obtener contenido por ID
  @Override
  public ContenidoResponseDTO obtenerContenidoPorId(Long id) {

    Contenido contenido = contenidoRepository.findById(id).orElseThrow(() ->
        new ContenidoNoEncontradoException(id));

    return contenidoMapper.contenidoToResponseDto(contenido);
  }

  // Actualizar contenido
  @Override
  public ContenidoResponseDTO actualizarContenido(Long id, ContenidoUpdateRequestDTO updateDto) {

    Contenido contenido = contenidoRepository.findById(id).orElseThrow(() ->
        new ContenidoNoEncontradoException(id));

    contenidoMapper.actualizarContenido(contenido, updateDto);

    contenidoValidator.validarCoherenciaActualizacion(contenido);

    return contenidoMapper.contenidoToResponseDto(
        contenidoRepository.save(contenido)
    );
  }

  // Obtener contenidos por modulo
  @Override
  public Set<ContenidoResponseDTO> obtenerContenidosPorModulo(Long idModulo) {

    Modulo modulo = moduloRepository.findById(idModulo).orElseThrow(() ->
        new ModuloNoEncontradoException(idModulo));

    return modulo.getContenidos().stream()
        .map(contenidoMapper::contenidoToResponseDto)
        .collect(Collectors.toSet());
  }

  // Eliminar contenido
  @Override
  public void eliminarContenido(Long id) {

    Contenido contenido = contenidoRepository.findById(id).orElseThrow(() ->
        new ContenidoNoEncontradoException(id));

    contenidoRepository.delete(contenido);
  }
}
