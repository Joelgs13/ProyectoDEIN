package com.example.olimpiadas.Model;

// Clase Deporte
public class Deporte {
    private int idDeporte;
    private String nombre;

    public Deporte(int idDeporte, String nombre) {
        this.idDeporte = idDeporte;
        this.nombre = nombre;
    }

    public int getIdDeporte() {
        return idDeporte;
    }

    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Deporte{idDeporte=" + idDeporte + ", nombre='" + nombre + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deporte deporte = (Deporte) o;
        return idDeporte == deporte.idDeporte;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idDeporte);
    }
}
