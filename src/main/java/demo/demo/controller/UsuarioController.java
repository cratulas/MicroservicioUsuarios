package demo.demo.controller;

import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Usuario;
import demo.demo.security.JwtUtil;
import demo.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public List<UsuarioDTO> obtenerUsuarios() {
        return usuarioService.listarUsuarios().stream()
                .map(usuarioService::convertirAUsuarioDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuarioPorId(@PathVariable Long id) {
        return usuarioService.obtenerUsuarioPorId(id)
                .map(usuario -> ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario);
        return ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(nuevoUsuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> existente = usuarioService.obtenerUsuarioPorId(id);
        if (existente.isPresent()) {
            usuario.setIdUsuario(id);
            // Asegurarse de que no se pierda la relación con el rol original
            if (usuario.getRol() == null) {
                usuario.setRol(existente.get().getRol());
            }
            Usuario actualizado = usuarioService.guardarUsuario(usuario);
            return ResponseEntity.ok(usuarioService.convertirAUsuarioDTO(actualizado));
        }
        return ResponseEntity.notFound().build();
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioService.validarLogin(email, contraseña);
        if (usuarioOpt.isPresent()) {
            String token = jwtUtil.generateToken(email);
            UsuarioDTO usuarioDTO = usuarioService.convertirAUsuarioDTO(usuarioOpt.get());
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "usuario", usuarioDTO
            ));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Credenciales incorrectas"));
    }

    @PostMapping("/recover-password")
    public ResponseEntity<?> recuperarPassword(@RequestParam String email) {
    Optional<Usuario> usuarioOpt = usuarioService.obtenerUsuarioPorEmail(email);
    if (usuarioOpt.isPresent()) {
        // Simulación: Enviar enlace
        return ResponseEntity.ok(Map.of("mensaje", "Enlace enviado a " + email));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "El correo no está registrado"));
    }
}

}
