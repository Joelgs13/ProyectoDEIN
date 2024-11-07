package com.example.olimpiadas.model;

import java.sql.Blob;

/**
 * Representa a un deportista en el sistema, con atributos como nombre, sexo, peso, altura y una foto.
 * Además, se proporciona un metodo para comparar objetos `Deportista` y obtener una representación en cadena
 * del nombre del deportista.
 */
public class Deportista {

    /**
     * El identificador único del deportista.
     */
    private int idDeportista;

    /**
     * El nombre del deportista.
     */
    private String nombre;

    /**
     * El sexo del deportista, representado mediante la enumeración {@link Genero}.
     */
    private Genero sexo;

    /**
     * El peso del deportista en kilogramos.
     */
    private Integer peso;

    /**
     * La altura del deportista en centímetros.
     */
    private Integer altura;

    /**
     * La foto del deportista, almacenada como un objeto {@link Blob}.
     */
    private Blob foto;

    /**
     * Constructor para crear un nuevo deportista con los valores proporcionados.
     *
     * @param idDeportista El identificador único del deportista.
     * @param nombre El nombre del deportista.
     * @param sexo El sexo del deportista, representado por el carácter 'M' (masculino) o 'F' (femenino).
     * @param peso El peso del deportista en kilogramos.
     * @param altura La altura del deportista en centímetros.
     * @param foto La foto del deportista, almacenada como un objeto {@link Blob}.
     */
    public Deportista(int idDeportista, String nombre, char sexo, Integer peso, Integer altura, Blob foto) {
        this.idDeportista = idDeportista;
        this.nombre = nombre;
        this.sexo = getGenero(sexo);
        this.peso = peso;
        this.altura = altura;
        this.foto = foto;
    }

    /**
     * Obtiene el identificador único del deportista.
     *
     * @return El identificador del deportista.
     */
    public int getIdDeportista() {
        return idDeportista;
    }

    /**
     * Establece el identificador único del deportista.
     *
     * @param idDeportista El nuevo identificador del deportista.
     */
    public void setIdDeportista(int idDeportista) {
        this.idDeportista = idDeportista;
    }

    /**
     * Obtiene el nombre del deportista.
     *
     * @return El nombre del deportista.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del deportista.
     *
     * @param nombre El nuevo nombre del deportista.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el sexo del deportista como un carácter: 'M' para masculino y 'F' para femenino.
     *
     * @return El sexo del deportista.
     */
    public char getSexo() {
        if (sexo.equals(Genero.FEMALE)) {
            return 'F';
        }
        return 'M';
    }

    /**
     * Establece el sexo del deportista usando la enumeración {@link Genero}.
     *
     * @param sexo El sexo del deportista.
     */
    public void setSexo(Genero sexo) {
        this.sexo = sexo;
    }

    /**
     * Obtiene el peso del deportista en kilogramos.
     *
     * @return El peso del deportista.
     */
    public Integer getPeso() {
        return peso;
    }

    /**
     * Establece el peso del deportista en kilogramos.
     *
     * @param peso El nuevo peso del deportista.
     */
    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    /**
     * Obtiene la altura del deportista en centímetros.
     *
     * @return La altura del deportista.
     */
    public Integer getAltura() {
        return altura;
    }

    /**
     * Establece la altura del deportista en centímetros.
     *
     * @param altura La nueva altura del deportista.
     */
    public void setAltura(Integer altura) {
        this.altura = altura;
    }

    /**
     * Obtiene la foto del deportista almacenada como un objeto {@link Blob}.
     *
     * @return La foto del deportista.
     */
    public Blob getFoto() {
        return foto;
    }

    /**
     * Establece la foto del deportista.
     *
     * @param foto La nueva foto del deportista.
     */
    public void setFoto(Blob foto) {
        this.foto = foto;
    }

    /**
     * Devuelve una representación en cadena del objeto `Deportista`, que es el nombre del deportista.
     *
     * @return El nombre del deportista.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Enumera los géneros posibles para un deportista.
     */
    public enum Genero {
        MALE, FEMALE;
    }

    /**
     * Convierte un carácter ('M' o 'F') en el valor correspondiente de la enumeración {@link Genero}.
     *
     * @param sex El carácter que representa el sexo del deportista ('M' o 'F').
     * @return El valor correspondiente de la enumeración {@link Genero}.
     */
    public Genero getGenero(char sex) {
        if (sex == 'F') {
            return Genero.FEMALE;
        } else if (sex == 'M') {
            return Genero.MALE;
        }
        return null;
    }

    /**
     * Compara este objeto `Deportista` con otro objeto para verificar si son iguales.
     * La comparación se realiza según el `idDeportista`, que debe ser único para cada deportista.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deportista deportista = (Deportista) o;
        return idDeportista == deportista.idDeportista;
    }

    /**
     * Devuelve el código hash del objeto `Deportista`. Se utiliza el `idDeportista` como base para el cálculo.
     *
     * @return El código hash del objeto `Deportista`.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(idDeportista);
    }
}
