package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

  boolean existsByEmail(String email);

  boolean existsByNombreUsuario(String nombreUsuario);

  Optional<Usuario> findByNombreUsuarioOrEmail(String nombreUsuario, String email);

  Optional<Usuario> findById(Long id);
}
