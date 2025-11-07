package com.tech.academia.nexuscore.controller;

import com.tech.academia.nexuscore.dto.UsuarioCreateRequestDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateContrasenaDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateRequestDTO;
import com.tech.academia.nexuscore.service.UsuarioService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  // Obtener todos los usuarios
  @GetMapping
  public ResponseEntity<List<UsuarioResponseDTO>> obtenerTodosLosUsuarios() {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(usuarioService.obtenerTodosLosUsuarios());
  }

  // Obtener usuario por ID
  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(
      @PathVariable Long id) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(usuarioService.obtenerUsuarioPorId(id));
  }

  // Crear usuario
  @PostMapping
  public ResponseEntity<UsuarioResponseDTO> crearUsuario(
      @Valid @RequestBody UsuarioCreateRequestDTO requestDTO) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(usuarioService.crearUsuario(requestDTO));
  }

  // Actualizar usuario (sin contraseña)
  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
      @PathVariable Long id,
      @Valid @RequestBody UsuarioUpdateRequestDTO requestDTO) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(usuarioService.actualizarUsuario(id, requestDTO));
  }

  // Actualizar contraseña
  @PatchMapping("/{id}/password")
  public ResponseEntity<UsuarioResponseDTO> actualizarContrasena(
      @PathVariable Long id,
      @Valid @RequestBody UsuarioUpdateContrasenaDTO contrasenaDTO) {

    return ResponseEntity
        .status(HttpStatus.OK)
        .body(usuarioService.actualizarContrasena(id, contrasenaDTO));
  }

  // Eliminar usuario
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarUsuario(
      @PathVariable Long id) {

    usuarioService.eliminarUsuario(id);

    return ResponseEntity.noContent().build();
  }
}
