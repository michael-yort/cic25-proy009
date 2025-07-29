package es.cic.curso25.proy009.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cic.curso25.proy009.exception.NotFoundException;
import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArbolService {

    @Autowired
    private ArbolRepository arbolRepository;

    public Arbol createArbol(Arbol arbol) {
        // Establecer la relación desde cada rama hacia el árbol
        if (arbol.getRamas() != null) {
            for (Rama rama : arbol.getRamas()) {
                rama.setArbol(arbol);
            }
        }
        return arbolRepository.save(arbol); // Gracias al cascade se guardan también las ramas
    }

    public List<Arbol> getAllArboles() {
        return arbolRepository.findAll();
    }

    public Arbol actualizarRamaDelArbol(Long arbolId, Long ramaId, Rama datosActualizados) {
        Optional<Arbol> optArbol = arbolRepository.findById(arbolId);

        if (optArbol.isEmpty()) {
            throw new RuntimeException("Arbol no encontrado con id: " + arbolId);
        }

        Arbol arbol = optArbol.get();
        List<Rama> ramas = arbol.getRamas();

        boolean encontrada = false;

        for (Rama rama : ramas) {
            if (rama.getId().equals(ramaId)) {
                rama.setLongitud(datosActualizados.getLongitud());
                rama.setHojas(datosActualizados.getHojas());
                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            throw new RuntimeException("Rama no encontrada en el árbol con id: " + ramaId);
        }

        return arbolRepository.save(arbol); // Gracias a CascadeType.MERGE la rama se actualiza
    }

    public void deleteArbol(Long id) {
        Optional<Arbol> arbol = arbolRepository.findById(id);
        if (arbol.isEmpty()) {
            throw new NotFoundException("Árbol no encontrado con id: " + id);
        }
        arbolRepository.delete(arbol.get());
    }

}
