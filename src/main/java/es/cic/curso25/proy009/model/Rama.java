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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + longitud;
        result = prime * result + hojas;
        result = prime * result + ((arbol == null) ? 0 : arbol.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Rama other = (Rama) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (longitud != other.longitud)
            return false;
        if (hojas != other.hojas)
            return false;
        if (arbol == null) {
            if (other.arbol != null)
                return false;
        } else if (!arbol.equals(other.arbol))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Rama [id=" + id + ", longitud=" + longitud + ", hojas=" + hojas + ", arbol_ID=" + arbol.getId() + "]";
    }

}
