// Estado global
let currentTab = 'calendario';
let recordatoriosMostrados = new Set(); // Para evitar mostrar el mismo recordatorio múltiples veces

// Inicializar cuando se carga la página
document.addEventListener('DOMContentLoaded', function() {
    // Cargar pestaña inicial
    loadTab('tareas');

    // Configurar eventos de tabs
    const tabButtons = document.querySelectorAll('.tab-btn');
    tabButtons.forEach(btn => {
        btn.addEventListener('click', function() {
            const tab = this.getAttribute('data-tab');
            switchTab(tab);
        });
    });

    // Configurar modal
    setupModal();

    // Configurar formulario de nueva tarea
    setupFormNuevaTarea();

    // Solicitar permisos para notificaciones
    solicitarPermisosNotificacion();

    // Iniciar verificación de recordatorios cada minuto
    verificarRecordatorios();
    setInterval(verificarRecordatorios, 60000); // Verificar cada 60 segundos
});

// Cambiar de pestaña
function switchTab(tabName) {
    // Actualizar botones
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');
    
    // Cargar contenido
    loadTab(tabName);
}

// Cargar contenido de pestaña
function loadTab(tabName) {
    currentTab = tabName;
    const contentArea = document.getElementById('content-area');
    
    // Mostrar loading
    contentArea.innerHTML = '<div style="text-align: center; padding: 50px;"><h3>Cargando...</h3></div>';
    
    // Cargar contenido via AJAX
    fetch(`/dashboard/${tabName}`)
        .then(response => response.text())
        .then(html => {
            contentArea.innerHTML = html;
        })
        .catch(error => {
            console.error('Error:', error);
            contentArea.innerHTML = '<div style="text-align: center; padding: 50px; color: red;"><h3>Error al cargar contenido</h3></div>';
        });
}

// Configurar modal
function setupModal() {
    const modal = document.getElementById('modal-nueva-tarea');
    const closeBtn = modal.querySelector('.close');
    
    closeBtn.onclick = function() {
        cerrarModal();
    };
    
    window.onclick = function(event) {
        if (event.target == modal) {
            cerrarModal();
        }
    };
}

// Abrir modal
function abrirModalNuevaTarea() {
    document.getElementById('modal-nueva-tarea').style.display = 'block';
}

// Cerrar modal
function cerrarModal() {
    document.getElementById('modal-nueva-tarea').style.display = 'none';
    document.getElementById('form-nueva-tarea').reset();
}

// Configurar formulario de nueva tarea
function setupFormNuevaTarea() {
    const form = document.getElementById('form-nueva-tarea');
    
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const formData = {
            titulo: document.getElementById('titulo').value,
            descripcion: document.getElementById('descripcion').value,
            categoriaId: document.getElementById('categoriaId').value,
            fecha: document.getElementById('fecha').value,
            horaFin: document.getElementById('horaFin').value,
            prioridad: document.getElementById('prioridad').value
        };
        
        fetch('/api/tareas', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(formData)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                alert('Tarea creada exitosamente');
                cerrarModal();
                loadTab(currentTab); // Recargar pestaña actual
            } else {
                alert('Error: ' + data.message);
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Error al crear tarea');
        });
    });
}

// Completar tarea
function completarTarea(id) {
    if (!confirm('¿Desea marcar esta tarea como completada?')) {
        return;
    }
    
    fetch(`/api/tareas/${id}/completar`, {
        method: 'PUT'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Tarea completada');
            loadTab(currentTab);
        } else {
            alert('Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al completar tarea');
    });
}

// Eliminar tarea
function eliminarTarea(id) {
    if (!confirm('¿Está seguro que desea eliminar esta tarea?')) {
        return;
    }
    
    fetch(`/api/tareas/${id}`, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Tarea eliminada');
            loadTab(currentTab);
        } else {
            alert('Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al eliminar tarea');
    });
}

// Activar recordatorio
function activarRecordatorio(id) {
    const hora = prompt('Ingrese la hora del recordatorio (HH:mm):');
    
    if (!hora) return;
    
    fetch(`/api/tareas/${id}/recordatorio`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ hora: hora })
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Recordatorio activado');
            loadTab(currentTab);
        } else {
            alert('Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al activar recordatorio');
    });
}

// Desactivar recordatorio
function desactivarRecordatorio(id) {
    if (!confirm('¿Desea desactivar el recordatorio?')) {
        return;
    }

    fetch(`/api/tareas/${id}/recordatorio`, {
        method: 'DELETE'
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            alert('Recordatorio desactivado');
            loadTab(currentTab);
        } else {
            alert('Error: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('Error al desactivar recordatorio');
    });
}

// ==================== SISTEMA DE NOTIFICACIONES ====================

// Solicitar permisos para notificaciones del navegador
function solicitarPermisosNotificacion() {
    if ('Notification' in window && Notification.permission === 'default') {
        Notification.requestPermission();
    }
}

// Verificar recordatorios activos
function verificarRecordatorios() {
    fetch('/api/tareas/recordatorios-activos')
        .then(response => response.json())
        .then(data => {
            if (data.success && data.tareas) {
                const ahora = new Date();
                const horaActual = ahora.getHours().toString().padStart(2, '0') + ':' +
                                  ahora.getMinutes().toString().padStart(2, '0');

                data.tareas.forEach(tarea => {
                    if (tarea.horaRecordatorio === horaActual) {
                        const claveRecordatorio = `${tarea.id}-${tarea.fecha}-${horaActual}`;

                        // Solo mostrar si no se ha mostrado ya hoy
                        if (!recordatoriosMostrados.has(claveRecordatorio)) {
                            mostrarNotificacion(tarea);
                            recordatoriosMostrados.add(claveRecordatorio);
                        }
                    }
                });
            }
        })
        .catch(error => {
            console.error('Error al verificar recordatorios:', error);
        });
}

// Mostrar notificación
function mostrarNotificacion(tarea) {
    // Reproducir sonido
    reproducirSonidoNotificacion();

    // Mostrar notificación visual en pantalla
    mostrarAlertaVisual(tarea);

    // Mostrar notificación del navegador si está permitido
    if ('Notification' in window && Notification.permission === 'granted') {
        const notification = new Notification('Recordatorio de Tarea', {
            body: `${tarea.titulo}\n${tarea.descripcion}\nHora: ${tarea.horaFin || 'Sin hora especificada'}`,
            icon: '/favicon.ico',
            badge: '/favicon.ico',
            tag: `tarea-${tarea.id}`,
            requireInteraction: true
        });

        notification.onclick = function() {
            window.focus();
            notification.close();
        };
    }
}

// Reproducir sonido de notificación
function reproducirSonidoNotificacion() {
    // Crear un tono simple usando Web Audio API
    const audioContext = new (window.AudioContext || window.webkitAudioContext)();
    const oscillator = audioContext.createOscillator();
    const gainNode = audioContext.createGain();

    oscillator.connect(gainNode);
    gainNode.connect(audioContext.destination);

    oscillator.frequency.value = 800; // Frecuencia en Hz
    oscillator.type = 'sine';

    gainNode.gain.setValueAtTime(0.3, audioContext.currentTime);
    gainNode.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 0.5);

    oscillator.start(audioContext.currentTime);
    oscillator.stop(audioContext.currentTime + 0.5);

    // Segundo beep
    setTimeout(() => {
        const oscillator2 = audioContext.createOscillator();
        const gainNode2 = audioContext.createGain();

        oscillator2.connect(gainNode2);
        gainNode2.connect(audioContext.destination);

        oscillator2.frequency.value = 1000;
        oscillator2.type = 'sine';

        gainNode2.gain.setValueAtTime(0.3, audioContext.currentTime);
        gainNode2.gain.exponentialRampToValueAtTime(0.01, audioContext.currentTime + 0.5);

        oscillator2.start(audioContext.currentTime);
        oscillator2.stop(audioContext.currentTime + 0.5);
    }, 600);
}

// Mostrar alerta visual en la página
function mostrarAlertaVisual(tarea) {
    // Crear elemento de notificación
    const notificacion = document.createElement('div');
    notificacion.className = 'notificacion-tarea';
    notificacion.innerHTML = `
        <div class="notificacion-header">
            <strong>Recordatorio de Tarea</strong>
            <button class="notificacion-close" onclick="this.parentElement.parentElement.remove()">×</button>
        </div>
        <div class="notificacion-body">
            <h4>${tarea.titulo}</h4>
            <p>${tarea.descripcion}</p>
            <p><strong>Fecha:</strong> ${tarea.fecha}</p>
            <p><strong>Hora:</strong> ${tarea.horaFin || 'Sin hora especificada'}</p>
            <span class="badge priority-${tarea.prioridad.toLowerCase()}">${tarea.prioridad}</span>
        </div>
    `;

    // Agregar al body
    document.body.appendChild(notificacion);

    // Animar entrada
    setTimeout(() => {
        notificacion.classList.add('show');
    }, 10);

    // Auto-eliminar después de 10 segundos
    setTimeout(() => {
        notificacion.classList.remove('show');
        setTimeout(() => {
            notificacion.remove();
        }, 300);
    }, 10000);
}
