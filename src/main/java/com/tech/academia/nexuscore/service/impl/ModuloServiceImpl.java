package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.ModuloCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloUpdateRequestDTO;
import com.tech.academia.nexuscore.exception.AccesoDenegadoException;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.ModuloNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.mapper.ModuloMapper;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Modulo;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.model.enums.Rol;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.repository.ModuloRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.service.ModuloService;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ModuloServiceImpl implements ModuloService {

  private final ModuloRepository moduloRepository;
  private final CursoRepository cursoRepository;
  private final ModuloMapper moduloMapper;
  private final UsuarioRepository usuarioRepository;

  // Obtener modulos por curso
  @Override
  @Transactional(readOnly = true)
  public Set<ModuloResponseDTO> obtenerModulosPorcurso(Long idCurso) {

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    return curso.getModulos().stream()
        .map(moduloMapper::moduloToResponseDto)
        .collect(Collectors.toSet());
  }

  // Obtener modulo por ID
  @Override
  public ModuloResponseDTO obtenerModuloPorId(Long id) {

    Modulo modulo = moduloRepository.findById(id).orElseThrow(() ->
        new ModuloNoEncontradoException(id));

    return moduloMapper.moduloToResponseDto(modulo);
  }

  // Crear modulo en curso
  @Override
  @Transactional
  public ModuloResponseDTO crearModuloEnCurso(Long idUsuario, ModuloCreateRequestDTO requestDto) {

    Curso curso = cursoRepository.findById(requestDto.idCurso()).orElseThrow(() ->
        new CursoNoEncontradoException(requestDto.idCurso()));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = curso.getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    Modulo modulo = moduloMapper.createDtoToModulo(requestDto, curso);

    Modulo moduloGuardado = moduloRepository.save(modulo);

    curso.getModulos().add(modulo);

    return moduloMapper.moduloToResponseDto(moduloGuardado);
  }

  // Actualizar modulo
  @Override
  @Transactional
  public ModuloResponseDTO actualizarModulo(Long idModulo, Long idUsuario, ModuloUpdateRequestDTO updateDto) {

    Modulo modulo = moduloRepository.findById(idModulo).orElseThrow(() ->
        new ModuloNoEncontradoException(idModulo));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = modulo.getCurso().getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    moduloMapper.actualizarModulo(modulo, updateDto);

    return moduloMapper.moduloToResponseDto(
      moduloRepository.save(modulo)
    );
  }

  // Eliminar modulo
  @Override
  @Transactional
  public void eliminarModulo(Long idModuelo, Long idUsuario) {

    Modulo modulo = moduloRepository.findById(idModuelo).orElseThrow(() ->
        new ModuloNoEncontradoException(idModuelo));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = modulo.getCurso().getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    moduloRepository.delete(modulo);
  }
}
