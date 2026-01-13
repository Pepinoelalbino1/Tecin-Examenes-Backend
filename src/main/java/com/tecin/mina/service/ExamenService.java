package com.tecin.mina.service;

import com.tecin.mina.model.Examen;
import com.tecin.mina.model.Usuario;
import com.tecin.mina.model.dto.ExamenDTO;
import com.tecin.mina.repository.ExamenRepository;
import com.tecin.mina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExamenService {
    
    @Autowired
    private ExamenRepository examenRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<ExamenDTO> findByUsuarioId(Long usuarioId) {
        return examenRepository.findByUsuarioId(usuarioId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public ExamenDTO findById(Long id) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con id: " + id));
        return toDTO(examen);
    }
    
    public ExamenDTO create(ExamenDTO examenDTO) {
        Usuario usuario = usuarioRepository.findById(examenDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + examenDTO.getUsuarioId()));
        
        if (examenDTO.getFechaEmision().isAfter(examenDTO.getFechaCaducidad())) {
            throw new RuntimeException("La fecha de emisión no puede ser posterior a la fecha de caducidad");
        }
        
        Examen examen = new Examen();
        examen.setUsuario(usuario);
        examen.setTipoExamen(examenDTO.getTipoExamen());
        examen.setFechaEmision(examenDTO.getFechaEmision());
        examen.setFechaCaducidad(examenDTO.getFechaCaducidad());
        examen.setObservaciones(examenDTO.getObservaciones());
        
        examen.calcularEstado();
        examen = examenRepository.save(examen);
        
        return toDTO(examen);
    }
    
    public ExamenDTO update(Long id, ExamenDTO examenDTO) {
        Examen examen = examenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Examen no encontrado con id: " + id));
        
        if (examenDTO.getFechaEmision().isAfter(examenDTO.getFechaCaducidad())) {
            throw new RuntimeException("La fecha de emisión no puede ser posterior a la fecha de caducidad");
        }
        
        examen.setTipoExamen(examenDTO.getTipoExamen());
        examen.setFechaEmision(examenDTO.getFechaEmision());
        examen.setFechaCaducidad(examenDTO.getFechaCaducidad());
        examen.setObservaciones(examenDTO.getObservaciones());
        
        examen.calcularEstado();
        examen = examenRepository.save(examen);
        
        return toDTO(examen);
    }
    
    public void delete(Long id) {
        if (!examenRepository.existsById(id)) {
            throw new RuntimeException("Examen no encontrado con id: " + id);
        }
        examenRepository.deleteById(id);
    }
    
    private ExamenDTO toDTO(Examen examen) {
        examen.calcularEstado();
        return new ExamenDTO(
                examen.getId(),
                examen.getUsuario().getId(),
                examen.getTipoExamen(),
                examen.getFechaEmision(),
                examen.getFechaCaducidad(),
                examen.getObservaciones(),
                examen.getEstado()
        );
    }
}
