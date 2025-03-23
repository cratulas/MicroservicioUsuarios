package demo.demo.service;

import demo.demo.model.Usuario;
import demo.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario crearUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                    usuario.setEmail(usuarioActualizado.getEmail());
                    usuario.setContraseña(usuarioActualizado.getContraseña());
                    usuario.setRolId(usuarioActualizado.getRolId());
                    return usuarioRepository.save(usuario);
                }).orElse(null);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario login(String email, String contraseña) {
        return usuarioRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email) && u.getContraseña().equals(contraseña))
                .findFirst().orElse(null);
    }
}
