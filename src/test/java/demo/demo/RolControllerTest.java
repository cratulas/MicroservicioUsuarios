package demo.demo;

import demo.demo.controller.RolController;
import demo.demo.model.Rol;
import demo.demo.service.RolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RolController.class)
@AutoConfigureMockMvc(addFilters = false) 
class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Test
    void obtenerTodosLosRoles_retornListaDeRoles() throws Exception {
        Rol rol1 = new Rol();
        rol1.setIdRol(1L);
        rol1.setNombreRol("ADMIN");

        Rol rol2 = new Rol();
        rol2.setIdRol(2L);
        rol2.setNombreRol("JUGADOR");

        List<Rol> roles = Arrays.asList(rol1, rol2);
        given(rolService.listarRoles()).willReturn(roles);

        mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].nombreRol", is("ADMIN")))
                .andExpect(jsonPath("$[1].nombreRol", is("JUGADOR")));
    }

    @Test
    void obtenerRolPorId_retornaRolCorrecto() throws Exception {
        Rol rol = new Rol();
        rol.setIdRol(1L);
        rol.setNombreRol("ADMIN");

        given(rolService.obtenerRolPorId(1L)).willReturn(rol);

        mockMvc.perform(get("/roles/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idRol", is(1)))
                .andExpect(jsonPath("$.nombreRol", is("ADMIN")));
    }
}
