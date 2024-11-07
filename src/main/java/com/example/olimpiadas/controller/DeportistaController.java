package com.example.olimpiadas.controller;

import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.DAO.DeportistaDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

public class DeportistaController {

    @FXML
    private RadioButton rbHombre;

    @FXML
    private RadioButton rbMujer;

    @FXML
    private ToggleGroup sexo;

    @FXML
    private TextField tfAltura;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfPeso;

    private Stage stage;
    private Deportista deportista;
    private Blob foto; // Mantener la foto seleccionada en un Blob

    /**
     * Establece el stage de la ventana para permitir su cierre.
     *
     * @param stage El objeto {@link Stage} de la ventana que se va a controlar.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Establece el objeto deportista a editar (si existe).
     * Si el deportista no es {@code null}, se llenan los campos con sus datos.
     *
     * @param deportista El objeto {@link Deportista} que se va a editar.
     */
    public void setDeportista(Deportista deportista) {
        this.deportista = deportista;

        if (deportista != null) {
            // Llenar los campos con los datos del deportista
            tfNombre.setText(deportista.getNombre());
            if (deportista.getSexo() == 'M') {
                rbHombre.setSelected(true);
            } else if (deportista.getSexo() == 'F') {
                rbMujer.setSelected(true);
            }
            if (deportista.getPeso() != null) {
                tfPeso.setText(deportista.getPeso().toString());
            }
            if (deportista.getAltura() != null) {
                tfAltura.setText(deportista.getAltura().toString());
            }
            this.foto = deportista.getFoto(); // Asignar foto existente si está disponible
        }
    }

    /**
     * Cancela la acción actual y cierra la ventana modal.
     *
     * @param event El evento de acción que dispara este {@code metodo}.
     */
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();  // Cierra la ventana modal
        }
    }

    /**
     * Guarda los datos del deportista.
     * Si el deportista no es {@code null}, actualiza sus datos en la base de datos,
     * si es un nuevo deportista, lo agrega a la base de datos.
     *
     * @param event El evento de acción que dispara este {@code metodo}.
     */
    @FXML
    void guardar(ActionEvent event) {
        // Validación de los campos requeridos
        String nombre = tfNombre.getText().trim();
        if (nombre.isEmpty()) {
            showError("El nombre del deportista no puede estar vacío.");
            return;
        }

        Deportista.Genero genero;
        if (rbHombre.isSelected()) {
            genero = Deportista.Genero.MALE;
        } else if (rbMujer.isSelected()) {
            genero = Deportista.Genero.FEMALE;
        } else {
            showError("Debe seleccionar el género del deportista.");
            return;
        }

        Integer peso;
        try {
            peso = Integer.parseInt(tfPeso.getText().trim());
        } catch (NumberFormatException e) {
            showError("El peso debe ser un número válido.");
            return;
        }

        Integer altura;
        try {
            altura = Integer.parseInt(tfAltura.getText().trim());
        } catch (NumberFormatException e) {
            showError("La altura debe ser un número válido.");
            return;
        }

        if (deportista == null) {
            // Crear nuevo deportista
            Deportista nuevoDeportista = new Deportista(0, nombre, genero == Deportista.Genero.FEMALE ? 'F' : 'M', peso, altura, foto);
            boolean exito = DeportistaDAO.addDeportista(nuevoDeportista);
            if (exito) {
                showSuccess("Deportista agregado correctamente.");
                stage.close();
            } else {
                showError("No se pudo agregar el deportista.");
            }
        } else {
            // Edición de deportista existente
            boolean cambios = false;
            if (!deportista.getNombre().equals(nombre)) {
                deportista.setNombre(nombre);
                cambios = true;
            }
            if (deportista.getSexo() != (genero == Deportista.Genero.FEMALE ? 'F' : 'M')) {
                deportista.setSexo(genero);
                cambios = true;
            }
            if (!deportista.getPeso().equals(peso)) {
                deportista.setPeso(peso);
                cambios = true;
            }
            if (!deportista.getAltura().equals(altura)) {
                deportista.setAltura(altura);
                cambios = true;
            }
            if (this.foto != null && !this.foto.equals(deportista.getFoto())) {
                deportista.setFoto(this.foto);
                cambios = true;
            }

            if (cambios) {
                boolean exito = DeportistaDAO.updateDeportista(deportista);
                if (exito) {
                    showSuccess("Deportista actualizado correctamente.");
                    stage.close();
                } else {
                    showError("No se pudo actualizar el deportista.");
                }
            } else {
                showError("No se realizaron cambios en los datos del deportista.");
            }
        }
    }

    /**
     * Permite seleccionar una foto para el deportista desde el sistema de archivos.
     * La imagen seleccionada se carga en un {@link Blob}.
     *
     * @param event El evento de acción que dispara este {@code metodo}.
     */
    @FXML
    void seleccionarFoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Foto");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try (FileInputStream fis = new FileInputStream(file)) {
                foto = new javax.sql.rowset.serial.SerialBlob(fis.readAllBytes());
            } catch (IOException | SQLException e) {
                showError("Error al cargar la imagen.");
                e.printStackTrace();
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
}
