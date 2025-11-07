package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.InscripcionCursosResponseDTO;
import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetails;
import com.tech.academia.nexuscore.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UsuarioInscripcionesController {

  private final InscripcionService inscripcionService;

  // Obtener los cursos inscritos de usuario
  @GetMapping("/usuarios/me/inscripciones")
  public ResponseEntity<InscripcionCursosResponseDTO> obtenerCursosInscritosDeUsuario(
      @AuthenticationPrincipal UsuarioDetails usuarioDetails) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(inscripcionService.obtenerCursosInscritosDeUsuario(usuarioDetails.getId()));
  }

  // Actualizar progreso
  @PatchMapping("/cursos/{idCurso}/inscripciones/recalcular-progreso")
  public ResponseEntity<InscripcionResponseDTO> actualizarProgreso(
      @PathVariable Long idCurso,
      @AuthenticationPrincipal UsuarioDetails usuarioDetails) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(inscripcionService.actualizarProgreso(
            usuarioDetails.getId(),
            idCurso
        ));
  }
}
