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
        // Verifica si ambos objetos son el mismo
        if (this == o) return true;

        // Verifica si el objeto es una instancia válida de Evento
        if (o == null || getClass() != o.getClass()) return false;

        // Realiza una conversión explícita a Evento
        Evento evento = (Evento) o;

        // Compara los atributos clave (en este caso, idEvento)
        return idEvento == evento.idEvento;
    }

    @Override
    public int hashCode() {
        // Usa idEvento para calcular el valor de hash
        return Integer.hashCode(idEvento);
    }


    @Override
    public String toString() {
        return nombre;
    }

}
