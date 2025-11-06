package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.ProgresoContenido;
import com.tech.academia.nexuscore.model.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgresoContenidoRepository extends JpaRepository<ProgresoContenido, Long> {

  Optional<ProgresoContenido> findByUsuarioAndContenido(Usuario usuario, Contenido contenido);
}
