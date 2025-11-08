package com.tech.academia.nexuscore.security.auth;

import com.tech.academia.nexuscore.dto.UsuarioCreateRequestDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.security.auth.dto.JwtAuthResponseDTO;
import com.tech.academia.nexuscore.security.auth.dto.LoginRequestDTO;
import com.tech.academia.nexuscore.security.jwt.JwtTokenProvider;
import com.tech.academia.nexuscore.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UsuarioService usuarioService;
  // Si tienes un servicio de registro, también lo inyectarías aquí

  /**
   * Endpoint para iniciar sesión y obtener el token JWT.
   * POST /api/auth/login
   */
  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponseDTO> authenticateUser(
      @Valid @RequestBody LoginRequestDTO loginRequest) {

    // 1. Autenticar las credenciales del usuario
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.usernameOrEmail(),
            loginRequest.contrasena()
        )
    );

    // 2. Establecer la autenticación en el contexto de seguridad (opcional, pero buena práctica)
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 3. Generar el token JWT
    String token = tokenProvider.generarToken(authentication);

    // 4. Devolver la respuesta al cliente
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new JwtAuthResponseDTO(token, "Bearer"));
  }

  // Crear usuario
  @PostMapping("/register")
  public ResponseEntity<UsuarioResponseDTO> crearUsuario(
      @Valid @RequestBody UsuarioCreateRequestDTO requestDTO) {

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(usuarioService.crearUsuario(requestDTO));
  }
}
