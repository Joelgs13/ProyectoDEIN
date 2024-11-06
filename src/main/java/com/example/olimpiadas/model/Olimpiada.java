package com.example.olimpiadas.model;

// Clase Olimpiada
public class Olimpiada {

    private int idOlimpiada;
    private String nombre;
    private int anio;
    private TipoTemporada temporada;
    private String ciudad;

    public Olimpiada(int idOlimpiada, String nombre, int anio, String temporada, String ciudad) {
        this.idOlimpiada = idOlimpiada;
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = getTipoTemporada(temporada);
        this.ciudad = ciudad;
    }

    public int getIdOlimpiada() {
        return idOlimpiada;
    }

    public void setIdOlimpiada(int idOlimpiada) {
        this.idOlimpiada = idOlimpiada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getTemporada() {
        if (temporada.equals(TipoTemporada.Winter)) {
            return "Winter";
        }
        return "Summer";
    }

    public void setTipoTemporada(TipoTemporada temporada) {
        this.temporada = temporada;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public enum TipoTemporada {
        Summer,Winter;
    }

    public TipoTemporada getTipoTemporada(String temporada) {
        if (temporada.equals("Winter")) {
            return TipoTemporada.Winter;
        } else if (temporada.equals("Summer")) {
            return TipoTemporada.Summer;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Olimpiada{idOlimpiada=" + idOlimpiada + ", nombre='" + nombre + "', anio=" + anio + ", temporada='" + temporada + "', ciudad='" + ciudad + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Olimpiada olimpiada = (Olimpiada) o;
        return idOlimpiada == olimpiada.idOlimpiada;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idOlimpiada);
    }
}
