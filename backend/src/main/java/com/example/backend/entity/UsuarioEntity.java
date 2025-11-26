package com.example.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "usuarios",
        uniqueConstraints = {@UniqueConstraint(name = "uk_usuarios_email", columnNames = {"email"})})
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del usuario no puede ser nulo")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotNull(message = "El email del usuario no puede ser nulo")
    @Size(min = 3, max = 60, message = "El email debe tener entre 3 y 150 caracteres")
    @Column(nullable = false, length = 60)
    private String email;

    @NotNull(message = "La contraseña del usuario no puede ser nulo")
    @Size(min = 3, max = 20, message = "La contraseña debe tener entre 3 y 20 caracteres")
    @Column(nullable = false)
    private String contrasena;

    @Column(nullable = false)
    private Date fechaCreacion;

    @PrePersist
    public void prePersist() {
        if (fechaCreacion == null) fechaCreacion = new Date();
    }
}
