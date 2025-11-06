package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.ModuloCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloUpdateRequestDTO;
import java.util.Set;

public interface ModuloService {

  // Obtener modulos por curso
  Set<ModuloResponseDTO> obtenerModulosPorcurso(Long idCurso);

  // Obtener modulo por ID
  ModuloResponseDTO obtenerModuloPorId(Long id);

  // Crear modulo en curso
  ModuloResponseDTO crearModuloEnCurso(ModuloCreateRequestDTO requestDto);

  // Actualizar modulo
  ModuloResponseDTO actualizarModulo(Long id, ModuloUpdateRequestDTO updateDto);

  // Eliminar modulo
  void eliminarModulo(Long id);
}
