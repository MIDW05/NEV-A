package com.example.backend.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.Date;

@Data
public class UsuarioDTO {
    private Long id;

    @NotBlank(message = "El nombre del usuario no puede ser nulo")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombre;

    @NotBlank(message = "El email del usuario no puede ser nulo")
    @Email(message = "El email no tiene un formato válido")
    @Size(max = 500, message = "El email no puede superar 500 caracteres")
    private String email;

    @NotBlank(message = "La contraseña del usuario no puede ser nula")
    @Size(min = 3, max = 20, message = "La contraseña debe tener entre 3 y 20 caracteres")
    private String contrasena;

    private Date fechaCreacion;
}
