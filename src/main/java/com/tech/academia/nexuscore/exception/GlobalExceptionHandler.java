package com.tech.academia.nexuscore.exception;

import com.tech.academia.nexuscore.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(UsuarioNoEncontradoException.class)
  public ResponseEntity<ErrorResponseDTO> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.NOT_FOUND,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(responseDTO);
  }

  @ExceptionHandler(EmailDuplicadoException.class)
  public ResponseEntity<ErrorResponseDTO> handleEmailDuplicado(EmailDuplicadoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.CONFLICT,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(responseDTO);
  }

  @ExceptionHandler(NombreUsuarioDuplicadoException.class)
  public ResponseEntity<ErrorResponseDTO> handleNombreUsuarioDuplicado(NombreUsuarioDuplicadoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.CONFLICT,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(responseDTO);
  }

  @ExceptionHandler(CursoNoEncontradoException.class)
  public ResponseEntity<ErrorResponseDTO> handleCursoNoEncontrado(CursoNoEncontradoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.NOT_FOUND,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(responseDTO);
  }

  @ExceptionHandler(UsuarioYaInscriptoException.class)
  public ResponseEntity<ErrorResponseDTO> handleUsuarioYaInscripto(UsuarioYaInscriptoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.CONFLICT,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.CONFLICT)
        .body(responseDTO);
  }

  @ExceptionHandler(InscripcionNoEncontradaException.class)
  public ResponseEntity<ErrorResponseDTO> handleInscripcionNoEncontrada(InscripcionNoEncontradaException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.NOT_FOUND,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(responseDTO);
  }

  @ExceptionHandler(ProgresoContenidoNoEncontradoException.class)
  public ResponseEntity<ErrorResponseDTO> handleProgresoContenidoNoEncontrado(ProgresoContenidoNoEncontradoException ex) {

    ErrorResponseDTO responseDTO = new ErrorResponseDTO(
        HttpStatus.NOT_FOUND,
        ex.getMessage()
    );

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(responseDTO);
  }
}
