package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.InscripcionResponseDTO;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetails;
import com.tech.academia.nexuscore.service.InscripcionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CursoInscripcionesController {

  private final InscripcionService inscripcionService;

  // Inscribir usuario a un curso
  @PostMapping("/cursos/{idCurso}/inscripciones")
  public ResponseEntity<InscripcionResponseDTO> inscribirUsuarioACurso(
      @PathVariable Long idCurso,
      @AuthenticationPrincipal UsuarioDetails usuarioDetails) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(inscripcionService.inscribirUsuarioACurso(
            usuarioDetails.getId(),
            idCurso));
  }

  // Obtener inscripcion por ID
  @GetMapping("/inscripciones/{id}")
  public ResponseEntity<InscripcionResponseDTO> obtenerInscripcionPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(inscripcionService.obtenerInscripcionPorId(id));
  }

  // Eliminar inscripcion
  @DeleteMapping("/inscripciones/{id}")
  public ResponseEntity<Void> eliminarInscripcion(
      @PathVariable Long id) {

    inscripcionService.eliminarInscripcion(id);

    return ResponseEntity.noContent().build();
  }
}
