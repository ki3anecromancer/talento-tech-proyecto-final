package com.tech.academia.nexuscore.security.jwt;

import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.security.userdetails.UsuarioDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class JwtTokenProvider {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.expiration-ms}")
  private long jwtExpirationMs;

  private SecretKey secretKey;

  @PostConstruct
  private void init() {
    this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  // 💡 Métod base para generar JWT a partir de UsuarioDetails
  public String generarToken(Authentication authentication) {

    // 💡 Aquí reside la corrección: SOLO se usa este métod después del login.
    UsuarioDetails usuarioPrincipal = (UsuarioDetails) authentication.getPrincipal();

    List<String> roles = usuarioPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .toList();

    Date ahora = new Date();
    Date fechaExpiracion = new Date(ahora.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .subject(usuarioPrincipal.getId().toString())
        .issuedAt(ahora)
        .expiration(fechaExpiracion)
        .claim("roles", roles)
        .claim("username", usuarioPrincipal.getUsername())
        .signWith(this.secretKey)
        .compact();
  }

  // 💡 Métod para regenerar token (cuando cambia el rol). EVITA LLAMAR generarToken(Authentication)
  public String generarToken(Usuario usuario) {

    List<String> roles = usuario.getRoles().stream()
        .map(rol -> "ROLE_" + rol.name())
        .toList();

    Date ahora = new Date();
    Date fechaExpiracion = new Date(ahora.getTime() + jwtExpirationMs);

    return Jwts.builder()
        .subject(usuario.getId().toString())
        .issuedAt(ahora)
        .expiration(fechaExpiracion)
        .claim("roles", roles)
        .claim("username", usuario.getNombreUsuario()) // Usamos nombreUsuario de la entidad
        .signWith(this.secretKey)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(this.secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    String idUsuario = claims.getSubject();

    // Manejo robusto del claim 'roles' que Jackson puede devolver como ArrayList<String> o String[]
    List<String> roles = new ArrayList<>();
    Object rolesClaim = claims.get("roles");

    if (rolesClaim instanceof List<?> list) {
      list.stream().map(Object::toString).forEach(roles::add);
    } else if (rolesClaim != null) {
      roles.add(rolesClaim.toString());
    }

    Collection<? extends GrantedAuthority> authorities = roles.stream()
        .map(SimpleGrantedAuthority::new)
        .collect(Collectors.toList());

    return new UsernamePasswordAuthenticationToken(idUsuario, null, authorities);
  }

  public Long obtenerIdDeJWT(String token) {
    Claims claims = Jwts.parser()
        .verifyWith(this.secretKey)
        .build()
        .parseSignedClaims(token)
        .getPayload();

    return Long.parseLong(claims.getSubject());
  }

  public boolean validarToken(String token) {
    try {
      Jwts.parser()
          .verifyWith(this.secretKey)
          .build()
          .parse(token);
      return true;
    } catch (SignatureException e) {
      log.error("Firma JWT inválida: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Token JWT malformado: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("Token JWT expirado: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT no soportado: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("Cadena JWT vacía o nula: {}", e.getMessage());
    }
    return false;
  }
}