package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Inscripcion;
import com.tech.academia.nexuscore.model.Usuario;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

  boolean existsByUsuarioAndCurso(Usuario usuario, Curso curso);

  Set<Inscripcion> findAllByUsuario(Usuario usuario);

  Optional<Inscripcion> findByUsuarioAndCurso(Usuario usuario, Curso curso);

  @Query("SELECT i FROM Inscripcion i JOIN FETCH i.usuario u WHERE i.curso.id = :idCurso")
  Set<Inscripcion> findByCursoIdWithUsuario(@Param("idCurso") Long idCurso);
}
