package com.tech.academia.nexuscore.dto;

import java.time.LocalDateTime;

public record UsuarioResponseDTO (
   Long id,
   String nombreUsuario,
   String email,
   String nombre,
   String apellido,
   LocalDateTime fechaCreacion
) {}
