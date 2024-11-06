package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.DAO.DeporteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.zone.ZoneRulesProvider;

public class DeporteController {

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Deporte deporte;

    // Este método se llamará desde el controlador principal para pasar el Stage a la ventana modal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para manejar el evento de cancelar
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();  // Cierra la ventana modal
        }
    }

    // Método para manejar el evento de guardar
    @FXML
    void guardar(ActionEvent event) {
        String nombreDeporte = txtNombre.getText().trim();

        // Validar que el nombre no esté vacío
        if (nombreDeporte.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "El nombre del deporte no puede estar vacío.", ButtonType.OK);
            alert.setTitle("Advertencia");
            alert.showAndWait();
            return;
        }

        // Crear el objeto Deporte
        Deporte nuevoDeporte = new Deporte(0,nombreDeporte);

        // Intentar agregar el deporte a la base de datos
        boolean success = DeporteDAO.addDeporte(nuevoDeporte);

        if (success) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Deporte añadido correctamente.", ButtonType.OK);
            alert.setTitle("Éxito");
            alert.showAndWait();
            cancelar(event);  // Cierra la ventana después de guardar
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Hubo un problema al añadir el deporte.", ButtonType.OK);
            alert.setTitle("Error");
            alert.showAndWait();
        }
    }

    public void setDeporte(Deporte deporteSeleccionado) {
        this.deporte=deporteSeleccionado;
    }
}
