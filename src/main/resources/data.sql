-- Script de datos iniciales para desarrollo

-- Insertar usuarios de ejemplo
INSERT INTO usuarios (nombre, documento, email) VALUES
('Juan Pérez', '12345678', 'juan.perez@example.com'),
('María González', '87654321', 'maria.gonzalez@example.com'),
('Carlos Rodríguez', '11223344', 'carlos.rodriguez@example.com');

-- Insertar establecimientos (minas) de ejemplo
INSERT INTO establecimientos (nombre, descripcion, ubicacion) VALUES
('Mina San José', 'Mina de cobre ubicada en la región norte', 'Región Norte'),
('Mina El Dorado', 'Mina de oro en la región sur', 'Región Sur'),
('Mina La Esperanza', 'Mina de plata en la región central', 'Región Central');

-- Insertar exámenes requeridos para cada establecimiento
-- Mina San José requiere: médico general, audiométrico, ocupacional
INSERT INTO examenes_requeridos (establecimiento_id, tipo_examen, observaciones) VALUES
(1, 'EXAMEN_MEDICO_GENERAL', 'Obligatorio para todos los trabajadores'),
(1, 'EXAMEN_AUDIOMETRICO', 'Requerido por exposición a ruido'),
(1, 'EXAMEN_OCUPACIONAL', 'Evaluación de capacidad laboral');

-- Mina El Dorado requiere: médico general, psicológico, toxicológico
INSERT INTO examenes_requeridos (establecimiento_id, tipo_examen, observaciones) VALUES
(2, 'EXAMEN_MEDICO_GENERAL', 'Obligatorio para todos los trabajadores'),
(2, 'EXAMEN_PSICOLOGICO', 'Evaluación psicológica obligatoria'),
(2, 'EXAMEN_TOXICOLOGICO', 'Control de sustancias');

-- Mina La Esperanza requiere: médico general, radiológico, espiración
INSERT INTO examenes_requeridos (establecimiento_id, tipo_examen, observaciones) VALUES
(3, 'EXAMEN_MEDICO_GENERAL', 'Obligatorio para todos los trabajadores'),
(3, 'EXAMEN_RADIOLOGICO', 'Control de exposición a radiación'),
(3, 'EXAMEN_ESPIRACION', 'Prueba de capacidad pulmonar');

-- Insertar exámenes de ejemplo para usuarios
-- Exámenes vigentes para Juan Pérez
INSERT INTO examenes (usuario_id, tipo_examen, fecha_emision, fecha_caducidad, observaciones) VALUES
(1, 'EXAMEN_MEDICO_GENERAL', DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_ADD(CURDATE(), INTERVAL 335 DAY), 'Apto para trabajo'),
(1, 'EXAMEN_AUDIOMETRICO', DATE_SUB(CURDATE(), INTERVAL 60 DAY), DATE_ADD(CURDATE(), INTERVAL 305 DAY), 'Audición normal');

-- Exámenes por vencer para María González
INSERT INTO examenes (usuario_id, tipo_examen, fecha_emision, fecha_caducidad, observaciones) VALUES
(2, 'EXAMEN_MEDICO_GENERAL', DATE_SUB(CURDATE(), INTERVAL 350 DAY), DATE_ADD(CURDATE(), INTERVAL 5 DAY), 'Renovar pronto'),
(2, 'EXAMEN_PSICOLOGICO', DATE_SUB(CURDATE(), INTERVAL 340 DAY), DATE_ADD(CURDATE(), INTERVAL 15 DAY), 'Apto');

-- Exámenes vencidos para Carlos Rodríguez
INSERT INTO examenes (usuario_id, tipo_examen, fecha_emision, fecha_caducidad, observaciones) VALUES
(3, 'EXAMEN_MEDICO_GENERAL', DATE_SUB(CURDATE(), INTERVAL 400 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), 'Vencido - requiere renovación'),
(3, 'EXAMEN_RADIOLOGICO', DATE_SUB(CURDATE(), INTERVAL 380 DAY), DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'Vencido');
