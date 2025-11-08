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
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
   * Genera un JWT para un usuario autenticado. (No requiere corrección)
   */
  public String generarToken(Authentication authentication) {

    UsuarioDetails usuarioPrincipal = (UsuarioDetails) authentication.getPrincipal();

    // Esto ya devuelve ["ROLE_USER", ...]
    List<String> roles = usuarioPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    Date ahora = new Date();
    Date fechaExpiracion = new Date(ahora.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .subject(usuarioPrincipal.getId().toString())
        .issuedAt(ahora)
        .expiration(fechaExpiracion)
        .claim("roles", roles) // Contiene el prefijo ROLE_
        .claim("username", usuarioPrincipal.getUsername())
        .signWith(key())
        .compact();
  }

  /**
   * Extrae el ID del usuario del token (el Subject) (No requiere corrección)
   */
  public Long obtenerIdDeJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return Long.parseLong(claims.getSubject());
  }

  // 💡 INICIO DE LA CORRECCIÓN/ADICIÓN
  /**
   * Obtiene el objeto Authentication a partir del token.
   * ESTO ES USADO POR EL FILTRO JWT para validar el 403.
   */
  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(key())
        .build()
        .parseSignedClaims(token)
        .getPayload();

    // 1. Obtener el Subject (ID de usuario)
    String idUsuario = claims.getSubject();

    // 2. Extraer los roles (ya vienen con el prefijo ROLE_)
    // Usamos .get("roles") y lo casteamos a List<String>
    List<String> roles = (List<String>) claims.get("roles");

    // 3. Crear las GrantedAuthority
    Collection<? extends GrantedAuthority> authorities = roles.stream()
        .map(SimpleGrantedAuthority::new) // Usamos los roles TAL CUAL vienen (ej: "ROLE_USER")
        .collect(Collectors.toList());

    // 4. Crear el token de autenticación
    // Usamos el ID de usuario como principal y las autoridades extraídas
    return new UsernamePasswordAuthenticationToken(idUsuario, null, authorities);
  }

  /**
   * Verificar la integridad y expiración del token (No requiere corrección)
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