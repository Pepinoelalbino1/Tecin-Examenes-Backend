package com.tecin.mina.service;

import com.tecin.mina.model.EstadoExamen;
import com.tecin.mina.model.Examen;
import com.tecin.mina.model.ExamenRequerido;
import com.tecin.mina.model.TipoExamen;
import com.tecin.mina.model.Establecimiento;
import com.tecin.mina.model.dto.AptitudDTO;
import com.tecin.mina.model.dto.ExamenAptitudDTO;
import com.tecin.mina.model.dto.MinaAptitudDTO;
import com.tecin.mina.model.dto.ResumenAptitudDTO;
import com.tecin.mina.repository.EstablecimientoRepository;
import com.tecin.mina.repository.ExamenRequeridoRepository;
import com.tecin.mina.repository.ExamenRepository;
import com.tecin.mina.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class AptitudService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EstablecimientoRepository establecimientoRepository;
    
    @Autowired
    private ExamenRequeridoRepository examenRequeridoRepository;
    
    @Autowired
    private ExamenRepository examenRepository;
    
    public AptitudDTO verificarAptitud(Long usuarioId, Long establecimientoId) {
        var usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));
        
        var establecimiento = establecimientoRepository.findById(establecimientoId)
                .orElseThrow(() -> new RuntimeException("Establecimiento no encontrado con id: " + establecimientoId));
        
        // Obtener exámenes requeridos por el establecimiento
        List<ExamenRequerido> examenesRequeridos = examenRequeridoRepository
                .findByEstablecimientoId(establecimientoId);
        
        // Obtener exámenes del usuario
        List<Examen> examenesUsuario = examenRepository.findByUsuarioId(usuarioId);
        Map<TipoExamen, Examen> examenesPorTipo = examenesUsuario.stream()
                .collect(Collectors.toMap(Examen::getTipoExamen, examen -> examen));
        
        // Verificar aptitud
        List<ExamenAptitudDTO> examenesAptitud = examenesRequeridos.stream()
                .map(er -> {
                    Examen examen = examenesPorTipo.get(er.getTipoExamen());
                    boolean presente = examen != null;
                    EstadoExamen estado = null;
                    if (presente) {
                        examen.calcularEstado();
                        estado = examen.getEstado();
                    }
                    boolean vigente = presente && estado == EstadoExamen.VIGENTE;
                    
                    return new ExamenAptitudDTO(
                            er.getTipoExamen(),
                            estado,
                            true, // requerido
                            vigente
                    );
                })
                .collect(Collectors.toList());
        
        // Calcular aptitud: todos los exámenes requeridos deben estar vigentes
        boolean apto = !examenesRequeridos.isEmpty() && 
                      examenesAptitud.stream().allMatch(ExamenAptitudDTO::isPresente);
        
        AptitudDTO aptitudDTO = new AptitudDTO();
        aptitudDTO.setUsuarioId(usuarioId);
        aptitudDTO.setEstablecimientoId(establecimientoId);
        aptitudDTO.setNombreUsuario(usuario.getNombre());
        aptitudDTO.setNombreEstablecimiento(establecimiento.getNombre());
        aptitudDTO.setApto(apto);
        aptitudDTO.setExamenes(examenesAptitud);
        
        return aptitudDTO;
    }
    
    public List<ResumenAptitudDTO> obtenerResumenAptitud() {
        List<com.tecin.mina.model.Usuario> usuarios = usuarioRepository.findAll();
        List<Establecimiento> establecimientos = establecimientoRepository.findAll();
        
        return usuarios.stream()
                .map(usuario -> {
                    List<MinaAptitudDTO> minas = establecimientos.stream()
                            .map(establecimiento -> {
                                boolean apto = verificarAptitudSimple(usuario.getId(), establecimiento.getId());
                                return new MinaAptitudDTO(
                                        establecimiento.getId(),
                                        establecimiento.getNombre(),
                                        apto
                                );
                            })
                            .collect(Collectors.toList());
                    
                    return new ResumenAptitudDTO(
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getDocumento(),
                            minas
                    );
                })
                .collect(Collectors.toList());
    }
    
    private boolean verificarAptitudSimple(Long usuarioId, Long establecimientoId) {
        List<ExamenRequerido> examenesRequeridos = examenRequeridoRepository
                .findByEstablecimientoId(establecimientoId);
        
        if (examenesRequeridos.isEmpty()) {
            return false;
        }
        
        List<Examen> examenesUsuario = examenRepository.findByUsuarioId(usuarioId);
        Map<TipoExamen, Examen> examenesPorTipo = examenesUsuario.stream()
                .collect(Collectors.toMap(Examen::getTipoExamen, examen -> examen));
        
        return examenesRequeridos.stream()
                .allMatch(er -> {
                    Examen examen = examenesPorTipo.get(er.getTipoExamen());
                    if (examen == null) {
                        return false;
                    }
                    examen.calcularEstado();
                    return examen.getEstado() == EstadoExamen.VIGENTE;
                });
    }
}
