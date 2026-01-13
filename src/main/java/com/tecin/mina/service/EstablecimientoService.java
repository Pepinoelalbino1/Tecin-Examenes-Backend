package com.tecin.mina.service;

import com.tecin.mina.model.Establecimiento;
import com.tecin.mina.model.ExamenRequerido;
import com.tecin.mina.model.TipoExamen;
import com.tecin.mina.model.dto.EstablecimientoDTO;
import com.tecin.mina.model.dto.TipoExamenDTO;
import com.tecin.mina.repository.EstablecimientoRepository;
import com.tecin.mina.repository.ExamenRequeridoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstablecimientoService {
    
    @Autowired
    private EstablecimientoRepository establecimientoRepository;
    
    @Autowired
    private ExamenRequeridoRepository examenRequeridoRepository;
    
    public List<EstablecimientoDTO> findAll() {
        return establecimientoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
    
    public EstablecimientoDTO findById(Long id) {
        Establecimiento establecimiento = establecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con id: " + id));
        return toDTO(establecimiento);
    }
    
    public EstablecimientoDTO create(EstablecimientoDTO establecimientoDTO) {
        Establecimiento establecimiento = new Establecimiento();
        establecimiento.setNombre(establecimientoDTO.getNombre());
        establecimiento.setDescripcion(establecimientoDTO.getDescripcion());
        establecimiento.setUbicacion(establecimientoDTO.getUbicacion());
        
        establecimiento = establecimientoRepository.save(establecimiento);
        
        if (establecimientoDTO.getExamenesRequeridos() != null) {
            for (TipoExamenDTO tipoExamenDTO : establecimientoDTO.getExamenesRequeridos()) {
                ExamenRequerido examenRequerido = new ExamenRequerido();
                examenRequerido.setEstablecimiento(establecimiento);
                examenRequerido.setTipoExamen(tipoExamenDTO.getTipoExamen());
                examenRequerido.setObservaciones(tipoExamenDTO.getObservaciones());
                examenRequeridoRepository.save(examenRequerido);
            }
        }
        
        return toDTO(establecimiento);
    }
    
    public EstablecimientoDTO update(Long id, EstablecimientoDTO establecimientoDTO) {
        Establecimiento establecimiento = establecimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con id: " + id));
        
        establecimiento.setNombre(establecimientoDTO.getNombre());
        establecimiento.setDescripcion(establecimientoDTO.getDescripcion());
        establecimiento.setUbicacion(establecimientoDTO.getUbicacion());
        
        establecimiento = establecimientoRepository.save(establecimiento);
        
        // Eliminar todos los exámenes requeridos existentes
        List<ExamenRequerido> examenesExistentes = examenRequeridoRepository.findByEstablecimientoId(id);
        examenRequeridoRepository.deleteAll(examenesExistentes);
        examenRequeridoRepository.flush(); // Asegurar que las eliminaciones se completen
        
        // Validar que no haya duplicados en la lista de exámenes requeridos
        if (establecimientoDTO.getExamenesRequeridos() != null) {
            Set<TipoExamen> tiposVistos = new HashSet<>();
            for (TipoExamenDTO tipoExamenDTO : establecimientoDTO.getExamenesRequeridos()) {
                if (tiposVistos.contains(tipoExamenDTO.getTipoExamen())) {
                    throw new RuntimeException("No se puede tener el mismo tipo de examen duplicado: " + tipoExamenDTO.getTipoExamen());
                }
                tiposVistos.add(tipoExamenDTO.getTipoExamen());
                
                ExamenRequerido examenRequerido = new ExamenRequerido();
                examenRequerido.setEstablecimiento(establecimiento);
                examenRequerido.setTipoExamen(tipoExamenDTO.getTipoExamen());
                examenRequerido.setObservaciones(tipoExamenDTO.getObservaciones());
                examenRequeridoRepository.save(examenRequerido);
            }
        }
        
        return toDTO(establecimiento);
    }
    
    public void delete(Long id) {
        if (!establecimientoRepository.existsById(id)) {
            throw new RuntimeException("Establecimiento no encontrado con id: " + id);
        }
        establecimientoRepository.deleteById(id);
    }
    
    private EstablecimientoDTO toDTO(Establecimiento establecimiento) {
        List<TipoExamenDTO> examenesRequeridos = examenRequeridoRepository
                .findByEstablecimientoId(establecimiento.getId())
                .stream()
                .map(er -> new TipoExamenDTO(er.getTipoExamen(), er.getObservaciones()))
                .collect(Collectors.toList());
        
        return new EstablecimientoDTO(
                establecimiento.getId(),
                establecimiento.getNombre(),
                establecimiento.getDescripcion(),
                establecimiento.getUbicacion(),
                examenesRequeridos
        );
    }
}
