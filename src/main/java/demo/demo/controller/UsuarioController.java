package demo.demo.controller;

import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Usuario;
import demo.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Obtener todos los usuarios 
    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.listarUsuarios()
                .stream()
                .map(usuarioService::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    // Obtener usuario por ID 
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorId(id);
        return usuarioOpt.map(usuario -> ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(usuario)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(nuevoUsuario));
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioService.obtenerUsuarioPorId(id);
        if (usuarioExistente.isPresent()) {
            usuario.setIdUsuario(id);
            Usuario usuarioActualizado = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(usuarioActualizado));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Inicio de sesión
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioService.validarLogin(email, contraseña);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok("Inicio de sesión exitoso. Bienvenido " + usuarioOpt.get().getNombreUsuario() + "!");
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas.");
        }
    }
}
