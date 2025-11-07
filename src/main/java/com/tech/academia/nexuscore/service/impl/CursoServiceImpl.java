package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.mapper.CursoMapper;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.service.CursoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CursoServiceImpl implements CursoService {

  private final CursoRepository cursoRepository;
  private final CursoMapper cursoMapper;

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
  public CursoResponseDTO crearCurso(CursoCreateRequestDTO createDto) {

    Curso curso = cursoMapper.createDtoToCurso(createDto);

    return cursoMapper.cursoToResponseDto(
        cursoRepository.save(curso)
    );
  }

  // Actualizar curso
  @Override
  public CursoResponseDTO actualizarCurso(Long id, CursoUpdateRequestDTO updateDto) {

    Curso curso = cursoRepository.findById(id).orElseThrow(() ->
        new CursoNoEncontradoException(id));

    cursoMapper.actualizarCurso(curso, updateDto);

    return cursoMapper.cursoToResponseDto(
        cursoRepository.save(curso)
    );
  }

  // Eliminar curso
  @Override
  public void eliminarCurso(Long id) {

    if (!cursoRepository.existsById(id)) {
      throw new CursoNoEncontradoException(id);
    }

    cursoRepository.deleteById(id);
  }
}
