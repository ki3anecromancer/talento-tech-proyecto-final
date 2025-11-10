package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ContenidoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ContenidoResponseDTO;
import com.tech.academia.nexuscore.service.ContenidoService;
import jakarta.validation.Valid;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/modulos/{idModulo}/contenidos")
public class ModuloContenidosController {

  private final ContenidoService contenidoService;

  // Crear contenido en modulo
  @PostMapping
  public ResponseEntity<ContenidoResponseDTO> crearContenidoEnModulo(
      @Valid @RequestBody ContenidoCreateRequestDTO createDto,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(contenidoService.crearContenidoEnModulo(idUsuario, createDto));
  }

  // Obtener contenidos por modulo
  @GetMapping
  public ResponseEntity<Set<ContenidoResponseDTO>> obtenerContenidosPorModulo(
      @PathVariable Long idModulo) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(contenidoService.obtenerContenidosPorModulo(idModulo));
  }
}
