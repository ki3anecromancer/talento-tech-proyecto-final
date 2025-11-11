package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloUpdateRequestDTO;
import com.tech.academia.nexuscore.service.ModuloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

  private final ModuloService moduloService;

  // Obtener modulo por ID
  @GetMapping("/{id}")
  public ResponseEntity<ModuloResponseDTO> obtenerModuloPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(moduloService.obtenerModuloPorId(id));
  }

  // ========================================================
  //                SECURITY CONTROLLER
  // ========================================================

  // Actualizar modulo
  @PutMapping("/{idModulo}")
  public ResponseEntity<ModuloResponseDTO> actualizarModulo(
      @PathVariable Long idModulo,
      @AuthenticationPrincipal String idUsuarioString,
      @Valid @RequestBody ModuloUpdateRequestDTO updateDto) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(moduloService.actualizarModulo(idModulo, idUsuario, updateDto));
  }

  // Eliminar modulo
  @DeleteMapping("/{idModulo}")
  public ResponseEntity<Void> eliminarModulo(
      @PathVariable Long idModulo,
      @AuthenticationPrincipal String idUsuarioString) {

    Long idUsuario = Long.parseLong(idUsuarioString);

    moduloService.eliminarModulo(idModulo, idUsuario);

    return ResponseEntity.noContent().build();
  }
}
