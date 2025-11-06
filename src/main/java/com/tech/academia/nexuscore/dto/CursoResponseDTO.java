package com.tech.academia.nexuscore.dto;

import java.math.BigDecimal;
import java.util.Set;

public record CursoResponseDTO (
    Long id,
    String titulo,
    String descripcion,
    Integer duracionHoras,
    BigDecimal precio,
    Set<ModuloResponseDTO> modulos
) {}