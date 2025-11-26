import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

import { HabitosService } from '../../services/habitos.service';
import { HabitoDTO } from '../../models/dtos';
import { UsuariosService } from '../../services/usuarios.service';

@Component({
  standalone: true,
  selector: 'app-habitos-page',
  imports: [
    CommonModule,
    FormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatListModule,
    MatIconModule
  ],
  templateUrl: './habitos-page.html',
  styleUrl: './habitos-page.scss'
})
export class HabitosPageComponent implements OnInit {

  private srv = inject(HabitosService);
  private usuariosSrv = inject(UsuariosService);

  lista = signal<HabitoDTO[]>([]);
  edit?: HabitoDTO;
  nombre = '';
  usuarioId: number | null = null;

  ngOnInit(): void {
    this.usuarioId = this.usuariosSrv.getUsuarioId();
    this.load();
  }

  load() {
    this.usuarioId = this.usuariosSrv.getUsuarioId();

    this.srv.listar().subscribe(v => {
      const propios = this.usuarioId
        ? v.filter(h => (h as any).usuario?.id === this.usuarioId)
        : v;
      this.lista.set(propios);
    });
  }

  guardar() {
    const nombre = this.nombre.trim();
    if (!nombre) return;

    const base: any = { nombre };
    if (this.usuarioId) base.usuario = { id: this.usuarioId };

    const obs = this.edit?.id
      ? this.srv.editar({ id: this.edit.id, ...base })
      : this.srv.crear(base);

    obs.subscribe(() => {
      this.cancelar();
      this.load();
    });
  }

  borrar(h: HabitoDTO) {
    if (h.id) this.srv.borrar(h.id).subscribe(() => this.load());
  }

  editar(h: HabitoDTO) {
    this.edit = h;
    this.nombre = h.nombre;
  }

  cancelar() {
    this.edit = undefined;
    this.nombre = '';
  }
}
