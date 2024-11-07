package com.example.olimpiadas.model;

/**
 * Representa una olimpiada, con detalles como su identificador, nombre, año, temporada y ciudad.
 * La clase incluye métodos para acceder y modificar estos atributos,
 * así como para comparar dos objetos de tipo `Olimpiada` y obtener una representación en cadena.
 */
public class Olimpiada {

    /**
     * El identificador único de la olimpiada.
     */
    private int idOlimpiada;

    /**
     * El nombre de la olimpiada.
     */
    private String nombre;

    /**
     * El año en que se celebró la olimpiada.
     */
    private int anio;

    /**
     * La temporada en la que se llevó a cabo la olimpiada (Verano o Invierno).
     */
    private TipoTemporada temporada;

    /**
     * La ciudad donde se celebró la olimpiada.
     */
    private String ciudad;

    /**
     * Constructor para crear una nueva olimpiada con los valores proporcionados.
     *
     * @param idOlimpiada El identificador único de la olimpiada.
     * @param nombre El nombre de la olimpiada.
     * @param anio El año en que se celebró la olimpiada.
     * @param temporada La temporada (verano o invierno) en la que se celebró la olimpiada.
     * @param ciudad La ciudad donde se celebró la olimpiada.
     */
    public Olimpiada(int idOlimpiada, String nombre, int anio, String temporada, String ciudad) {
        this.idOlimpiada = idOlimpiada;
        this.nombre = nombre;
        this.anio = anio;
        this.temporada = getTipoTemporada(temporada);
        this.ciudad = ciudad;
    }

    /**
     * Obtiene el identificador único de la olimpiada.
     *
     * @return El identificador de la olimpiada.
     */
    public int getIdOlimpiada() {
        return idOlimpiada;
    }

    /**
     * Establece el identificador único de la olimpiada.
     *
     * @param idOlimpiada El nuevo identificador de la olimpiada.
     */
    public void setIdOlimpiada(int idOlimpiada) {
        this.idOlimpiada = idOlimpiada;
    }

    /**
     * Obtiene el nombre de la olimpiada.
     *
     * @return El nombre de la olimpiada.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la olimpiada.
     *
     * @param nombre El nuevo nombre de la olimpiada.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el año en que se celebró la olimpiada.
     *
     * @return El año de la olimpiada.
     */
    public int getAnio() {
        return anio;
    }

    /**
     * Establece el año de la olimpiada.
     *
     * @param anio El nuevo año de la olimpiada.
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * Obtiene la temporada en la que se celebró la olimpiada, ya sea verano o invierno.
     *
     * @return El nombre de la temporada ("Summer" o "Winter").
     */
    public String getTemporada() {
        if (temporada.equals(TipoTemporada.Winter)) {
            return "Winter";
        }
        return "Summer";
    }

    /**
     * Establece el tipo de temporada de la olimpiada (verano o invierno).
     *
     * @param temporada La temporada (tipo `TipoTemporada`) de la olimpiada.
     */
    public void setTipoTemporada(TipoTemporada temporada) {
        this.temporada = temporada;
    }

    /**
     * Obtiene la ciudad en la que se celebró la olimpiada.
     *
     * @return El nombre de la ciudad de la olimpiada.
     */
    public String getCiudad() {
        return ciudad;
    }

    /**
     * Establece la ciudad en la que se celebró la olimpiada.
     *
     * @param ciudad El nuevo nombre de la ciudad.
     */
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Enum que representa las dos temporadas posibles de la olimpiada:
     * Verano (Summer) e Invierno (Winter).
     */
    public enum TipoTemporada {
        Summer, Winter;
    }

    /**
     * Convierte una cadena de texto a un valor del tipo `TipoTemporada`.
     *
     * @param temporada La cadena que representa la temporada ("Summer" o "Winter").
     * @return El valor correspondiente del tipo `TipoTemporada`.
     */
    public TipoTemporada getTipoTemporada(String temporada) {
        if (temporada.equals("Winter")) {
            return TipoTemporada.Winter;
        } else if (temporada.equals("Summer")) {
            return TipoTemporada.Summer;
        }
        return null;
    }

    /**
     * Devuelve una representación en cadena del objeto `Olimpiada`, que es el nombre de la olimpiada.
     *
     * @return El nombre de la olimpiada.
     */
    @Override
    public String toString() {
        return nombre;
    }

    /**
     * Compara este objeto `Olimpiada` con otro objeto para verificar si son iguales.
     * La comparación se realiza según el `idOlimpiada`, que debe ser único para cada olimpiada.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Olimpiada olimpiada = (Olimpiada) o;
        return idOlimpiada == olimpiada.idOlimpiada;
    }

    /**
     * Devuelve el código hash del objeto `Olimpiada`. Se utiliza `idOlimpiada` como base para el cálculo.
     *
     * @return El código hash del objeto `Olimpiada`.
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(idOlimpiada);
    }
}
