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

    // Método para establecer el deporte a editar (en caso de que sea edición).
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
        if (deporte != null) {
            txtNombre.setText(deporte.getNombre()); // Llenar el campo con el nombre del deporte
        }
    }

    // Método para establecer el Stage (si es necesario para cerrar el modal desde este controlador).
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void guardar() {
        String nombre = txtNombre.getText().trim();

        // Validar que el campo no esté vacío
        if (nombre.isEmpty()) {
            showError("El nombre del deporte no puede estar vacío.");
            return;
        }

        if (deporte == null) {
            // Si el deporte es null, significa que es una inserción (nuevo deporte)
            Deporte nuevoDeporte = new Deporte(0,nombre);
            boolean exito = DeporteDAO.addDeporte(nuevoDeporte); // Usamos el DAO para agregar el deporte
            if (exito) {
                stage.close();
            } else {
                showError("No se pudo agregar el deporte.");
            }
        } else {
            // Si hay un deporte, es una edición (actualizar deporte)
            if (deporte.getNombre().equals(nombre)) {
                showError("El nombre no ha cambiado. No se realiza ninguna actualización.");
                return; // No realizamos la actualización si no hay cambios.
            }
            // Si el nombre ha cambiado, procedemos con la actualización
            deporte.setNombre(nombre);
            boolean exito = DeporteDAO.updateDeporte(deporte);
            if (exito) {
                stage.close();
            } else {
                showError("No se pudo actualizar el deporte.");
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

    // Método para manejar el evento de cancelar
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();  // Cierra la ventana modal
        }
    }

}
