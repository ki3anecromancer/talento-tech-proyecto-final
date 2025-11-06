package com.tech.academia.nexuscore.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponseDTO(
    int status,
    String error,
    String message
) {

  public ErrorResponseDTO(HttpStatus status, String message) {
    this(status.value(), status.getReasonPhrase(), message);
  }
}
