package com.example.olimpiadas.model;

/**
 * Representa un deporte en el sistema, con un identificador único y un nombre.
 * Esta clase proporciona métodos para acceder y modificar los datos del deporte,
 * así como métodos para comparar objetos `Deporte` y obtener una representación en
 * cadena del nombre del deporte.
 */
public class Deporte {

    /**
     * El identificador único del deporte.
     */
    private int idDeporte;

    /**
     * El nombre del deporte.
     */
    private String nombre;

    /**
     * Constructor que crea un nuevo objeto `Deporte` con el identificador y nombre proporcionados.
     *
     * @param idDeporte El identificador único del deporte.
     * @param nombre El nombre del deporte.
     */
    public Deporte(int idDeporte, String nombre) {
        this.idDeporte = idDeporte;
        this.nombre = nombre;
    }

    /**
     * Obtiene el identificador único del deporte.
     *
     * @return El identificador del deporte.
     */
    public int getIdDeporte() {
        return idDeporte;
    }

    /**
     * Establece el identificador único del deporte.
     *
     * @param idDeporte El nuevo identificador del deporte.
     */
    public void setIdDeporte(int idDeporte) {
        this.idDeporte = idDeporte;
    }

    /**
     * Obtiene el nombre del deporte.
     *
     * @return El nombre del deporte.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del deporte.
     *
     * @param nombre El nuevo nombre del deporte.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve una representación en cadena del objeto `Deporte`, que es el nombre del deporte.
     *
     * @return El nombre del deporte.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Compara este objeto `Deporte` con otro objeto para verificar si son iguales.
     * La comparación se realiza según el `idDeporte`, que debe ser único para cada deporte.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deporte deporte = (Deporte) o;
        return idDeporte == deporte.idDeporte;
    }

    /**
     * Devuelve el código hash del objeto `Deporte`. Se utiliza el `idDeporte` como base para el cálculo.
     *
     * @return El código hash del objeto `Deporte`.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(idDeporte);
    }
}
