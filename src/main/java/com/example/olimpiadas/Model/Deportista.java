package com.example.olimpiadas.Model;

import java.sql.Blob;

// Clase Deportista
public class Deportista {
    private int idDeportista;
    private String nombre;
    private char sexo;
    private Integer peso;
    private Integer altura;
    private Blob foto;

    public Deportista(int idDeportista, String nombre, char sexo, Integer peso, Integer altura, Blob foto) {
        this.idDeportista = idDeportista;
        this.nombre = nombre;
        this.sexo = sexo;
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    public int getIdDeportista() {
        return idDeportista;
    }

    public void setIdDeportista(int idDeportista) {
        this.idDeportista = idDeportista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getAltura() {
        return altura;
    }

    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    public Blob getFoto() {
        return foto;
    }

    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Deportista{idDeportista=" + idDeportista + ", nombre='" + nombre + "', sexo=" + sexo + ", peso=" + peso + ", altura=" + altura + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deportista that = (Deportista) o;
        return idDeportista == that.idDeportista;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(idDeportista);
    }
}
