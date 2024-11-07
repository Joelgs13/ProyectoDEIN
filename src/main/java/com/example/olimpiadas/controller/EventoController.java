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

    // Metodo para establecer el evento a editar (en caso de que sea edición)
    public void setEvento(Evento evento) {
        this.evento = evento;
        if (evento != null) {
            tfNombre.setText(evento.getNombre()); // Llenar el campo con el nombre del evento
            cbOlimpiada.setValue(evento.getOlimpiada()); // Llenar el ComboBox de Olimpiada
            cbDeporte.setValue(evento.getDeporte()); // Llenar el ComboBox de Deporte
        }
    }

    // Metodo para establecer el Stage (si es necesario para cerrar el modal desde este controlador)
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Metodo para cargar los datos en los ComboBox
    public void cargarDatos() {
        List<Deporte> listaDeportes = DeporteDAO.findAll(); // Metodo que obtiene todos los deportes
        List<Olimpiada> listaOlimpiadas = OlimpiadaDAO.getAll(); // Metodo que obtiene todas las olimpiadas

        // Llenar los ComboBox con los datos obtenidos
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

        // Validar que los campos no estén vacíos
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
            // Si el evento es null, significa que es una inserción (nuevo evento)
            Evento nuevoEvento = new Evento(0, nombre, olimpiada, deporte);
            boolean exito = EventoDAO.addEvento(nuevoEvento); // Usamos el DAO para agregar el evento
            if (exito) {
                stage.close();
            } else {
                showError("No se pudo agregar el evento.");
            }
        } else {
            // Si hay un evento, es una edición (actualizar evento)
            if (evento.getNombre().equals(nombre) && evento.getOlimpiada().equals(olimpiada) && evento.getDeporte().equals(deporte)) {
                showError("No se han realizado cambios en el evento.");
                return; // No realizamos la actualización si no hay cambios.
            }
            // Si los datos han cambiado, procedemos con la actualización
            evento.setNombre(nombre);
            evento.setOlimpiada(olimpiada);
            evento.setDeporte(deporte);
            boolean exito = EventoDAO.updateEvento(evento);
            if (exito) {
                stage.close();
            } else {
                showError("No se pudo actualizar el evento.");
            }
        }
    }

    private void showError(String mensaje) {
        // Mostrar error al usuario (se puede personalizar)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Metodo para manejar el evento de cancelar
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();  // Cierra la ventana modal
        }
    }

    // Metodo que se ejecuta cuando se muestra el controlador
    @FXML
    public void initialize() {
        cargarDatos(); // Cargar los deportes y olimpiadas al iniciar el controlador
    }
}
