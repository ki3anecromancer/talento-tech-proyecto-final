package com.tech.academia.nexuscore.security.jwt;

import com.tech.academia.nexuscore.security.userdetails.UsuarioDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration-ms}")
  private long jwtExpirationMs;

  // Metodo para generar la clave de firma (key)
  private SecretKey key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  /**
   * Genera un JWT para un usuario autenticado.
   */
  public String generarToken(Authentication authentication) {

    // Obtener el objeto principal de seguridad (UsuarioDetails)
    UsuarioDetails usuarioPrincipal = (UsuarioDetails) authentication.getPrincipal();

    Date ahora = new Date();
    Date fechaExpiracion = new Date(ahora.getTime() + jwtExpirationMs);

    // Usar la ID como Subject (identificado único)
    String idUsuario = usuarioPrincipal.getId().toString();

    return Jwts.builder()
        .subject(usuarioPrincipal.getId().toString())
        .issuedAt(ahora)
        .expiration(fechaExpiracion)
        .claim("username", usuarioPrincipal.getUsername())
        .signWith(key())
        .compact();
  }

  /**
   * Extrae el ID del usuario del token (el Subject)
   */
  public Long obtenerIdDeJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return Long.parseLong(claims.getSubject());
  }

  /**
   * Verificar la integridad y expiración del token
   */
  public boolean validarToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(key())
          .build()
          .parse(token);
      return true;
    } catch (SignatureException e) {
      // Log: Firma JWT inválida
    } catch (MalformedJwtException e) {
      // Log: Token JWT malformado
    } catch (ExpiredJwtException e) {
      // Log: Token JWT expirado
    } catch (UnsupportedJwtException e) {
      // Log: JWT no soportado
    } catch (IllegalArgumentException e) {
      // Log: Cadena JWT vacía
    }
    return false;
  }
}
