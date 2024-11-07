package com.example.olimpiadas.model;

/**
 * Representa un evento dentro de una olimpiada y un deporte específico.
 * Cada evento tiene un identificador único, un nombre, y está relacionado con una olimpiada y un deporte.
 * Esta clase incluye métodos para acceder y modificar los atributos del evento,
 * comparar objetos de tipo `Evento` y obtener una representación en cadena del evento.
 */
public class Evento {

    /**
     * El identificador único del evento.
     */
    private int idEvento;

    /**
     * El nombre del evento.
     */
    private String nombre;

    /**
     * La olimpiada a la que pertenece el evento.
     */
    private Olimpiada olimpiada;

    /**
     * El deporte asociado con el evento.
     */
    private Deporte deporte;

    /**
     * Constructor para crear un nuevo evento con los valores proporcionados.
     *
     * @param idEvento El identificador único del evento.
     * @param nombre El nombre del evento.
     * @param olimpiada La olimpiada a la que pertenece el evento.
     * @param deporte El deporte asociado con el evento.
     */
    public Evento(int idEvento, String nombre, Olimpiada olimpiada, Deporte deporte) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.olimpiada = olimpiada;
        this.deporte = deporte;
    }

    /**
     * Obtiene el identificador único del evento.
     *
     * @return El identificador del evento.
     */
    public int getIdEvento() {
        return idEvento;
    }

    /**
     * Establece el identificador único del evento.
     *
     * @param id_evento El nuevo identificador del evento.
     */
    public void setIdEvento(int id_evento) {
        this.idEvento = id_evento;
    }

    /**
     * Obtiene el nombre del evento.
     *
     * @return El nombre del evento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del evento.
     *
     * @param nombre El nuevo nombre del evento.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la olimpiada a la que pertenece el evento.
     *
     * @return La olimpiada asociada con el evento.
     */
    public Olimpiada getOlimpiada() {
        return olimpiada;
    }

    /**
     * Establece la olimpiada a la que pertenece el evento.
     *
     * @param olimpiada La nueva olimpiada asociada con el evento.
     */
    public void setOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
    }

    /**
     * Obtiene el deporte asociado con el evento.
     *
     * @return El deporte asociado con el evento.
     */
    public Deporte getDeporte() {
        return deporte;
    }

    /**
     * Establece el deporte asociado con el evento.
     *
     * @param deporte El nuevo deporte asociado con el evento.
     */
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
    }

    /**
     * Compara este objeto `Evento` con otro objeto para verificar si son iguales.
     * La comparación se realiza según el `idEvento`, que debe ser único para cada evento.
     *
     * @param o El objeto con el que se va a comparar.
     * @return `true` si los objetos son iguales, de lo contrario `false`.
     */
    @Override
    public boolean equals(Object o) {
        // Verifica si ambos objetos son el mismo
        if (this == o) return true;

        // Verifica si el objeto es una instancia válida de Evento
        if (o == null || getClass() != o.getClass()) return false;

        // Realiza una conversión explícita a Evento
        Evento evento = (Evento) o;

        // Compara los atributos clave (en este caso, idEvento)
        return idEvento == evento.idEvento;
    }

    /**
     * Devuelve el código hash del objeto `Evento`. Se utiliza el `idEvento` como base para el cálculo.
     *
     * @return El código hash del objeto `Evento`.
     */
    @Override
    public int hashCode() {
        // Usa idEvento para calcular el valor de hash
        return Integer.hashCode(idEvento);
    }

    /**
     * Devuelve una representación en cadena del objeto `Evento`, que es el nombre del evento.
     *
     * @return El nombre del evento.
     */
    @Override
    public String toString() {
        return nombre;
    }
}
