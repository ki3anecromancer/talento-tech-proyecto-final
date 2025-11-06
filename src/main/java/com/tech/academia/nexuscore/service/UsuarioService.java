package com.tech.academia.nexuscore.service;

import com.tech.academia.nexuscore.dto.UsuarioCreateRequestDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateContrasenaDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateRequestDTO;
import java.util.List;

public interface UsuarioService {

  // Obtener todos los usuarios
  List<UsuarioResponseDTO> obtenerTodosLosUsuarios();

  // Obtener usuario por ID
  UsuarioResponseDTO obtenerUsuarioPorId(Long id);

  // Crear usuario
  UsuarioResponseDTO crearUsuario(UsuarioCreateRequestDTO requestDTO);

  // Actualizar usuario (sin contraseña)
  UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateRequestDTO requestDTO);

  // Actualizar contraseña
  UsuarioResponseDTO actualizarContrasena(Long id, UsuarioUpdateContrasenaDTO contrasenaDTO);

  // Eliminar usuario
  void eliminarUsuario(Long id);
}
