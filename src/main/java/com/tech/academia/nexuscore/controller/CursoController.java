package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.CursoCreateRequestDTO;
import com.tech.academia.nexuscore.dto.CursoCreateResponseDTO;
import com.tech.academia.nexuscore.dto.CursoResponseDTO;
import com.tech.academia.nexuscore.dto.CursoUpdateRequestDTO;
import com.tech.academia.nexuscore.service.CursoService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

  private final CursoService cursoService;

  // Obtener todos los cursos
  @GetMapping
  public ResponseEntity<List<CursoResponseDTO>> obtenerTodosLosCursos() {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cursoService.obtenerTodosLosCursos());
  }

  // Obtener curso por ID
  @GetMapping("/{id}")
  public ResponseEntity<CursoResponseDTO> obtenerCursoPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cursoService.obtenerCursoPorId(id));
  }

  // Crear curso
  /*
  @PostMapping("/{id}")
  public ResponseEntity<CursoResponseDTO> crearCurso(
      @Valid @RequestBody CursoCreateRequestDTO createDto,
      @PathVariable Long idUsuario) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(cursoService.crearCurso(createDto, idUsuario));
  }
   */

  // ========================================================
  //                SECURITY CONTROLLER
  // ========================================================

  // Crear curso
  @PostMapping
  public ResponseEntity<CursoCreateResponseDTO> crearCursoLogueado(
      @Valid @RequestBody CursoCreateRequestDTO createDto,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(cursoService.crearCurso(createDto, idUsuario));
  }

  // Obtener cursos por usuario
  @GetMapping("/me")
  public ResponseEntity<List<CursoResponseDTO>> obtenerCursosPorUsuario(
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cursoService.obtenerCursosPorUsuario(idUsuario));
  }

  // Actualizar curso
  @PutMapping("/{idCurso}")
  public ResponseEntity<CursoResponseDTO> actualizarCurso(
      @PathVariable Long idCurso,
      @Valid @RequestBody CursoUpdateRequestDTO updateDto,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(cursoService.actualizarCurso(idCurso, idUsuario, updateDto));
  }

  // Eliminar curso
  @DeleteMapping("/{idCurso}")
  public ResponseEntity<Void> eliminarCurso(
      @PathVariable Long idCurso,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    cursoService.eliminarCurso(idCurso, idUsuario);

    return ResponseEntity.noContent().build();
  }
}

