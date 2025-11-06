package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.model.Modulo;
import org.springframework.stereotype.Component;

@Component
public class ModuloMapper {

  // Modulo -> ModuloResponseDTO
  public ModuloResponseDTO moduloToResponseDto(Modulo modulo) {

    return new ModuloResponseDTO(
        modulo.getId(),
        modulo.getTitulo(),
        modulo.getOrden()
    );
  }
}
