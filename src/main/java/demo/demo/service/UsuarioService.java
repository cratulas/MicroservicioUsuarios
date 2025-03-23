package demo.demo.service;

import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Usuario;
import demo.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Listar todos los usuarios
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener un usuario por su ID
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Guardar un usuario
    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Eliminar usuario por ID
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Validar login
    public Optional<Usuario> validarLogin(String email, String contraseña) {
        return usuarioRepository.findAll().stream()
                .filter(usuario -> usuario.getEmail().equals(email) && usuario.getContraseña().equals(contraseña))
                .findFirst();
    }

    // Convertir entidad Usuario a DTO
    public UsuarioDTO convertirAUsuarioDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .nombreUsuario(usuario.getNombreUsuario())
                .email(usuario.getEmail())
                .rolId(usuario.getRolId())
                .build();
    }
}
