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

    public void setEvento(Evento evento) {
        this.evento = evento;
        if (evento != null) {
            tfNombre.setText(evento.getNombre());
            cbOlimpiada.setValue(evento.getOlimpiada());
            cbDeporte.setValue(evento.getDeporte());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void cargarDatos() {
        List<Deporte> listaDeportes = DeporteDAO.findAll();
        List<Olimpiada> listaOlimpiadas = OlimpiadaDAO.getAll();

        cbDeporte.getItems().clear();
        cbDeporte.getItems().addAll(listaDeportes);

        cbOlimpiada.getItems().clear();
        cbOlimpiada.getItems().addAll(listaOlimpiadas);
    }

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

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void showSuccess(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    public void initialize() {
        cargarDatos();
    }
}
