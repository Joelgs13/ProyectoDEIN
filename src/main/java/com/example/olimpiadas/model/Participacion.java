package com.example.olimpiadas.model;

/**
 * Representa la participación de un deportista en un evento de una olimpiada con los detalles sobre
 * el evento, el equipo, la edad del deportista y la medalla obtenida (si es aplicable).
 */
public class Participacion {

    /**
     * El deportista que participó en el evento.
     */
    private Deportista deportista;

    /**
     * El evento en el que el deportista participó.
     */
    private Evento evento;

    /**
     * El equipo al que pertenece el deportista en el evento.
     */
    private Equipo equipo;

    /**
     * La edad del deportista durante el evento.
     */
    private int edad;

    /**
     * La medalla obtenida por el deportista en el evento (si aplica).
     */
    private String medalla;

    /**
     * Constructor para crear una nueva participación de un deportista en un evento con la información
     * sobre el equipo, la edad y la medalla obtenida.
     *
     * @param deportista El deportista que participó en el evento.
     * @param evento El evento en el que participó el deportista.
     * @param equipo El equipo al que pertenece el deportista.
     * @param edad La edad del deportista durante la participación en el evento.
     * @param medalla La medalla obtenida por el deportista (puede ser null si no obtuvo medalla).
     */
    public Participacion(Deportista deportista, Evento evento, Equipo equipo, int edad, String medalla) {
        this.deportista = deportista;
        this.evento = evento;
        this.equipo = equipo;
        this.edad = edad;
        this.medalla = medalla;
    }

    /**
     * Obtiene el deportista que participó en el evento.
     *
     * @return El deportista que participó en el evento.
     */
    public Deportista getDeportista() {
        return deportista;
    }

    /**
     * Establece el deportista que participó en el evento.
     *
     * @param deportista El deportista que participó en el evento.
     */
    public void setDeportista(Deportista deportista) {
        this.deportista = deportista;
    }

    /**
     * Obtiene el evento en el que el deportista participó.
     *
     * @return El evento en el que participó el deportista.
     */
    public Evento getEvento() {
        return evento;
    }

    /**
     * Establece el evento en el que el deportista participó.
     *
     * @param evento El evento en el que participó el deportista.
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    /**
     * Obtiene el equipo al que pertenece el deportista en el evento.
     *
     * @return El equipo al que pertenece el deportista.
     */
    public Equipo getEquipo() {
        return equipo;
    }

    /**
     * Establece el equipo al que pertenece el deportista.
     *
     * @param equipo El equipo al que pertenece el deportista.
     */
    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    /**
     * Obtiene la edad del deportista durante la participación en el evento.
     *
     * @return La edad del deportista.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del deportista durante la participación en el evento.
     *
     * @param edad La edad del deportista.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene la medalla obtenida por el deportista en el evento.
     *
     * @return La medalla obtenida por el deportista (puede ser null si no obtuvo medalla).
     */
    public String getMedalla() {
        return medalla;
    }

    /**
     * Establece la medalla obtenida por el deportista en el evento.
     *
     * @param medalla La medalla obtenida (puede ser null si no obtuvo medalla).
     */
    public void setMedalla(String medalla) {
        this.medalla = medalla;
    }

    /**
     * Compara este objeto `Participacion` con otro objeto para verificar si son iguales.
     * Dos objetos `Participacion` se consideran iguales si tienen el mismo deportista,
     * evento, equipo, edad y medalla.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participacion that = (Participacion) o;
        return edad == that.edad &&
                (deportista != null ? deportista.equals(that.deportista) : that.deportista == null) &&
                (evento != null ? evento.equals(that.evento) : that.evento == null) &&
                (equipo != null ? equipo.equals(that.equipo) : that.equipo == null) &&
                (medalla != null ? medalla.equals(that.medalla) : that.medalla == null);
    }

    /**
     * Devuelve el código hash del objeto `Participacion`. El código hash se calcula a partir de los
     * atributos `deportista`, `evento`, `equipo`, `edad` y `medalla`.
     *
     * @return El código hash del objeto `Participacion`.
     */
    @Override
    public int hashCode() {
        int result = (deportista != null ? deportista.hashCode() : 0);
        result = 31 * result + (evento != null ? evento.hashCode() : 0);
        result = 31 * result + (equipo != null ? equipo.hashCode() : 0);
        result = 31 * result + edad;
        result = 31 * result + (medalla != null ? medalla.hashCode() : 0);
        return result;
    }

    /**
     * Devuelve una representación en cadena del objeto `Participacion`. La cadena incluye los
     * detalles del deportista, evento, equipo, edad y medalla obtenida.
     *
     * @return Una representación en cadena del objeto `Participacion`.
     */
    @Override
    public String toString() {
        return "Participacion{" +
                "deportista=" + (deportista != null ? deportista.getNombre() : "null") +
                ", evento=" + (evento != null ? evento.getNombre() : "null") +
                ", equipo=" + (equipo != null ? equipo.getNombre() : "null") +
                ", edad=" + edad +
                ", medalla='" + medalla + '\'' +
                '}';
    }
}
