package com.tech.academia.nexuscore.service.impl;

import com.tech.academia.nexuscore.dto.UsuarioCreateRequestDTO;
import com.tech.academia.nexuscore.dto.UsuarioResponseDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateContrasenaDTO;
import com.tech.academia.nexuscore.dto.UsuarioUpdateRequestDTO;
import com.tech.academia.nexuscore.exception.EmailDuplicadoException;
import com.tech.academia.nexuscore.exception.NombreUsuarioDuplicadoException;
import com.tech.academia.nexuscore.exception.UsuarioNoEncontradoException;
import com.tech.academia.nexuscore.mapper.UsuarioMapper;
import com.tech.academia.nexuscore.model.Usuario;
import com.tech.academia.nexuscore.repository.UsuarioRepository;
import com.tech.academia.nexuscore.service.UsuarioService;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final PasswordEncoder passwordEncoder;

  public UsuarioServiceImpl(
      UsuarioRepository usuarioRepository,
      UsuarioMapper usuarioMapper,
      PasswordEncoder passwordEncoder) {

    this.usuarioRepository = usuarioRepository;
    this.usuarioMapper = usuarioMapper;
    this.passwordEncoder = passwordEncoder;
  }

  // Obtener todos los usuarios
  @Override
  public List<UsuarioResponseDTO> obtenerTodosLosUsuarios() {

    return usuarioRepository.findAll().stream()
        .map(usuarioMapper::usuarioToResponseDto)
        .toList();
  }

  // Obtener usuario por ID
  @Override
  public UsuarioResponseDTO obtenerUsuarioPorId(Long id) {

    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
        new UsuarioNoEncontradoException(id));

    return usuarioMapper.usuarioToResponseDto(usuario);
  }

  // Crear usuario
  @Override
  public UsuarioResponseDTO crearUsuario(UsuarioCreateRequestDTO requestDTO) {

    if (usuarioRepository.existsByNombreUsuario(requestDTO.nombreUsuario())) {
      throw new NombreUsuarioDuplicadoException();
    }

    if (usuarioRepository.existsByEmail(requestDTO.email())) {
      throw new EmailDuplicadoException();
    }

    Usuario usuario = usuarioMapper.createDtoToUsuario(requestDTO);

    String contrasenaHasheada = passwordEncoder.encode(requestDTO.contrasena());

    usuario.setContrasena(contrasenaHasheada);

    return usuarioMapper.usuarioToResponseDto(
        usuarioRepository.save(usuario)
    );
  }

  // Actualizar usuario (sin contraseña)
  @Override
  public UsuarioResponseDTO actualizarUsuario(Long id, UsuarioUpdateRequestDTO requestDTO) {

    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
        new UsuarioNoEncontradoException(id));

    if (!usuario.getNombreUsuario().equalsIgnoreCase(requestDTO.nombreUsuario()) &&
        usuarioRepository.existsByNombreUsuario(requestDTO.nombreUsuario())) {

      throw new NombreUsuarioDuplicadoException();
    }

    if (!usuario.getEmail().equalsIgnoreCase(requestDTO.email()) &&
        usuarioRepository.existsByEmail(requestDTO.email())) {

      throw new EmailDuplicadoException();
    }

    usuarioMapper.actualizarUsuario(usuario, requestDTO);

    return usuarioMapper.usuarioToResponseDto(
        usuarioRepository.save(usuario)
    );
  }

  // Actualizar contraseña
  @Override
  public UsuarioResponseDTO actualizarContrasena(Long id, UsuarioUpdateContrasenaDTO contrasenaDTO) {

    Usuario usuario = usuarioRepository.findById(id).orElseThrow(() ->
        new UsuarioNoEncontradoException(id));

    String contrasenaHasheada = passwordEncoder.encode(contrasenaDTO.contrasena());

    usuario.setContrasena(contrasenaHasheada);

    return usuarioMapper.usuarioToResponseDto(
        usuarioRepository.save(usuario)
    );
  }

  // Eliminar usuario
  @Override
  public void eliminarUsuario(Long id) {

    if (!usuarioRepository.existsById(id)) {
      throw new UsuarioNoEncontradoException(id);
    }

    usuarioRepository.deleteById(id);
  }
}
