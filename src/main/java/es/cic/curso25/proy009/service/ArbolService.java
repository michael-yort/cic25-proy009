package es.cic.curso25.proy009.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(ArbolService.class);

    @Autowired
    private ArbolRepository arbolRepository;

    public Arbol createArbol(Arbol arbol) {
        // Establecer la relación desde cada rama hacia el árbol
        if (arbol.getRamas() != null) {
            LOGGER.info("Creando Rama");
            for (Rama rama : arbol.getRamas()) {
                rama.setArbol(arbol);
            }
        }
        LOGGER.info("Creando Arbol");
        return arbolRepository.save(arbol); // Gracias al cascade se guardan también las ramas
    }

    public List<Arbol> getAllArboles() {
        return arbolRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        // return arbolRepository.findAll();
    }

    public Optional<Arbol> getArbol(Long id) {
        return arbolRepository.findById(id);
    }

    // ------------------------------------------------------------------------

    public Arbol actualizarArbol(Long id, Arbol reqArbol) {
        Arbol arbol = arbolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Arbol no encontrado con id: " + id));

        if (reqArbol.getEspecie() != null) {
            arbol.setEspecie(reqArbol.getEspecie());
        }

        return arbolRepository.save(arbol);
    }

    public Arbol actualizarRamaDelArbol(Long arbolId, Long ramaId, Rama datosActualizados) {
        Optional<Arbol> optArbol = arbolRepository.findById(arbolId);

        if (optArbol.isEmpty()) {
            throw new NotFoundException("Arbol no encontrado con id: " + arbolId);
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
