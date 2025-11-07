package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.ProgresoContenidoResponseDTO;
import com.tech.academia.nexuscore.exception.ContenidoNoEncontradoException;
import com.tech.academia.nexuscore.exception.CursoNoEncontradoException;
import com.tech.academia.nexuscore.exception.ProgresoContenidoNoEncontradoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.mapper.ProgresoContenidoMapper;
import com.tech.academia.nexuscore.model.Contenido;
import com.tech.academia.nexuscore.model.ProgresoContenido;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.repository.ContenidoRepository;
import com.tech.academia.nexuscore.repository.ProgresoContenidoRepository;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.service.ProgresoContenidoService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProgresoContenidoServiceImpl implements ProgresoContenidoService {

  private final ProgresoContenidoRepository progresoContenidoRepository;
  private final UsuarioRepository usuarioRepository;
  private final ContenidoRepository contenidoRepository;
  private final ProgresoContenidoMapper progresoContenidoMapper;

  // Marcar contenido como completado
  @Override
  public ProgresoContenidoResponseDTO marcarContenidoComoCompletado(Long idUsuario, Long idContenido) {

    Usuario usuario = usuarioRepository.findById(idUsuario).orElseThrow(() ->
        new UsuarioNoEncontradoException(idUsuario));

    Contenido contenido = contenidoRepository.findById(idContenido).orElseThrow(() ->
        new ContenidoNoEncontradoException(idContenido));

    ProgresoContenido progresoContenido = progresoContenidoRepository.findByUsuarioAndContenido(usuario, contenido)
        .orElseThrow(() -> new ProgresoContenidoNoEncontradoException(usuario, contenido));

    progresoContenido.setCompletado(Boolean.TRUE);
    progresoContenido.setFechaCompletado(LocalDateTime.now());

    return progresoContenidoMapper.progresoToContenidoResponseDto(
        progresoContenidoRepository.save(progresoContenido),
        usuario,
        contenido
    );
  }
}
