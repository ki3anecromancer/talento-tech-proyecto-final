package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.repository.ContenidoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContenidoServiceImpl {

  private final ContenidoRepository contenidoRepository;

  public ContenidoServiceImpl(ContenidoRepository contenidoRepository) {
    this.contenidoRepository = contenidoRepository;
  }

  // crearContenidoEnModulo

  // obtenerContenidoPorId

  // actualizarContenido

  // eliminarContenido

  // obtenerContenidosPorModulo
}
