package es.cic.curso25.proy009.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Arbol {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String especie;

    
    @OneToMany(mappedBy = "arbol", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    private List<Rama> ramas;

    public Arbol() {
    }

    public Arbol(String especie, List<Rama> ramas) {
        this.especie = especie;
        this.ramas = ramas;
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public List<Rama> getRamas() {
        return ramas;
    }

    public void setRamas(List<Rama> ramas) {
        this.ramas = ramas;
    }

    @Override
    public String toString() {
        return "Arbol [id=" + id + ", especie=" + especie + ", ramasSIZE=" + ramas.size() + "]";
    }

    
    
}
