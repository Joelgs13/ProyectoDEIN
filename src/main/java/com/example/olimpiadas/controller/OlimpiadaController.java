package com.example.olimpiadas.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

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

    @FXML
    void cancelar(ActionEvent event) {

    }

    @FXML
    void guardar(ActionEvent event) {

    }

}
