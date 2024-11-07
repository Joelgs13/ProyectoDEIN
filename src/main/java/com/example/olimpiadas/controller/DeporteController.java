package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.DAO.DeporteDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DeporteController {

    @FXML
    private TextField txtNombre;

    private Stage stage;
    private Deporte deporte;

    /**
     * Metodo para establecer el deporte a editar (en caso de que sea edición).
     *
     * @param deporte El objeto {@link Deporte} que se va a editar.
     */
    public void setDeporte(Deporte deporte) {
        this.deporte = deporte;
        if (deporte != null) {
            txtNombre.setText(deporte.getNombre()); // Llenar el campo con el nombre del deporte
        }
    }

    /**
     * Metodo para establecer el Stage (si es necesario para cerrar el modal desde este controlador).
     *
     * @param stage El objeto {@link Stage} de la ventana que se va a controlar.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Metodo que guarda los cambios realizados en el nombre del deporte.
     * Si el campo está vacío o no se han realizado cambios, muestra un mensaje de error.
     * Si el deporte es nuevo, lo agrega a la base de datos. Si es una edición, actualiza el deporte existente.
     */
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
            Deporte nuevoDeporte = new Deporte(0, nombre);
            boolean exito = DeporteDAO.addDeporte(nuevoDeporte); // Usamos el DAO para agregar el deporte
            if (exito) {
                showConfirmation("El deporte ha sido añadido exitosamente.");
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
                showConfirmation("El deporte ha sido actualizado exitosamente.");
                stage.close();
            } else {
                showError("No se pudo actualizar el deporte.");
            }
        }
    }

    /**
     * Muestra un mensaje de error al usuario.
     *
     * @param mensaje El mensaje de error a mostrar.
     */
    private void showError(String mensaje) {
        // Mostrar error al usuario
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de confirmación al usuario.
     *
     * @param mensaje El mensaje de confirmación a mostrar.
     */
    private void showConfirmation(String mensaje) {
        // Mostrar confirmación al usuario
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Metodo para manejar el evento de cancelar.
     * Cierra la ventana modal si el stage no es null.
     *
     * @param event El evento de acción que dispara el metodo (no se utiliza en este caso).
     */
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();  // Cierra la ventana modal
        }
    }
}
