package com.tech.academia.nexuscore.mapper;

import com.tech.academia.nexuscore.dto.ModuloCreateRequestDTO;
import com.tech.academia.nexuscore.dto.ModuloResponseDTO;
import com.tech.academia.nexuscore.dto.ModuloUpdateRequestDTO;
import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Modulo;
import java.util.HashSet;
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

  // ModuloCreateRequestDTO -> Modulo
  public Modulo createDtoToModulo(ModuloCreateRequestDTO createDto, Curso curso) {

    return new Modulo(
        null,
        createDto.titulo(),
        createDto.orden(),
        curso,
        new HashSet<>()
    );
  }

  // Actualizar Modulo
  public void actualizarModulo(Modulo modulo, ModuloUpdateRequestDTO updateDto) {

    modulo.setTitulo(updateDto.titulo());
    modulo.setOrden(updateDto.orden());
  }
}
