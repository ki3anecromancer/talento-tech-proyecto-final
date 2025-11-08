package com.tech.academia.nexuscore.security.userdetails;

import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioDetailsService implements UserDetailsService {

  private final UsuarioRepository usuarioRepository;

  @Override
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

    Usuario usuario = usuarioRepository.findByNombreUsuarioOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException(
            "Usuario no encontrado con el nombre o email: " + usernameOrEmail));

    return new UsuarioDetails(usuario);
  }

  /** Permite cargar UserDetails directamente por ID (útil para JWT). */
  public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con ID: " + id));

    return new UsuarioDetails(usuario);
  }
}