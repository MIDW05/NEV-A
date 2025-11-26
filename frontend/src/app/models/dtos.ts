import { EstadoTarea, Prioridad, TipoIntervalo, TipoMeta } from './enums';

export interface UsuarioDTO {
  id?: number;
  nombre: string;
  email: string;
  contrasena: string;
  fechaCreacion?: string;
}

/**
 * Tarea principal del sistema.
 * El backend puede enviar más campos, pero estos son los que usamos en el front.
 */
export interface TareaDTO {
  id?: number;
  titulo: string;
  descripcion?: string;
  prioridad: Prioridad;
  // ISO string que viene del backend (yyyy-MM-dd o completo con hora)
  fechaVencimiento: string;

  // Relación con usuario (solo usamos id)
  usuario?: Partial<UsuarioDTO>;

  estado?: EstadoTarea;
  fechaCreacion?: string;
  fechaInicio?: string;
  fechaFinalizacion?: string;
  duracionEstimadaMinutos?: number;

  // Opcionalmente puedes tener proyecto, pero el front lo puede ignorar si no lo usas
  proyectoId?: number;
}

/**
 * Evento de calendario generado desde las tareas.
 */
export interface CalendarItemDTO {
  tareaId: number;
  titulo: string;
  inicio: string; // ISO
  fin: string;    // ISO
}

/**
 * Sesión de Pomodoro que viene del backend.
 */
export interface PomodoroDTO {
  id: number;
  usuarioId: number;
  tareaId?: number;
  tipo: TipoIntervalo;
  inicio: string;
  finPlanificado: string;
  finReal?: string;
  duracionMin: number;
  activo: boolean;
}

/**
 * Proyecto de usuario.
 */
export interface ProyectoDTO {
  id?: number;
  nombre: string;
  descripcion?: string;
  usuario?: Partial<UsuarioDTO>;
}

/**
 * Nota suelta del usuario (no necesariamente ligada a tarea).
 */
export interface NotaDTO {
  id?: number;
  titulo: string;
  categoria: string;
  contenido: string;
  usuario?: Partial<UsuarioDTO>;
}

/**
 * Hábito del usuario.
 */
export interface HabitoDTO {
  id?: number;
  nombre: string;
  usuario?: Partial<UsuarioDTO>;
}

/**
 * Meta del usuario.
 */
export interface MetaDTO {
  id?: number;
  titulo: string;
  descripcion: string;
  tipoMeta: TipoMeta;
  fechaLimite?: string; // ISO (opcional)
  usuario?: Partial<UsuarioDTO>;
}
