package com.example.olimpiadas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PantallaPrincipalController {

    @FXML
    private MenuItem miAniadirDeporte;

    @FXML
    private MenuItem miAniadirDeportista;

    @FXML
    private MenuItem miAniadirEquipo;

    @FXML
    private MenuItem miAniadirEvento;

    @FXML
    private MenuItem miAniadirOlimpiada;

    @FXML
    private MenuItem miAniadirParticipacion;

    @FXML
    private MenuItem miBorrarDeporte;

    @FXML
    private MenuItem miBorrarDeportista;

    @FXML
    private MenuItem miBorrarEquipo;

    @FXML
    private MenuItem miBorrarEvento;

    @FXML
    private MenuItem miBorrarOlimpiada;

    @FXML
    private MenuItem miBorrarParticipacion;

    @FXML
    private MenuItem miEditarDeporte;

    @FXML
    private MenuItem miEditarDeportista;

    @FXML
    private MenuItem miEditarEquipo;

    @FXML
    private MenuItem miEditarEvento;

    @FXML
    private MenuItem miEditarOlimpiada;

    @FXML
    private MenuItem miEditarParticipacion;

    @FXML
    private TableView tabla;

    @FXML
    private TextField tfNombre;

    @FXML
    private ComboBox<String> cbTablaElegida;

    @FXML
    public void initialize() {
        ObservableList<String> tablas = FXCollections.observableArrayList(
                "Deporte", "Deportistas", "Equipos", "Eventos", "Olimpiadas", "Participaciones"
        );
        cbTablaElegida.setItems(tablas);
        cbTablaElegida.getSelectionModel().selectFirst(); // Selecciona el primer elemento por defecto
    }

    @FXML
    void aniadirDeporte(ActionEvent event) {

    }

    @FXML
    void aniadirDeportista(ActionEvent event) {

    }

    @FXML
    void aniadirEquipo(ActionEvent event) {

    }

    @FXML
    void aniadirEvento(ActionEvent event) {

    }

    @FXML
    void aniadirOlimpiada(ActionEvent event) {

    }

    @FXML
    void aniadirParticipacion(ActionEvent event) {

    }

    @FXML
    void borrarAeropuerto(ActionEvent event) {

    }

    @FXML
    void borrarDeporte(ActionEvent event) {

    }

    @FXML
    void borrarDeportista(ActionEvent event) {

    }

    @FXML
    void borrarEquipo(ActionEvent event) {

    }

    @FXML
    void borrarEvento(ActionEvent event) {

    }

    @FXML
    void borrarOlimpiada(ActionEvent event) {

    }

    @FXML
    void borrarParticipacion(ActionEvent event) {

    }

    @FXML
    void editarAeropuerto(ActionEvent event) {

    }

    @FXML
    void editarDeporte(ActionEvent event) {

    }

    @FXML
    void editarDeportista(ActionEvent event) {

    }

    @FXML
    void editarEquipo(ActionEvent event) {

    }

    @FXML
    void editarEvento(ActionEvent event) {

    }

    @FXML
    void editarOlimpiada(ActionEvent event) {

    }

    @FXML
    void editarParticipacion(ActionEvent event) {

    }

    @FXML
    void filtrar(ActionEvent event) {

    }

    @FXML
    void cambiarDeTabla(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            generarDeportes();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            generarDeportistas();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            generarEquipos();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            generarEventos();
        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            generarOlimpiadas();
        } else {
            generarParticipaciones();
        }
    }

    // Métodos adicionales para manejar las tablas específicas
    private void generarDeportes() {
        // Lógica para cargar y mostrar datos de la tabla Deporte
    }

    private void generarDeportistas() {
        // Lógica para cargar y mostrar datos de la tabla Deportistas
    }

    private void generarEquipos() {
        // Lógica para cargar y mostrar datos de la tabla Equipos
    }

    private void generarEventos() {
        // Lógica para cargar y mostrar datos de la tabla Eventos
    }

    private void generarOlimpiadas() {
        // Lógica para cargar y mostrar datos de la tabla Olimpiadas
    }

    private void generarParticipaciones() {
        // Lógica para cargar y mostrar datos de la tabla Participaciones
    }
}
