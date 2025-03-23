package demo.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 50, message = "El nombre de usuario no debe exceder los 50 caracteres")
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    @Size(max = 100, message = "El email no debe exceder los 100 caracteres")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "contraseña")
    private String contraseña;

    @Column(name = "rol_id")
    private Long rolId;
}
