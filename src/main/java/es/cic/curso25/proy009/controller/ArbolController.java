package es.cic.curso25.proy009.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.service.ArbolService;

import java.util.List;

@RestController
@RequestMapping("/arboles")
public class ArbolController {

    @Autowired
    private ArbolService arbolService;

    @PostMapping
    public Arbol createArbol(@RequestBody Arbol arbol) {
        return arbolService.createArbol(arbol);
    }

    @GetMapping
    public List<Arbol> getAllArboles() {
        return arbolService.getAllArboles();
    }

    @PutMapping("/{arbolId}/ramas/{ramaId}")
    public Arbol actualizarRamaDeArbol(
            @PathVariable Long arbolId,
            @PathVariable Long ramaId,
            @RequestBody Rama ramaActualizada) {

        return arbolService.actualizarRamaDelArbol(arbolId, ramaId, ramaActualizada);
    }

    @DeleteMapping("/{arbolId}")
    public void borrarArbol(@PathVariable Long arbolId) {
        arbolService.deleteArbol(arbolId);
    }

}
