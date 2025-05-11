package demo.demo;

import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Rol;
import demo.demo.model.Usuario;
import demo.demo.repository.RolRepository;
import demo.demo.repository.UsuarioRepository;
import demo.demo.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testListarUsuarios() {
        Usuario u = new Usuario();
        when(usuarioRepository.findAll()).thenReturn(List.of(u));

        List<Usuario> result = usuarioService.listarUsuarios();
        assertEquals(1, result.size());
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testObtenerUsuarioPorId() {
        Usuario u = new Usuario();
        u.setIdUsuario(1L);
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.obtenerUsuarioPorId(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getIdUsuario());
    }

    @Test
    void testGuardarUsuarioConRolYPassword() {
        Usuario u = new Usuario();
        u.setContraseña("plain");
        Rol rol = new Rol();
        rol.setIdRol(1L);
        u.setRol(rol);

        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Usuario result = usuarioService.guardarUsuario(u);
        assertNotEquals("plain", result.getContraseña());
        assertEquals(rol, result.getRol());
    }

    @Test
    void testGuardarUsuarioSinRol() {
        Usuario u = new Usuario();
        u.setContraseña("clave");

        when(usuarioRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        Usuario result = usuarioService.guardarUsuario(u);
        assertNotNull(result.getContraseña());
    }

    @Test
    void testEliminarUsuario() {
        usuarioService.eliminarUsuario(2L);
        verify(usuarioRepository, times(1)).deleteById(2L);
    }

    @Test
    void testValidarLoginCorrecto() {
        Usuario u = new Usuario();
        u.setEmail("a@a.com");
        u.setContraseña(org.springframework.security.crypto.bcrypt.BCrypt.hashpw("1234", org.springframework.security.crypto.bcrypt.BCrypt.gensalt()));

        when(usuarioRepository.findByEmail("a@a.com")).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.validarLogin("a@a.com", "1234");
        assertTrue(result.isPresent());
    }

    @Test
    void testValidarLoginIncorrecto() {
        Usuario u = new Usuario();
        u.setEmail("a@a.com");
        u.setContraseña(org.springframework.security.crypto.bcrypt.BCrypt.hashpw("1234", org.springframework.security.crypto.bcrypt.BCrypt.gensalt()));

        when(usuarioRepository.findByEmail("a@a.com")).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.validarLogin("a@a.com", "wrong");
        assertTrue(result.isEmpty());
    }

    @Test
    void testObtenerUsuarioPorEmail() {
        Usuario u = new Usuario();
        u.setEmail("e@e.com");
        when(usuarioRepository.findByEmail("e@e.com")).thenReturn(Optional.of(u));

        Optional<Usuario> result = usuarioService.obtenerUsuarioPorEmail("e@e.com");
        assertTrue(result.isPresent());
    }

    @Test
    void testConvertirAUsuarioDTO() {
        Rol r = new Rol();
        r.setIdRol(1L);
        r.setNombreRol("ADMIN");

        Usuario u = new Usuario();
        u.setIdUsuario(5L);
        u.setEmail("mail");
        u.setNombreUsuario("nombre");
        u.setRol(r);

        UsuarioDTO dto = usuarioService.convertirAUsuarioDTO(u);
        assertEquals("ADMIN", dto.getRolNombre());
        assertEquals("mail", dto.getEmail());
        assertEquals(1L, dto.getRolId());
        assertEquals("nombre", dto.getNombreUsuario());
    }
}
