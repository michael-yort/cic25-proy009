package es.cic.curso25.proy009.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;

@SpringBootTest
public class ArbolServiceTest {
        


    @Autowired
    private ArbolService arbolService;

    @Test
    void testActualizarRamaDelArbol() {

    }

    @Test
    void testCreateArbol() {

        Arbol arbolTest = new Arbol();

        arbolTest.setEspecie("Pino");

        Rama ramaTest1 = new Rama();
        ramaTest1.setHojas(100);
        ramaTest1.setLongitud(5);

        Rama ramaTest2 = new Rama();
        ramaTest2.setHojas(200);
        ramaTest2.setLongitud(7);

        List<Rama> listaRamas = new ArrayList<>();
        listaRamas.add(ramaTest1);
        listaRamas.add(ramaTest2);

        arbolTest.setRamas(listaRamas);

        Arbol arbolCreado = arbolService.createArbol(arbolTest);
        Long id = arbolCreado.getId();


        
        assertNotNull(id);
    }

    @Test
    void testGetAllArboles() {
        // Paso 1: Crear pantalones de prueba

        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Arbol arbolTest2 = new Arbol();
        arbolTest2.setEspecie("Roble");

        Arbol arbolTest3 = new Arbol();
        arbolTest3.setEspecie("Acacia");

        // Paso 2: Guardarlos en la base de datos

        arbolService.createArbol(arbolTest1);
        arbolService.createArbol(arbolTest2);
        arbolService.createArbol(arbolTest3);

        List<Arbol> listaArboles = arbolService.getAllArboles();

        long longitud = listaArboles.size();

        assertEquals(3, longitud);

    }

    @Test
    void testGetArbol() {

        // Paso 1: Crear pantalones de prueba

        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Arbol arbolTest2 = new Arbol();
        arbolTest2.setEspecie("Roble");

        Arbol arbolTest3 = new Arbol();
        arbolTest3.setEspecie("Acacia");

        // Paso 2: Guardarlos en la base de datos

        arbolService.createArbol(arbolTest1);
        arbolService.createArbol(arbolTest2);
        arbolService.createArbol(arbolTest3);

        List<Arbol> listaArboles = arbolService.getAllArboles();
        Arbol arbolBuscado = listaArboles.get(0);

        String especie = arbolBuscado.getEspecie();

        assertEquals("Pino", especie);

    }

    @Test
    void testDeleteArbol() {

        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Arbol arbolCreado = arbolService.createArbol(arbolTest1);
        Long id = arbolCreado.getId();
        assertTrue(id > 0);

        arbolService.deleteArbol(id);

        Optional<Arbol> resultado = arbolService.getArbol(id);
        assertTrue(resultado.isEmpty());

    }

}
