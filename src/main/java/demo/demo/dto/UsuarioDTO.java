package demo.demo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private String email;
    private Long rolId;
    private String rolNombre;

    public UsuarioDTO() {}

    private UsuarioDTO(Builder builder) {
        this.idUsuario = builder.idUsuario;
        this.nombreUsuario = builder.nombreUsuario;
        this.email = builder.email;
        this.rolId = builder.rolId;
        this.rolNombre = builder.rolNombre;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long idUsuario;
        private String nombreUsuario;
        private String email;
        private Long rolId;
        private String rolNombre;

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

        public Builder rolId(Long rolId) {
            this.rolId = rolId;
            return this;
        }

        public Builder rolNombre(String rolNombre) {
            this.rolNombre = rolNombre;
            return this;
        }

        public UsuarioDTO build() {
            UsuarioDTO dto = new UsuarioDTO(this);
            return dto;
        }
    }
}

