package com.tecin.mina.service;

import com.tecin.mina.model.Usuario;
import com.tecin.mina.model.dto.UsuarioDTO;
import com.tecin.mina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public UsuarioDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return toDTO(usuario);
    }
    
    public UsuarioDTO findByDocumento(String documento) {
        Usuario usuario = usuarioRepository.findByDocumento(documento)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con documento: " + documento));
        return toDTO(usuario);
    }
    
    public UsuarioDTO create(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByDocumento(usuarioDTO.getDocumento())) {
            throw new RuntimeException("Ya existe un usuario con el documento: " + usuarioDTO.getDocumento());
        }
        
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setEmail(usuarioDTO.getEmail());
        
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }
    
    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        
        if (!usuario.getDocumento().equals(usuarioDTO.getDocumento()) && 
            usuarioRepository.existsByDocumento(usuarioDTO.getDocumento())) {
            throw new RuntimeException("Ya existe un usuario con el documento: " + usuarioDTO.getDocumento());
        }
        
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setDocumento(usuarioDTO.getDocumento());
        usuario.setEmail(usuarioDTO.getEmail());
        
        usuario = usuarioRepository.save(usuario);
        return toDTO(usuario);
    }
    
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
    
    private UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getDocumento(),
                usuario.getEmail()
        );
    }
}
