package demo.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    @Column(name = "nombre_usuario")
    private String nombreUsuario;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Debe ser un email válido")
    @Column(name = "email", unique = true)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "contraseña")
    private String contraseña;

    @NotNull(message = "El rol es obligatorio")
    @Min(value = 1, message = "Rol inválido")
    @Max(value = 3, message = "Rol inválido")
    @Column(name = "rol_id")
    private Long rolId;

    // Constructor vacío
    public Usuario() {}

    // Constructor privado para usar con builder
    private Usuario(Builder builder) {
        this.idUsuario = builder.idUsuario;
        this.nombreUsuario = builder.nombreUsuario;
        this.email = builder.email;
        this.contraseña = builder.contraseña;
        this.rolId = builder.rolId;
    }

    // Builder
    public static Builder builder() {
        return new Builder();
    }

    // Clase Builder
    public static class Builder {
        private Long idUsuario;
        private String nombreUsuario;
        private String email;
        private String contraseña;
        private Long rolId;

        public Builder idUsuario(Long idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public Builder nombreUsuario(String nombreUsuario) {
            this.nombreUsuario = nombreUsuario;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder contraseña(String contraseña) {
            this.contraseña = contraseña;
            return this;
        }

        public Builder rolId(Long rolId) {
            this.rolId = rolId;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

    // Métodos
    public boolean isAdmin() {
        return this.rolId == 3;
    }

    public boolean isModerator() {
        return this.rolId == 2;
    }

    public boolean isUser() {
        return this.rolId == 1;
    }

    public boolean canModerate() {
        return isAdmin() || isModerator();
    }
}
