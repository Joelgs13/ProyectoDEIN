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

    @FXML
    public void initialize() {
        cargarDatos(); // Cargar los deportes y olimpiadas al iniciar el controlador
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setParticipacion(Participacion participacion) {
        this.participacion = participacion;
        if (participacion != null) {
            // Asegurarse de que los items de ComboBox estén actualizados
            cargarDatos();

            // Deshabilitar los ComboBox de Evento y Deportista para edición
            cbDeportista.setDisable(true);
            cbEvento.setDisable(true);

            // Seleccionar los valores en el ComboBox usando los objetos de la participación
            cbDeportista.getSelectionModel().select(participacion.getDeportista());
            cbEquipo.getSelectionModel().select(participacion.getEquipo());
            cbEvento.getSelectionModel().select(participacion.getEvento());

            // Asignar los valores de edad y medalla
            tfEdad.setText(String.valueOf(participacion.getEdad()));
            tfMedalla.setText(participacion.getMedalla());
        } else {
            // Si es una nueva Participacion, habilitar los ComboBox de Evento y Deportista
            cbDeportista.setDisable(false);
            cbEvento.setDisable(false);
        }
    }

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

    @FXML
    void cancelar(ActionEvent event) {
        if (stage != null) {
            stage.close();
        }
    }

    @FXML
    void guardar(ActionEvent event) {
        // Obtener y validar los datos de los campos
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
                stage.close();
            } else {
                showError("No se pudo actualizar la participación.");
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

}
