package es.cic.curso25.proy009.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Rama {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int longitud;
    private int hojas;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "arbol_id")
    private Arbol arbol;

    public Rama() {
    }

    public Rama(int longitud, int hojas) {
        this.longitud = longitud;
        this.hojas = hojas;
    }

    // Getters y Setters...

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLongitud() {
        return longitud;
    }

    public void setLongitud(int longitud) {
        this.longitud = longitud;
    }

    public int getHojas() {
        return hojas;
    }

    public void setHojas(int hojas) {
        this.hojas = hojas;
    }

    public Arbol getArbol() {
        return arbol;
    }

    public void setArbol(Arbol arbol) {
        this.arbol = arbol;
    }

    // hashCode, equals y toString...
}
