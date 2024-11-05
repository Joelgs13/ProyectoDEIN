package com.example.olimpiadas.Model;

// Clase Equipo
public class Equipo {
    private int idEquipo;
    private String nombre;
    private String iniciales;

    public Equipo(int idEquipo, String nombre, String iniciales) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIniciales() {
        return iniciales;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    @Override
    public String toString() {
        return "Equipo{idEquipo=" + idEquipo + ", nombre='" + nombre + "', iniciales='" + iniciales + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return idEquipo == equipo.idEquipo;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idEquipo);
    }
}
