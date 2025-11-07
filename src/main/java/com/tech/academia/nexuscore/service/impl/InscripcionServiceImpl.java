package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.InscripcionCursosResponseDTO;
import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.InscripcionNoEncontradaException;
import com.tech.academia.nexuscore.exception.ProgresoContenidoNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioYaInscriptoException;
import com.tech.academia.nexuscore.mapper.CursoMapper;
import com.tech.academia.nexuscore.mapper.InscripcionMapper;
import com.tech.academia.nexuscore.mapper.UsuarioMapper;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Inscripcion;
import com.tech.academia.nexuscore.model.Modulo;
import com.tech.academia.nexuscore.model.ProgresoContenido;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.repository.InscripcionRepository;
import com.tech.academia.nexuscore.repository.ProgresoContenidoRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.service.InscripcionService;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InscripcionServiceImpl implements InscripcionService {

  private final InscripcionRepository inscripcionRepository;
  private final UsuarioRepository usuarioRepository;
  private final CursoRepository cursoRepository;
  private final InscripcionMapper inscripcionMapper;
  private final UsuarioMapper usuarioMapper;
  private final CursoMapper cursoMapper;
  private final ProgresoContenidoRepository progresoContenidoRepository;

  // Inscribir usuario a un curso
  @Override
  public InscripcionResponseDTO inscribirUsuarioACurso(Long idUsuario, Long idCurso) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    if (inscripcionRepository.existsByUsuarioAndCurso(usuario, curso)) {
      throw new UsuarioYaInscriptoException(usuario.getNombreUsuario(), curso.getTitulo());
    }

    Inscripcion inscripcion = inscripcionMapper.usuarioYCursoToInscripcion(usuario, curso);

    Set<Modulo> modulos = curso.getModulos();

    Set<ProgresoContenido> nuevosProgresos = new HashSet<>();

    for (Modulo modulo : modulos) {

      Set<Contenido> contenidos = modulo.getContenidos();

      for (Contenido contenido : contenidos) {

        ProgresoContenido progresoContenido = new ProgresoContenido(
            null,
            usuario,
            contenido,
            Boolean.FALSE,
            null
        );

        nuevosProgresos.add(progresoContenido);
      }
    }

    progresoContenidoRepository.saveAll(nuevosProgresos);

    return inscripcionMapper.inscripcionToResponseDto(
        inscripcionRepository.save(inscripcion),
        usuarioMapper.usuarioToReferenceDto(usuario),
        cursoMapper.cursoToReferenceDto(curso)
        );
  }

  // Obtener inscripcion por ID
  @Override
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
  @Override
  public InscripcionCursosResponseDTO obtenerCursosInscritosDeUsuario(Long idUsuario) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Set<Inscripcion> inscripciones = inscripcionRepository.findAllByUsuario(usuario);

    return inscripcionMapper.inscripcionesToCursosResponseDto(
        usuarioMapper.usuarioToReferenceDto(usuario),
        inscripciones
    );
  }

  // Actualizar progreso
  @Override
  public InscripcionResponseDTO actualizarProgreso(Long idUsuario, Long idCurso) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    Inscripcion inscripcion = inscripcionRepository.findByUsuarioAndCurso(usuario, curso).orElseThrow(() ->
        new InscripcionNoEncontradaException(usuario, curso));

    int totalContenido = 0;
    int totalContenidoCompletado = 0;

    for (Modulo modulo : curso.getModulos()) {

      for (Contenido contenido : modulo.getContenidos()) {

        ProgresoContenido progreso = progresoContenidoRepository.findByUsuarioAndContenido(usuario, contenido)
            .orElseThrow(() -> new ProgresoContenidoNoEncontradoException(usuario, contenido));

        totalContenido++;

        if (progreso.getCompletado()) {
          totalContenidoCompletado++;
        }
      }
    }

    Double progreso = totalContenidoCompletado * 100.0 / totalContenido;

    inscripcion.setProgreso(progreso);

    return inscripcionMapper.inscripcionToResponseDto(
        inscripcionRepository.save(inscripcion),
        usuarioMapper.usuarioToReferenceDto(usuario),
        cursoMapper.cursoToReferenceDto(curso)
    );
  }

  // Eliminar inscripcion
  @Override
  public void eliminarInscripcion(Long id) {

    Inscripcion inscripcion = inscripcionRepository.findById(id).orElseThrow(() ->
        new InscripcionNoEncontradaException(id));

    inscripcionRepository.delete(inscripcion);
  }
}
