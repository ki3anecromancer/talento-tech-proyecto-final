package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoCreateResponseDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import com.tech.academia.nexuscore.exception.AccesoDenegadoException;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.mapper.CursoMapper;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.model.enums.Rol;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.security.jwt.JwtTokenProvider;
import com.tech.academia.nexuscore.service.CursoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

  private final CursoRepository cursoRepository;
  private final CursoMapper cursoMapper;
  private final UsuarioRepository usuarioRepository;
  private final JwtTokenProvider tokenProvider;

  // Obtener todos los cursos
  @Override
  public List<CursoResponseDTO> obtenerTodosLosCursos() {

    return cursoRepository.findAll().stream()
        .map(cursoMapper::cursoToResponseDto)
        .toList();
  }

  // Obtener curso por ID
  @Override
  public CursoResponseDTO  obtenerCursoPorId(Long id) {

    Curso curso = cursoRepository.findById(id).orElseThrow(() ->
        new CursoNoEncontradoException(id));

    return cursoMapper.cursoToResponseDto(curso);
  }

  // Crear curso
  @Override
  @Transactional
  public CursoCreateResponseDTO crearCurso(CursoCreateRequestDTO createDto, Long idUsuario) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Curso curso = cursoMapper.createDtoToCurso(createDto);
    curso.setUsuario(usuario);
    usuario.getCursos().add(curso);

    boolean rolCambiado = usuario.getRoles().add(Rol.INSTRUCTOR);

    Curso cursoGuardado = cursoRepository.save(curso);

    String nuevoToken = null;
    if (rolCambiado) {
      nuevoToken = tokenProvider.generarToken(usuario);
    }

    return new CursoCreateResponseDTO(
        cursoMapper.cursoToResponseDto(cursoGuardado),
        nuevoToken
    );
  }

  // Actualizar curso
  @Override
  @Transactional
  public CursoResponseDTO actualizarCurso(Long idCurso, Long idUsuario, CursoUpdateRequestDTO updateDto) {

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = curso.getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    cursoMapper.actualizarCurso(curso, updateDto);

    return cursoMapper.cursoToResponseDto(
        cursoRepository.save(curso)
    );
  }

  // Eliminar curso
  @Override
  @Transactional
  public void eliminarCurso(Long idCurso, Long idUsuario) {

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    boolean esAdmin = usuario.getRoles().contains(Rol.ADMIN);
    boolean esPropietario = curso.getUsuario().getId().equals(idUsuario);

    if (!esAdmin && !esPropietario) {
      throw new AccesoDenegadoException();
    }

    cursoRepository.delete(curso);
  }

  // Obtener cursos por usuario
  @Override
  public List<CursoResponseDTO> obtenerCursosPorUsuario(Long idUsuario) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    return cursoRepository.findAllByUsuario(usuario).stream()
        .map(cursoMapper::cursoToResponseDto)
        .toList();
  }
}
