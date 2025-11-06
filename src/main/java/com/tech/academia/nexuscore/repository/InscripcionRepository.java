package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Inscripcion;
import com.tech.academia.nexuscore.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

  boolean existsByUsuarioAndCurso(Usuario usuario, Curso curso);
}
