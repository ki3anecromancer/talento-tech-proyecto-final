package com.tech.academia.nexuscore.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CursoUpdateRequestDTO(

    @NotBlank
    @Size(min = 3, max = 50)
    String titulo,

    @NotBlank
    @Size(min = 15)
    String descripcion,

    @NotNull
    @Min(1)
    Integer duracionHoras,

    @NotNull
    @DecimalMin("0.00")
    @Digits(integer = 8, fraction = 2)
    BigDecimal precio
) {

}
