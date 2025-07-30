package es.cic.curso25.proy009.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.cic.curso25.proy009.model.Arbol;
import es.cic.curso25.proy009.model.Rama;
import es.cic.curso25.proy009.repository.ArbolRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ArbolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ArbolRepository arbolRepository;

    @Test
    void testCrearSoloArbol() throws Exception {

        Arbol arbolTest = new Arbol();
        arbolTest.setEspecie("Pino");

        String arbolJson = objectMapper.writeValueAsString(arbolTest);

        mockMvc.perform(post("/arboles")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Arbol arbolCreado = objectMapper.readValue(respuesta, Arbol.class);

                    assertTrue(arbolCreado.getId() >= 1, "El valor debe ser mayor que 0");

                });
    }

    @Test
    void testCrearArbolConRamas() throws Exception {

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

        String arbolJson = objectMapper.writeValueAsString(arbolTest);

        mockMvc.perform(post("/arboles")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Arbol arbolCreado = objectMapper.readValue(respuesta, Arbol.class);

                    assertTrue(arbolCreado.getId() >= 1, "El valor debe ser mayor que 0");
                    assertTrue(arbolCreado.getRamas().size() == 2, "Este arbol tiene que tener solo 2 ramas");

                });
    }

    @Test
    void testGetAllArboles() throws Exception {

        // Paso 1: Crear arboles de prueba
        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Arbol arbolTest2 = new Arbol();
        arbolTest2.setEspecie("Roble");

        Arbol arbolTest3 = new Arbol();
        arbolTest3.setEspecie("Acacia");

        // Paso 2: Guardardo en la base de datos
        arbolRepository.deleteAll();
        arbolRepository.save(arbolTest1);
        arbolRepository.save(arbolTest2);
        arbolRepository.save(arbolTest3);

        // Paso 3: Hacer la petición GET a /arboles
        MvcResult resultado = mockMvc.perform(get("/arboles"))
                .andExpect(status().isOk())
                .andReturn();

        // Paso 4: Obtener el contenido JSON de la respuesta como texto
        String contenidoJson = resultado.getResponse().getContentAsString();

        // Paso 5: Convertir el JSON en una lista de objetos Arbol
        ObjectMapper objectMapper = new ObjectMapper();
        List<Arbol> listaArboles = objectMapper.readValue(
                contenidoJson,
                new TypeReference<List<Arbol>>() {
                });

        // Paso 6: Verificar que se devolvieron 3 arboles
        assertEquals(3, listaArboles.size());

        // Paso 7: Verificar que los datos son los esperados
        assertEquals("Pino", listaArboles.get(0).getEspecie());
        assertEquals("Roble", listaArboles.get(1).getEspecie());
        assertEquals("Acacia", listaArboles.get(2).getEspecie());
    }

    @Test
    void testGetOneArbol() throws Exception {

        // Paso 1: Crear arboles de prueba
        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Arbol arbolTest2 = new Arbol();
        arbolTest2.setEspecie("Roble");

        Arbol arbolTest3 = new Arbol();
        arbolTest3.setEspecie("Acacia");

        // Paso 2: Guardardo en la base de datos
        arbolRepository.deleteAll();
        arbolRepository.save(arbolTest1);
        arbolRepository.save(arbolTest2);
        arbolRepository.save(arbolTest3);

        // Paso 3: Hacer la petición GET a /arboles
        MvcResult resultado = mockMvc.perform(get("/arboles/{id}", arbolTest2.getId()))
                .andExpect(status().isOk())
                .andReturn();

        // Paso 4: Obtener el contenido JSON de la respuesta como texto
        String contenidoJson = resultado.getResponse().getContentAsString();

        // Paso 5: Convertir el JSON en una lista de objetos Arbol
        ObjectMapper objectMapper = new ObjectMapper();
        Arbol arbol = objectMapper.readValue(
                contenidoJson,
                new TypeReference<Arbol>() {
                });

        // Paso 6: Verificar que se devolvio el arbol con id 2
        // assertEquals(2, arbol.getId());

        // Paso 7: Verificar que los datos son los esperados

        assertEquals("Roble", arbol.getEspecie());

    }

    @Test
    void testActualizarArbol() throws Exception {

        // 1. Crear un arbol
        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        String arbolJson = objectMapper.writeValueAsString(arbolTest1);
        String respuestaCreacion = mockMvc.perform(post("/arboles")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Arbol arbolCreado = objectMapper.readValue(respuestaCreacion, Arbol.class);
        Long idCreado = arbolCreado.getId();

        // 2. Modificar el arbol
        arbolCreado.setEspecie("Acacia");

        String arbolActualizadoJson = objectMapper.writeValueAsString(arbolCreado);

        // 3. Hacer PUT con el ID en la URL
        mockMvc.perform(put("/arboles/" + idCreado)
                .contentType("application/json")
                .content(arbolActualizadoJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Arbol arbolActualizado = objectMapper.readValue(respuesta, Arbol.class);

                    // Verificar los cambios
                    assertEquals("Acacia", arbolActualizado.getEspecie());
                    assertEquals(idCreado, arbolActualizado.getId());
                });

    }

    @Test
    @Disabled
    void testActualizarArbolConRamas() throws Exception {

        // 1. Crear un arbol
        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");

        Rama ramaTest1 = new Rama();
        ramaTest1.setHojas(100);
        ramaTest1.setLongitud(5);

        // setear ambos lados de la relación
        ramaTest1.setArbol(arbolTest1);
        arbolTest1.setRamas(new ArrayList<>());
        arbolTest1.getRamas().add(ramaTest1);

        String arbolJson = objectMapper.writeValueAsString(arbolTest1);
        String respuestaCreacion = mockMvc.perform(post("/arboles")
                .contentType("application/json")
                .content(arbolJson))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Arbol arbolCreado = objectMapper.readValue(respuestaCreacion, Arbol.class);
        Long idCreado = arbolCreado.getId();
        Long ramaId = arbolCreado.getRamas().get(0).getId();

        // 2. Modificar el arbol
        arbolCreado.setEspecie("Acacia");
        arbolCreado.getRamas().get(0).setHojas(999);

        String arbolActualizadoJson = objectMapper.writeValueAsString(arbolCreado);

        // 3. Hacer PUT con el ID en la URL
        mockMvc.perform(put("/arboles/" + idCreado + "/ramas/" + ramaId)
                .contentType("application/json")
                .content(arbolActualizadoJson))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    String respuesta = result.getResponse().getContentAsString();
                    Arbol arbolActualizado = objectMapper.readValue(respuesta, Arbol.class);

                    // Verificar los cambios
                    assertEquals("Acacia", arbolActualizado.getEspecie());
                    assertEquals(999, arbolActualizado.getRamas().get(0).getHojas());
                });

    }

    @Test
    void testBorrarArbol() throws Exception {
        arbolRepository.deleteAll();
        Arbol arbolTest1 = new Arbol();
        arbolTest1.setEspecie("Pino");
        Arbol arbolGuardado = arbolRepository.save(arbolTest1);
        Long id = arbolGuardado.getId();

        mockMvc.perform(delete("/arboles/" + id))
                .andExpect(status().isOk());

        Optional<Arbol> pantalonEliminado = arbolRepository.findById(id);

        assertTrue(pantalonEliminado.isEmpty());

    }
}
