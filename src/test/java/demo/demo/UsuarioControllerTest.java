package demo.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import demo.demo.controller.UsuarioController;
import demo.demo.dto.UsuarioDTO;
import demo.demo.model.Usuario;
import demo.demo.security.JwtUtil;
import demo.demo.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = UsuarioController.class)
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void obtenerUsuarios_retornRespuestaOkConListaDeDTOs() throws Exception {
        Usuario u = new Usuario(); u.setIdUsuario(1L);
        UsuarioDTO dto = new UsuarioDTO(); dto.setIdUsuario(1L);
        when(usuarioService.listarUsuarios()).thenReturn(List.of(u));
        when(usuarioService.convertirAUsuarioDTO(u)).thenReturn(dto);

        mockMvc.perform(get("/usuarios"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].idUsuario").value(1));
    }

    @Test
    void obtenerUsuarioPorId_cuandoExiste_retornOk() throws Exception {
        Usuario u = new Usuario(); u.setIdUsuario(2L);
        UsuarioDTO dto = new UsuarioDTO(); dto.setIdUsuario(2L);
        when(usuarioService.obtenerUsuarioPorId(2L)).thenReturn(Optional.of(u));
        when(usuarioService.convertirAUsuarioDTO(u)).thenReturn(dto);

        mockMvc.perform(get("/usuarios/{id}", 2L))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(2));
    }

    @Test
    void obtenerUsuarioPorId_cuandoNoExiste_retornNotFound() throws Exception {
        when(usuarioService.obtenerUsuarioPorId(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/usuarios/{id}", 3L))
            .andExpect(status().isNotFound());
    }

    @Test
    void crearUsuario_retornOkConDTO() throws Exception {
        Usuario u = new Usuario(); u.setIdUsuario(4L); u.setNombreUsuario("n");
        UsuarioDTO dto = new UsuarioDTO(); dto.setIdUsuario(4L); dto.setNombreUsuario("n");
        when(usuarioService.guardarUsuario(any())).thenReturn(u);
        when(usuarioService.convertirAUsuarioDTO(u)).thenReturn(dto);

        mockMvc.perform(post("/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(4))
            .andExpect(jsonPath("$.nombreUsuario").value("n"));
    }

    @Test
    void actualizarUsuario_cuandoExiste_retornaOkConDTO() throws Exception {
        Usuario existing = new Usuario(); existing.setIdUsuario(5L);
        Usuario updated = new Usuario(); updated.setIdUsuario(5L);
        UsuarioDTO dto = new UsuarioDTO(); dto.setIdUsuario(5L);
        when(usuarioService.obtenerUsuarioPorId(5L)).thenReturn(Optional.of(existing));
        when(usuarioService.guardarUsuario(any())).thenReturn(updated);
        when(usuarioService.convertirAUsuarioDTO(updated)).thenReturn(dto);

        mockMvc.perform(put("/usuarios/{id}", 5L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.idUsuario").value(5));
    }

    @Test
    void actualizarUsuario_cuandoNoExiste_retornaNotFound() throws Exception {
        when(usuarioService.obtenerUsuarioPorId(6L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/usuarios/{id}", 6L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isNotFound());
    }

    @Test
    void eliminarUsuario_retornNoContent() throws Exception {
        doNothing().when(usuarioService).eliminarUsuario(7L);

        mockMvc.perform(delete("/usuarios/{id}", 7L))
            .andExpect(status().isNoContent());
    }

    @Test
    void login_cuandoCredencialesValidas_retornaTokenYUsuario() throws Exception {
        Usuario u = new Usuario(); u.setIdUsuario(8L); u.setEmail("a@a.com");
        UsuarioDTO dto = new UsuarioDTO(); dto.setIdUsuario(8L); dto.setEmail("a@a.com");
        when(usuarioService.validarLogin("a@a.com","p")).thenReturn(Optional.of(u));
        when(jwtUtil.generateToken("a@a.com")).thenReturn("tok123");
        when(usuarioService.convertirAUsuarioDTO(u)).thenReturn(dto);

        mockMvc.perform(post("/usuarios/login")
                .param("email","a@a.com")
                .param("contraseña","p"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("tok123"))
            .andExpect(jsonPath("$.usuario.idUsuario").value(8));
    }

    @Test
    void login_cuandoCredencialesInvalidas_retornaUnauthorized() throws Exception {
        when(usuarioService.validarLogin(anyString(),anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/usuarios/login")
                .param("email","x")
                .param("contraseña","bad"))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.error").value("Credenciales incorrectas"));
    }

    @Test
    void recuperarPassword_cuandoEmailRegistrado_retornOk() throws Exception {
        Usuario u = new Usuario(); u.setEmail("e@e.com");
        when(usuarioService.obtenerUsuarioPorEmail("e@e.com")).thenReturn(Optional.of(u));

        mockMvc.perform(post("/usuarios/recover-password")
                .param("email","e@e.com"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.mensaje").value("Enlace enviado a e@e.com"));
    }

    @Test
    void recuperarPassword_cuandoEmailNoRegistrado_retornNotFound() throws Exception {
        when(usuarioService.obtenerUsuarioPorEmail("no")).thenReturn(Optional.empty());

        mockMvc.perform(post("/usuarios/recover-password")
                .param("email","no"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("El correo no está registrado"));
    }
}
