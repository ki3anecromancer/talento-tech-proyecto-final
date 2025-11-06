package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.repository.ModuloRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuloServiceImpl {

  private final ModuloRepository moduloRepository;

  public ModuloServiceImpl(ModuloRepository moduloRepository) {
    this.moduloRepository = moduloRepository;
  }

  // crearModuloEnCurso

  // obtenerModuloPorId

  // actualizarModulo

  // eliminarModulo

  // obtenerModulosPorCurso
}
