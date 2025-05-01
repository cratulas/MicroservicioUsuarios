package demo.demo.service;

import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Rol;
import demo.demo.model.Usuario;
import demo.demo.repository.RolRepository;
import demo.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        // Hashear la contraseña si se proporciona
        if (usuario.getContraseña() != null) {
            String hashed = BCrypt.hashpw(usuario.getContraseña(), BCrypt.gensalt());
            usuario.setContraseña(hashed);
        }

        if (usuario.getRol() != null && usuario.getRol().getIdRol() != null) {
            Optional<Rol> rolOpt = rolRepository.findById(usuario.getRol().getIdRol());
            rolOpt.ifPresent(usuario::setRol);
        }
        

        return usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> validarLogin(String email, String contraseñaPlano) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (BCrypt.checkpw(contraseñaPlano, usuario.getContraseña())) {
                return Optional.of(usuario);
            }
        }
        return Optional.empty();
    }

    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    

    public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .rolId(usuario.getRol().getIdRol())
                .rolNombre(usuario.getRol().getNombreRol())
                .build();
    }
}
