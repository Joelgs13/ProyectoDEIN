package com.example.olimpiadas.model;

/**
 * Representa un equipo en el sistema. Un equipo está compuesto por un nombre, iniciales y un identificador único.
 * Esta clase incluye métodos para acceder y modificar los atributos del equipo, así como para comparar objetos
 * de tipo `Equipo` y obtener una representación en cadena del equipo.
 */
public class Equipo {

    /**
     * El identificador único del equipo.
     */
    private int idEquipo;

    /**
     * El nombre del equipo.
     */
    private String nombre;

    /**
     * Las iniciales del equipo, que suelen ser una abreviatura del nombre del equipo.
     */
    private String iniciales;

    /**
     * Constructor para crear un nuevo equipo con los valores proporcionados.
     *
     * @param idEquipo El identificador único del equipo.
     * @param nombre El nombre del equipo.
     * @param iniciales Las iniciales del equipo.
     */
    public Equipo(int idEquipo, String nombre, String iniciales) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.iniciales = iniciales;
    }

    /**
     * Obtiene el identificador único del equipo.
     *
     * @return El identificador del equipo.
     */
    public int getIdEquipo() {
        return idEquipo;
    }

    /**
     * Establece el identificador único del equipo.
     *
     * @param idEquipo El nuevo identificador del equipo.
     */
    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return El nombre del equipo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param nombre El nuevo nombre del equipo.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene las iniciales del equipo.
     *
     * @return Las iniciales del equipo.
     */
    public String getIniciales() {
        return iniciales;
    }

    /**
     * Establece las iniciales del equipo.
     *
     * @param iniciales Las nuevas iniciales del equipo.
     */
    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    /**
     * Devuelve una representación en cadena del objeto `Equipo`, que es el nombre del equipo.
     *
     * @return El nombre del equipo.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Compara este objeto `Equipo` con otro objeto para verificar si son iguales.
     * La comparación se realiza según el `idEquipo`, que debe ser único para cada equipo.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipo equipo = (Equipo) o;
        return idEquipo == equipo.idEquipo;
    }

    /**
     * Devuelve el código hash del objeto `Equipo`. Se utiliza el `idEquipo` como base para el cálculo.
     *
     * @return El código hash del objeto `Equipo`.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(idEquipo);
    }
}
