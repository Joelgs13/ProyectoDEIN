package com.example.olimpiadas.model;

// Clase Participacion
public class Participacion {
    private int idDeportista;
    private int idEvento;
    private int idEquipo;
    private Integer edad;
    private String medalla;

    public Participacion(int idDeportista, int idEvento, int idEquipo, Integer edad, String medalla) {
        this.idDeportista = idDeportista;
        this.idEvento = idEvento;
        this.idEquipo = idEquipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    public int getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(int idDeportista) {
        this.idDeportista = idDeportista;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getMedalla() {
        return medalla;
    }

    public void setMedalla(String medalla) {
        this.medalla = medalla;
    }

    @Override
    public String toString() {
        return "Participacion{idDeportista=" + idDeportista + ", idEvento=" + idEvento + ", idEquipo=" + idEquipo + ", edad=" + edad + ", medalla='" + medalla + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participacion that = (Participacion) o;
        return idDeportista == that.idDeportista && idEvento == that.idEvento;
    }

    @Override
    public int hashCode() {
        return 31 * idDeportista + idEvento;
    }
}
