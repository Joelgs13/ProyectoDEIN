package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.DAO.EquipoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EquipoController {

    @FXML
    private TextField tfIniciales;

    @FXML
    private TextField tfNombre;

    private Stage stage;
    private Equipo equipo;

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;

        if (equipo != null) {
            // Llenar el campo de nombre
            tfNombre.setText(equipo.getNombre());

            // Llenar el campo de iniciales
            tfIniciales.setText(equipo.getIniciales());
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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
            stage.close();  // Cierra la ventana modal
        }
    }

    @FXML
    public void guardar() {
        String nombre = tfNombre.getText().trim();
        String iniciales = tfIniciales.getText().trim();

        // Validar que los campos no estén vacíos
        if (nombre.isEmpty()) {
            showError("El nombre del equipo no puede estar vacío.");
            return;
        }

        if (iniciales.isEmpty()) {
            showError("Las iniciales del equipo no pueden estar vacías.");
            return;
        }

        if (equipo == null) {
            // Si el equipo es null, significa que es una inserción (nuevo equipo)
            Equipo nuevoEquipo = new Equipo(0, nombre, iniciales);
            boolean exito = EquipoDAO.addEquipo(nuevoEquipo); // Usamos el DAO para agregar el equipo
            if (exito) {
                showSuccess("Equipo agregado correctamente.");
                stage.close();
            } else {
                showError("No se pudo agregar el equipo.");
            }
        } else {
            // Si hay un equipo, es una edición (actualizar equipo)
            boolean cambios = false;

            // Comprobar si el nombre ha cambiado
            if (!equipo.getNombre().equals(nombre)) {
                equipo.setNombre(nombre);
                cambios = true;
            }

            // Comprobar si las iniciales han cambiado
            if (!equipo.getIniciales().equals(iniciales)) {
                equipo.setIniciales(iniciales);
                cambios = true;
            }

            // Si hubo cambios, actualizar el equipo
            if (cambios) {
                boolean exito = EquipoDAO.updateEquipo(equipo);
                if (exito) {
                    showSuccess("Equipo actualizado correctamente.");
                    stage.close();
                } else {
                    showError("No se pudo actualizar el equipo.");
                }
            } else {
                showError("No se realizaron cambios en los datos del equipo.");
            }
        }
    }
}
