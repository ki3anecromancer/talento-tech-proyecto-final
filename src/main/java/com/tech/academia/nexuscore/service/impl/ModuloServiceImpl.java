package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.mapper.CursoMapper;
import com.tech.academia.nexuscore.mapper.ModuloMapper;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.repository.CursoRepository;
import com.tech.academia.nexuscore.repository.ModuloRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ModuloServiceImpl {

  private final ModuloRepository moduloRepository;
  private final CursoRepository cursoRepository;
  private final ModuloMapper moduloMapper;

  public ModuloServiceImpl(
      ModuloRepository moduloRepository,
      CursoRepository cursoRepository,
      ModuloMapper moduloMapper) {

    this.moduloRepository = moduloRepository;
    this.cursoRepository = cursoRepository;
    this.moduloMapper = moduloMapper;
  }

  // Obtener modulos por curso
  public Set<ModuloResponseDTO> obtenerModulosPorcurso(Long idCurso) {

    Curso curso = cursoRepository.findById(idCurso).orElseThrow(() ->
        new CursoNoEncontradoException(idCurso));

    return curso.getModulos().stream()
        .map(moduloMapper::moduloToResponseDto)
        .collect(Collectors.toSet());
  }

  // obtenerModuloPorId

  // crearModuloEnCurso

  // actualizarModulo

  // eliminarModulo


}
