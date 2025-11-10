package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ModuloCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.service.ModuloService;
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
@RequestMapping("/api/cursos/{idCurso}/modulos")
public class CursoModulosController {

  private final ModuloService moduloService;

  // Obtener modulos por curso
  @GetMapping
  public ResponseEntity<Set<ModuloResponseDTO>> obtenerModulosPorcurso(
      @PathVariable Long idCurso) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(moduloService.obtenerModulosPorcurso(idCurso));
  }

  // Crear modulo en curso
  @PostMapping
  public ResponseEntity<ModuloResponseDTO> crearModuloEnCurso(
      @Valid @RequestBody ModuloCreateRequestDTO requestDto,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(moduloService.crearModuloEnCurso(idUsuario, requestDto));
  }
}
