package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.InscripcionCursosResponseDTO;
import com.tech.academia.nexuscore.dto.InscripcionRequestDTO;
import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.InscripcionNoEncontradaException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioYaInscriptoException;
import com.tech.academia.nexuscore.mapper.CursoMapper;
import com.tech.academia.nexuscore.mapper.InscripcionMapper;
import com.tech.academia.nexuscore.mapper.UsuarioMapper;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Inscripcion;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.repository.InscripcionRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class InscripcionServiceImpl {

  private final InscripcionRepository inscripcionRepository;
  private final UsuarioRepository usuarioRepository;
  private final CursoRepository cursoRepository;
  private final InscripcionMapper inscripcionMapper;
  private final UsuarioMapper usuarioMapper;
  private final CursoMapper cursoMapper;

  public InscripcionServiceImpl(
      InscripcionRepository inscripcionRepository,
      UsuarioRepository usuarioRepository,
      CursoRepository cursoRepository,
      InscripcionMapper inscripcionMapper,
      UsuarioMapper usuarioMapper,
      CursoMapper cursoMapper) {

    this.inscripcionRepository = inscripcionRepository;
    this.usuarioRepository = usuarioRepository;
    this.cursoRepository = cursoRepository;
    this.inscripcionMapper = inscripcionMapper;
    this.usuarioMapper = usuarioMapper;
    this.cursoMapper = cursoMapper;
  }

  // Inscribir usuario a un curso
  public InscripcionResponseDTO inscribirUsuarioACurso(InscripcionRequestDTO requestDTO) {

    Usuario usuario = usuarioRepository.findById(requestDTO.usuarioId()).orElseThrow(() ->
        new UsuarioNoEncontradoException(requestDTO.usuarioId()));

    Curso curso = cursoRepository.findById(requestDTO.cursoId()).orElseThrow(() ->
        new CursoNoEncontradoException(requestDTO.cursoId()));

    if (inscripcionRepository.existsByUsuarioAndCurso(usuario, curso)) {
      throw new UsuarioYaInscriptoException(usuario.getNombreUsuario(), curso.getTitulo());
    }

    Inscripcion inscripcion = inscripcionMapper.usuarioYCursoToInscripcion(usuario, curso);

    return inscripcionMapper.inscripcionToResponseDto(
        inscripcionRepository.save(inscripcion),
        usuarioMapper.usuarioToReferenceDto(usuario),
        cursoMapper.cursoToReferenceDto(curso)
        );
  }

  // Obtener inscripcion por ID
  public InscripcionResponseDTO obtenerInscripcionPorId(Long id) {

    Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(() ->
        new InscripcionNoEncontradaException(id));

    return inscripcionMapper.inscripcionToResponseDto(
        inscripcion,
        usuarioMapper.usuarioToReferenceDto(inscripcion.getUsuario()),
        cursoMapper.cursoToReferenceDto(inscripcion.getCurso())
    );
  }

  // Obtener los cursos inscritos de usuario
  public InscripcionCursosResponseDTO obtenerCursosInscritosDeUsuario(Long idUsuario) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Set<Inscripcion> inscripciones = inscripcionRepository.findAllByUsuario(usuario);

    return inscripcionMapper.inscripcionesToCursosResponseDto(
        usuarioMapper.usuarioToReferenceDto(usuario),
        inscripciones
    );
  }

  // actualizarProgreso

  // eliminarInscripcion
}
