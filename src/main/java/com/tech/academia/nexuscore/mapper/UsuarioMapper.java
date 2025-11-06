package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.UsuarioCreateRequestDTO;
import com.tech.academia.nexuscore.dto.UsuarioReferenceDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateRequestDTO;
import com.tech.academia.nexuscore.model.Usuario;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

  // UsuarioCreateRequestDTO -> Usuario
  public Usuario createDtoToUsuario(UsuarioCreateRequestDTO dto) {

    return new Usuario(
        null,
        dto.nombreUsuario(),
        dto.email(),
        dto.contrasena(),
        dto.nombre(),
        dto.apellido(),
        LocalDateTime.now(),
        null,
        null
    );
  }

  // Usuario -> UsuarioResponseDTO
  public UsuarioResponseDTO usuarioToResponseDto(Usuario usuario) {

    return new UsuarioResponseDTO(
        usuario.getId(),
        usuario.getNombreUsuario(),
        usuario.getEmail(),
        usuario.getNombre(),
        usuario.getApellido(),
        usuario.getFechaCreacion()
    );
  }

  // Actualizar Usuario
  public void actualizarUsuario(Usuario usuario, UsuarioUpdateRequestDTO updateDto) {

    usuario.setNombreUsuario(updateDto.nombreUsuario());
    usuario.setEmail(updateDto.email());
    usuario.setNombre(updateDto.nombre());
    usuario.setApellido(updateDto.apellido());
  }

  // Usuario -> UsuarioReferenceDTO
  public UsuarioReferenceDTO usuarioToReferenceDto(Usuario usuario) {

    return new UsuarioReferenceDTO(
        usuario.getId(),
        usuario.getNombreUsuario()
    );
  }
}
