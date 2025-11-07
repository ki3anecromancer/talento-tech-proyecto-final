package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.Modulo;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {

  Set<Contenido> findAllByModulo(Modulo modulo);
}
