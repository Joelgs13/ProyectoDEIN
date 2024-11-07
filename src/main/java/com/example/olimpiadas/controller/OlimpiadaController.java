package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Olimpiada;
import com.example.olimpiadas.DAO.OlimpiadaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class OlimpiadaController {

    @FXML
    private RadioButton rbInvierno;

    @FXML
    private RadioButton rbVerano;

    @FXML
    private ToggleGroup temporada;

    @FXML
    private TextField tfAnio;

    @FXML
    private TextField tfCiudad;

    @FXML
    private TextField tfNombre;

    private Stage stage;
    private Olimpiada olimpiada;

    /**
     * Establece la olimpiada a editar.
     * Si la olimpiada no es {@code null}, llena los campos con los datos de la olimpiada.
     *
     * @param olimpiada El objeto {@link Olimpiada} que se va a editar.
     */
    public void setOlimpiada(Olimpiada olimpiada) {
        this.olimpiada = olimpiada;
        if (olimpiada != null) {
            tfNombre.setText(olimpiada.getNombre());
            tfAnio.setText(String.valueOf(olimpiada.getAnio()));
            tfCiudad.setText(olimpiada.getCiudad());

            if (olimpiada.getTemporada().equals("Winter")) {
                rbInvierno.setSelected(true);
            } else {
                rbVerano.setSelected(true);
            }
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
     * Guarda la olimpiada en la base de datos.
     * Si la olimpiada no es {@code null}, actualiza sus datos; si es una nueva olimpiada, la agrega.
     *
     * @see OlimpiadaDAO#addOlimpiada(Olimpiada)
     * @see OlimpiadaDAO#updateOlimpiada(Olimpiada)
     */
    @FXML
    public void guardar(ActionEvent event) {
        String nombre = tfNombre.getText().trim();
        String anioStr = tfAnio.getText().trim();
        String ciudad = tfCiudad.getText().trim();

        if (nombre.isEmpty() || anioStr.isEmpty() || ciudad.isEmpty()) {
            showError("Todos los campos son obligatorios.");
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
        } catch (NumberFormatException e) {
            showError("El año debe ser un número válido.");
            return;
        }

        String temporadaSeleccionada = rbInvierno.isSelected() ? "Winter" : "Summer";

        if (olimpiada == null) {
            Olimpiada nuevaOlimpiada = new Olimpiada(0, nombre, anio, temporadaSeleccionada, ciudad);
            boolean exito = OlimpiadaDAO.addOlimpiada(nuevaOlimpiada);
            if (exito) {
                showSuccess("Olimpiada agregada correctamente.");
                stage.close();
            } else {
                showError("No se pudo agregar la olimpiada.");
            }
        } else {
            if (olimpiada.getNombre().equals(nombre) && olimpiada.getAnio() == anio && olimpiada.getCiudad().equals(ciudad) && olimpiada.getTemporada().equals(temporadaSeleccionada)) {
                showError("No hay cambios para guardar.");
                return;
            }

            olimpiada.setNombre(nombre);
            olimpiada.setAnio(anio);
            olimpiada.setCiudad(ciudad);
            olimpiada.setTipoTemporada(Olimpiada.TipoTemporada.valueOf(temporadaSeleccionada));

            boolean exito = OlimpiadaDAO.updateOlimpiada(olimpiada);
            if (exito) {
                showSuccess("Olimpiada actualizada correctamente.");
                stage.close();
            } else {
                showError("No se pudo actualizar la olimpiada.");
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
}
