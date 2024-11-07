package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.model.Olimpiada;
import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.DAO.DeporteDAO;
import com.example.olimpiadas.DAO.OlimpiadaDAO;
import com.example.olimpiadas.DAO.EventoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class EventoController {

    @FXML
    private ComboBox<Deporte> cbDeporte;

    @FXML
    private ComboBox<Olimpiada> cbOlimpiada;

    @FXML
    private TextField tfNombre;

    private Stage stage;
    private Evento evento;

    /**
     * Establece el evento a editar.
     * Si el evento no es {@code null}, llena los campos con los datos del evento.
     *
     * @param evento El objeto {@link Evento} que se va a editar.
     */
    public void setEvento(Evento evento) {
        this.evento = evento;
        if (evento != null) {
            tfNombre.setText(evento.getNombre());
            cbOlimpiada.setValue(evento.getOlimpiada());
            cbDeporte.setValue(evento.getDeporte());
        }
    }

    /**
     * Establece el stage para permitir su cierre.
     *
     * @param stage El objeto {@link Stage} de la ventana que se va a controlar.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Carga los datos necesarios para llenar los {@link ComboBox} de Deporte y Olimpiada.
     */
    public void cargarDatos() {
        List<Deporte> listaDeportes = DeporteDAO.findAll();
        List<Olimpiada> listaOlimpiadas = OlimpiadaDAO.getAll();

        cbDeporte.getItems().clear();
        cbDeporte.getItems().addAll(listaDeportes);

        cbOlimpiada.getItems().clear();
        cbOlimpiada.getItems().addAll(listaOlimpiadas);
    }

    /**
     * Guarda el evento en la base de datos.
     * Si el evento no es {@code null}, actualiza sus datos; si es un nuevo evento, lo agrega.
     *
     * @see EventoDAO#addEvento(Evento)
     * @see EventoDAO#updateEvento(Evento)
     */
    @FXML
    public void guardar() {
        String nombre = tfNombre.getText().trim();
        Olimpiada olimpiada = cbOlimpiada.getValue();
        Deporte deporte = cbDeporte.getValue();

        if (nombre.isEmpty()) {
            showError("El nombre del evento no puede estar vacío.");
            return;
        }
        if (olimpiada == null) {
            showError("Por favor, seleccione una Olimpiada.");
            return;
        }
        if (deporte == null) {
            showError("Por favor, seleccione un Deporte.");
            return;
        }

        if (evento == null) {
            // Inserción de nuevo evento
            Evento nuevoEvento = new Evento(0, nombre, olimpiada, deporte);
            boolean exito = EventoDAO.addEvento(nuevoEvento);
            if (exito) {
                showSuccess("Evento agregado correctamente.");
                stage.close();
            } else {
                showError("No se pudo agregar el evento.");
            }
        } else {
            // Edición de evento existente
            if (evento.getNombre().equals(nombre) && evento.getOlimpiada().equals(olimpiada) && evento.getDeporte().equals(deporte)) {
                showError("No se han realizado cambios en el evento.");
                return;
            }
            evento.setNombre(nombre);
            evento.setOlimpiada(olimpiada);
            evento.setDeporte(deporte);
            boolean exito = EventoDAO.updateEvento(evento);
            if (exito) {
                showSuccess("Evento actualizado correctamente.");
                stage.close();
            } else {
                showError("No se pudo actualizar el evento.");
            }
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje El mensaje de error que se va a mostrar.
     */
    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de éxito al usuario.
     *
     * @param mensaje El mensaje de éxito que se va a mostrar.
     */
    private void showSuccess(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Cancela la acción actual y cierra la ventana modal.
     *
     * @param event El evento de acción que dispara este metodo.
     */
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Inicializa los datos del controlador cargando los elementos de los {@link ComboBox}.
     */
    @FXML
    public void initialize() {
        cargarDatos();
    }
}
