package com.example.olimpiadas.model;

// Clase Evento
public class Evento {

    private int idEvento;
    private String nombre;
    private Olimpiada olimpiada;
    private Deporte deporte;

    public Evento(int idEvento, String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int id_evento) {
        this.idEvento = id_evento;
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
        return idEvento == evento.idEvento &&
                (nombre != null ? nombre.equals(evento.nombre) : evento.nombre == null) &&
                (olimpiada != null ? olimpiada.equals(evento.olimpiada) : evento.olimpiada == null) &&
                (deporte != null ? deporte.equals(evento.deporte) : evento.deporte == null);
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(idEvento);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (olimpiada != null ? olimpiada.hashCode() : 0);
        result = 31 * result + (deporte != null ? deporte.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Evento{" +
                "idEvento=" + idEvento +
                ", nombre='" + nombre + '\'' +
                ", olimpiada=" + (olimpiada != null ? olimpiada.getNombre() : "null") +
                ", deporte=" + (deporte != null ? deporte.getNombre() : "null") +
                '}';
    }

}
