

-- Insertar categorías
INSERT INTO `categorias` VALUES
(2,'Estudio'),
(3,'Personal'),
(4,'Salud'),
(5,'Compras'),
(6,'Proyectos'),
(7,'Familia'),
(8,'Finanzas');

/*(usuarioid = 1)*/
INSERT INTO `tareas` (`titulo`, `descripcion`, `estado`, `fecha`, `fecha_creacion`, `hora_inicio`, `hora_fin`, `prioridad`, `recordatorio_activo`, `hora_recordatorio`, `categoriaid`, `usuarioid`) VALUES
(1,'Reunión equipo proyecto', 'Revisar avances del proyecto Alpha con el equipo de desarrollo', 'Pendiente', '2024-11-25', '2024-11-24 08:00:00.000000', '09:00:00', '10:30:00', 'Alta', b'1', '08:30:00', 1, 1),
(1,'Estudiar base de datos', 'Repasar conceptos de normalización y consultas SQL avanzadas', 'Pendiente', '2024-11-26', '2024-11-24 10:00:00.000000', '16:00:00', '18:00:00', 'Media', b'1', '15:45:00', 2, 1),
(1,'Ir al gimnasio', 'Rutina de cardio y pesas - 45 minutos', 'Completado', '2024-11-24', '2024-11-23 19:00:00.000000', '19:00:00', '19:45:00', 'Baja', b'0', NULL, 4, 1);

/*(usuarioid = 2)*/
INSERT INTO `tareas` (`titulo`, `descripcion`, `estado`, `fecha`, `fecha_creacion`, `hora_inicio`, `hora_fin`, `prioridad`, `recordatorio_activo`, `hora_recordatorio`, `categoriaid`, `usuarioid`) VALUES
(2,'Presentación cliente', 'Preparar presentación para reunión con cliente importante', 'Pendiente', '2024-11-26', '2024-11-24 09:30:00.000000', '14:00:00', '15:30:00', 'Alta', b'1', '13:30:00', 1, 2),
(2,'Comprar supermercado', 'Lista: leche, pan, frutas, verduras y productos de limpieza', 'Pendiente', '2024-11-25', '2024-11-24 18:00:00.000000', '18:30:00', '19:30:00', 'Media', b'1', '17:45:00', 5, 2),
(2,'Pagar recibos', 'Pagar servicios: luz, agua e internet', 'Completado', '2024-11-24', '2024-11-23 20:00:00.000000', NULL, NULL, 'Alta', b'0', NULL, 8, 2);

/*(usuarioid = 3)*/
INSERT INTO `tareas` (`titulo`, `descripcion`, `estado`, `fecha`, `fecha_creacion`, `hora_inicio`, `hora_fin`, `prioridad`, `recordatorio_activo`, `hora_recordatorio`, `categoriaid`, `usuarioid`) VALUES
(3,'Examen matemáticas', 'Estudiar capítulos 5-8 para examen final', 'Pendiente', '2024-11-27', '2024-11-24 11:00:00.000000', '10:00:00', '13:00:00', 'Alta', b'1', '09:30:00', 2, 3),
(3,'Cita médico', 'Control anual con el médico de cabecera', 'Pendiente', '2024-11-25', '2024-11-24 15:00:00.000000', '11:00:00', '12:00:00', 'Media', b'1', '10:30:00', 4, 3),
(3,'Llamar a padres', 'Llamada semual a padres - actualizar noticias familiares', 'Completado', '2024-11-23', '2024-11-22 19:00:00.000000', '20:00:00', '20:30:00', 'Baja', b'0', NULL, 7, 3);
