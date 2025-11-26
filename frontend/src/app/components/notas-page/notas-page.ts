import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

import { NotasService } from '../../services/notas.service';
import { NotaDTO } from '../../models/dtos';
import { UsuariosService } from '../../services/usuarios.service';

@Component({
  standalone: true,
  selector: 'app-notas-page',
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
  templateUrl: './notas-page.html',
  styleUrl: './notas-page.scss'
})
export class NotasPageComponent implements OnInit {

  private srv = inject(NotasService);
  private usuariosSrv = inject(UsuariosService);

  lista = signal<NotaDTO[]>([]);
  edit?: NotaDTO;
  form: NotaDTO = { titulo: '', categoria: '', contenido: '' };
  usuarioId: number | null = null;

  ngOnInit(): void {
    this.usuarioId = this.usuariosSrv.getUsuarioId();
    this.load();
  }

  load() {
    this.usuarioId = this.usuariosSrv.getUsuarioId();

    this.srv.listar().subscribe(v => {
      const propios = this.usuarioId
        ? v.filter(n => (n as any).usuario?.id === this.usuarioId)
        : v;
      this.lista.set(propios);
    });
  }

  guardar() {
    const titulo = this.form.titulo?.trim();
    const categoria = this.form.categoria?.trim();
    const contenido = this.form.contenido?.trim() ?? '';

    if (!titulo || !categoria) return;

    const base: any = { titulo, categoria, contenido };
    if (this.usuarioId) base.usuario = { id: this.usuarioId };

    const payload = this.edit?.id ? { id: this.edit.id, ...base } : base;
    const obs = this.edit?.id ? this.srv.editar(payload) : this.srv.crear(base);

    obs.subscribe(() => {
      this.cancelar();
      this.load();
    });
  }

  borrar(n: NotaDTO) {
    if (n.id) this.srv.borrar(n.id).subscribe(() => this.load());
  }

  editar(n: NotaDTO) {
    this.edit = n;
    this.form = {
      id: n.id,
      titulo: n.titulo,
      categoria: n.categoria,
      contenido: n.contenido,
      usuario: n.usuario
    };
  }

  cancelar() {
    this.edit = undefined;
    this.form = { titulo: '', categoria: '', contenido: '' };
  }
}
