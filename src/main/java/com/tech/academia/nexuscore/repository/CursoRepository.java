package com.tech.academia.nexuscore.repository;

import com.tech.academia.nexuscore.model.Curso;
import com.tech.academia.nexuscore.model.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

  List<Curso> findAllByUsuario(Usuario usuario);
}
