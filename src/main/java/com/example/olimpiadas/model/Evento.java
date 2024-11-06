package com.example.olimpiadas.model;

// Clase Evento
public class Evento {

    private int id_evento;
    private String nombre;
    private Olimpiada olimpiada;
    private Deporte deporte;

    public Evento(int id_evento, String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Olimpiada getOlimpiada() {
        return olimpiada;
    }

    public void setOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
    }

    public Deporte getDeporte() {
        return deporte;
    }

    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return id_evento == evento.id_evento &&
                (nombre != null ? nombre.equals(evento.nombre) : evento.nombre == null) &&
                (olimpiada != null ? olimpiada.equals(evento.olimpiada) : evento.olimpiada == null) &&
                (deporte != null ? deporte.equals(evento.deporte) : evento.deporte == null);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(id_evento);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (olimpiada != null ? olimpiada.hashCode() : 0);
        result = 31 * result + (deporte != null ? deporte.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "id_evento=" + id_evento +
                ", nombre='" + nombre + '\'' +
                ", olimpiada=" + (olimpiada != null ? olimpiada.getNombre() : "null") +
                ", deporte=" + (deporte != null ? deporte.getNombre() : "null") +
                '}';
    }

}
