package com.example.olimpiadas.controller;

import com.example.olimpiadas.DAO.DeportistaDAO;
import com.example.olimpiadas.DAO.EquipoDAO;
import com.example.olimpiadas.DAO.EventoDAO;
import com.example.olimpiadas.DAO.ParticipacionDAO;
import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.model.Participacion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class ParticipacionController {

    @FXML
    private ComboBox<Deportista> cbDeportista;

    @FXML
    private ComboBox<Equipo> cbEquipo;

    @FXML
    private ComboBox<Evento> cbEvento;

    @FXML
    private TextField tfEdad;

    @FXML
    private TextField tfMedalla;

    private Stage stage;
    private Participacion participacion;

    /**
     * Inicializa la vista cargando los datos necesarios en las combinaciones de campos.
     */
    @FXML
    public void initialize() {
        cargarDatos();
    }

    /**
     * Establece el stage de la ventana actual.
     *
     * @param stage El stage que representa la ventana.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Establece los valores de la participacion para su edición.
     * Si la participacion no es {@code null}, se cargan los datos correspondientes.
     *
     * @param participacion El objeto {@link Participacion} que se desea editar.
     */
    public void setParticipacion(Participacion participacion) {
        this.participacion = participacion;
        if (participacion != null) {
            cargarDatos();
            cbDeportista.setDisable(true);
            cbEvento.setDisable(true);
            cbDeportista.getSelectionModel().select(participacion.getDeportista());
            cbEquipo.getSelectionModel().select(participacion.getEquipo());
            cbEvento.getSelectionModel().select(participacion.getEvento());
            tfEdad.setText(String.valueOf(participacion.getEdad()));
            tfMedalla.setText(participacion.getMedalla());
        } else {
            cbDeportista.setDisable(false);
            cbEvento.setDisable(false);
        }
    }

    /**
     * Carga los datos de deportistas, equipos y eventos en las respectivas combinaciones de campos.
     */
    public void cargarDatos() {
        List<Deportista> listaDeportistas = DeportistaDAO.findAll();
        List<Equipo> listaEquipos = EquipoDAO.findAll();
        List<Evento> listaEventos = EventoDAO.getAll();

        cbDeportista.getItems().clear();
        cbDeportista.getItems().addAll(listaDeportistas);

        cbEquipo.getItems().clear();
        cbEquipo.getItems().addAll(listaEquipos);

        cbEvento.getItems().clear();
        cbEvento.getItems().addAll(listaEventos);
    }

    /**
     * Cancela la operación actual y cierra la ventana de la aplicación.
     *
     * @param event El evento que dispara este metodo.
     */
    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Guarda la participación en la base de datos. Si la participacion es {@code null}, crea una nueva.
     * Si la participacion ya existe, actualiza los datos de la misma.
     * Muestra mensajes de error o éxito según el resultado.
     *
     * @param event El evento que dispara este metodo.
     */
    @FXML
    void guardar(ActionEvent event) {
        Deportista deportista = cbDeportista.getValue();
        Evento evento = cbEvento.getValue();
        Equipo equipo = cbEquipo.getValue();
        String edadText = tfEdad.getText().trim();
        String medalla = tfMedalla.getText().trim();

        if (deportista == null) {
            showError("Por favor, seleccione un deportista.");
            return;
        }
        if (evento == null) {
            showError("Por favor, seleccione un evento.");
            return;
        }
        if (equipo == null) {
            showError("Por favor, seleccione un equipo.");
            return;
        }
        if (edadText.isEmpty()) {
            showError("La edad no puede estar vacía.");
            return;
        }
        if (medalla.isEmpty()) {
            showError("El campo de la medalla no puede estar vacío.");
            return;
        }

        int edad;
        try {
            edad = Integer.parseInt(edadText);
        } catch (NumberFormatException e) {
            showError("La edad debe ser un número.");
            return;
        }

        if (participacion == null) {
            Participacion nuevaParticipacion = new Participacion(deportista, evento, equipo, edad, medalla);
            boolean exito = ParticipacionDAO.addParticipacion(nuevaParticipacion);

            if (exito) {
                showSuccess("Participación agregada correctamente.");
                stage.close();
            } else {
                showError("No se pudo agregar la participación.");
            }
        } else {
            if (participacion.getDeportista().equals(deportista) &&
                    participacion.getEvento().equals(evento) &&
                    participacion.getEquipo().equals(equipo) &&
                    participacion.getEdad() == edad &&
                    participacion.getMedalla().equals(medalla)) {
                showError("No se han realizado cambios en la participación.");
                return;
            }

            participacion.setDeportista(deportista);
            participacion.setEvento(evento);
            participacion.setEquipo(equipo);
            participacion.setEdad(edad);
            participacion.setMedalla(medalla);

            boolean exito = ParticipacionDAO.updateParticipacion(participacion);
            if (exito) {
                showSuccess("Participación actualizada correctamente.");
                stage.close();
            } else {
                showError("No se pudo actualizar la participación.");
            }
        }
    }

    /**
     * Muestra un mensaje de error en una ventana emergente.
     *
     * @param mensaje El mensaje que se va a mostrar.
     */
    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    /**
     * Muestra un mensaje de éxito en una ventana emergente.
     *
     * @param mensaje El mensaje que se va a mostrar.
     */
    private void showSuccess(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
