package com.example.olimpiadas.model;

public class Participacion {

    private Deportista deportista;
    private Evento evento;
    private Equipo equipo;
    private int edad;
    private String medalla;

    public Participacion(Deportista deportista, Evento evento, Equipo equipo, int edad, String medalla) {
        this.deportista = deportista;
        this.evento = evento;
        this.equipo = equipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    public Deportista getDeportista() {
        return deportista;
    }

    public void setDeportista(Deportista deportista) {
        this.deportista = deportista;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getMedalla() {
        return medalla;
    }

    public void setMedalla(String medalla) {
        this.medalla = medalla;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participacion that = (Participacion) o;
        return edad == that.edad &&
                (deportista != null ? deportista.equals(that.deportista) : that.deportista == null) &&
                (evento != null ? evento.equals(that.evento) : that.evento == null) &&
                (equipo != null ? equipo.equals(that.equipo) : that.equipo == null) &&
                (medalla != null ? medalla.equals(that.medalla) : that.medalla == null);
    }

    @Override
    public int hashCode() {
        int result = (deportista != null ? deportista.hashCode() : 0);
        result = 31 * result + (evento != null ? evento.hashCode() : 0);
        result = 31 * result + (equipo != null ? equipo.hashCode() : 0);
        result = 31 * result + edad;
        result = 31 * result + (medalla != null ? medalla.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Participacion{" +
                "deportista=" + (deportista != null ? deportista.getNombre() : "null") +
                ", evento=" + (evento != null ? evento.getNombre() : "null") +
                ", equipo=" + (equipo != null ? equipo.getNombre() : "null") +
                ", edad=" + edad +
                ", medalla='" + medalla + '\'' +
                '}';
    }

}
