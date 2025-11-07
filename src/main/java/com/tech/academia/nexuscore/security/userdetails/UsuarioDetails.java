package com.tech.academia.nexuscore.security.userdetails;

import com.tech.academia.nexuscore.model.Usuario;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioDetails implements UserDetails {

  @Getter
  private final Long id;
  private final String nombreUsuario;
  private final String contrasena;
  private final String email;
  private final Collection<? extends GrantedAuthority> authorities;

  public UsuarioDetails(Usuario usuario) {
    this.id = usuario.getId();
    this.nombreUsuario = usuario.getNombreUsuario();
    this.contrasena = usuario.getContrasena();
    this.email = usuario.getEmail();

    this.authorities = usuario.getRoles().stream()
        .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.name()))
        .collect(Collectors.toSet());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return contrasena;
  }

  @Override
  public String getUsername() {
    return nombreUsuario;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
