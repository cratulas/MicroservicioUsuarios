package demo.demo.controller;

import demo.demo.model.Rol;
import demo.demo.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public List<Rol> obtenerTodosLosRoles() {
        return rolService.listarRoles();
    }

    @GetMapping("/{id}")
    public Rol obtenerRolPorId(@PathVariable Long id) {
        return rolService.obtenerRolPorId(id);
    }
}
