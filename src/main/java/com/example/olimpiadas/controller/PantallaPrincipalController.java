package com.example.olimpiadas.controller;

import com.example.olimpiadas.DAO.DeporteDAO;
import com.example.olimpiadas.DAO.OlimpiadaDAO;
import com.example.olimpiadas.DAO.DeportistaDAO;
import com.example.olimpiadas.DAO.EquipoDAO;
import com.example.olimpiadas.DAO.EventoDAO;
import com.example.olimpiadas.DAO.ParticipacionDAO;
import com.example.olimpiadas.model.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

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
    public TableView tabla;

    public TableView getTabla() {
        return this.tabla;
    }

    @FXML
    private TextField tfNombre;

    @FXML
    private ComboBox<String> cbTablaElegida;

    private Stage stage;

    // Método para recibir el Stage desde la clase principal
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        ObservableList<String> tablas = FXCollections.observableArrayList(
                "Deporte", "Deportistas", "Equipos", "Eventos", "Olimpiadas", "Participaciones"
        );
        cbTablaElegida.setItems(tablas);
        cbTablaElegida.getSelectionModel().selectFirst(); // Selecciona el primer elemento por defecto
        generarDeportes();
    }


    // Método que abre la ventana de deportes (en el controlador principal)
    @FXML
    public void aniadirDeporte() {
        try {
            // Cargar el FXML de la ventana modal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deporte.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            DeporteController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Deporte");
            stage.showAndWait();
            cambiarDeTabla(null);
            //System.out.println("HEY AQUI ESTOY");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    void filtrar(KeyEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            ObservableList<Deportista> todosDeportistas = DeportistaDAO.findAll();
            tabla.setItems(todosDeportistas.filtered(Deportista ->
                    Deportista.getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));

        }else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            ObservableList<Deporte> todosDeportes = DeporteDAO.findAll();
            tabla.setItems(todosDeportes.filtered(Deporte ->
                    Deporte.getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));

        }else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            ObservableList<Equipo> todosEquipos = EquipoDAO.findAll();
            tabla.setItems(todosEquipos.filtered(Equipo ->
                    Equipo.getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));

        }else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            ObservableList<Olimpiada> todasOlimpiadas = OlimpiadaDAO.getAll();
            tabla.setItems(todasOlimpiadas.filtered(Olimpiada ->
                    Olimpiada.getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));

        }else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            ObservableList<Evento> todosEventos = EventoDAO.getAll();
            tabla.setItems(todosEventos.filtered(Evento ->
                    Evento.getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));
        }else {
            ObservableList<Participacion> todasParticipaciones = ParticipacionDAO.findAll();
            tabla.setItems(todasParticipaciones.filtered(Participacion ->
                    Participacion.getDeportista().getNombre().toLowerCase().contains(tfNombre.getText().toLowerCase())));
        }
        tabla.refresh();
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

    @FXML
    void generarDeportes()  {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Deporte
        TableColumn<Deporte, Integer> colIdDeporte = new TableColumn<>("ID Deporte");
        colIdDeporte.setCellValueFactory(new PropertyValueFactory<>("idDeporte"));

        TableColumn<Deporte, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdDeporte, colNombre);

        // Cargar los datos en la tabla
        ObservableList<Deporte> deportes = DeporteDAO.findAll();
        tabla.setItems(deportes);
    }

    @FXML
    void generarDeportistas() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Deportista
        TableColumn<Deportista, Integer> colIdDeportista = new TableColumn<>("ID Deportista");
        colIdDeportista.setCellValueFactory(new PropertyValueFactory<>("idDeportista"));

        TableColumn<Deportista, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Deportista, String> colSexo = new TableColumn<>("Sexo");
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));

        TableColumn<Deportista, Integer> colPeso = new TableColumn<>("Peso");
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));

        TableColumn<Deportista, Integer> colAltura = new TableColumn<>("Altura");
        colAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        TableColumn<Deportista, Blob> colFoto = new TableColumn<>("Foto");
        colFoto.setCellValueFactory(new PropertyValueFactory<>("foto"));

        // Configurar el cell factory para mostrar las imágenes
        colFoto.setCellFactory(column -> new TableCell<Deportista, Blob>() {
            private final ImageView imageView = new ImageView();
            private final Image imagenPorDefecto = new Image(getClass().getResourceAsStream("/com/example/olimpiadas/img/iconitoOlimpiadas.png"));

            @Override
            protected void updateItem(Blob item, boolean empty) {
                super.updateItem(item, empty);

                // Asegúrate de limpiar el gráfico si la celda está vacía
                if (empty) {
                    setGraphic(null);
                } else {
                    if (item == null) {
                        // No hay imagen, usar imagen por defecto
                        imageView.setImage(imagenPorDefecto);
                    } else {
                        try {
                            // Convierte el Blob a un Image
                            Image image = new Image(item.getBinaryStream());
                            imageView.setImage(image);
                        } catch (SQLException e) {
                            e.printStackTrace();
                            // En caso de error, establecer la imagen por defecto
                            imageView.setImage(imagenPorDefecto);
                        }
                    }

                    // Ajusta el tamaño de la imagen y mantiene su proporción
                    imageView.setFitWidth(50); // Establece el ancho de la imagen
                    imageView.setFitHeight(50); // Establece la altura de la imagen
                    imageView.setPreserveRatio(true); // Mantiene la proporción de la imagen

                    // Establecer la gráfica de la celda
                    setGraphic(imageView); // Muestra la imagen en la celda
                }
            }
        });

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdDeportista, colNombre, colSexo, colPeso, colAltura, colFoto);

        // Cargar los datos en la tabla
            ObservableList<Deportista> deportistas = DeportistaDAO.findAll();
        tabla.setItems(deportistas);
    }

    @FXML
    void generarEquipos() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Equipo
        TableColumn<Equipo, Integer> colIdEquipo = new TableColumn<>("ID Equipo");
        colIdEquipo.setCellValueFactory(new PropertyValueFactory<>("idEquipo"));

        TableColumn<Equipo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Equipo, String> colIniciales = new TableColumn<>("Iniciales");
        colIniciales.setCellValueFactory(new PropertyValueFactory<>("iniciales"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdEquipo, colNombre, colIniciales);

        // Cargar los datos en la tabla
        ObservableList<Equipo> equipos = EquipoDAO.findAll();
        tabla.setItems(equipos);
    }

    @FXML
    void generarEventos() {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Evento
        TableColumn<Evento, Integer> colIdEvento = new TableColumn<>("ID Evento");
        colIdEvento.setCellValueFactory(new PropertyValueFactory<>("idEvento"));

        TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Evento, String> colOlimpiada = new TableColumn<>("Olimpiada");
        colOlimpiada.setCellValueFactory(cellData -> {
            Olimpiada olimpiada = cellData.getValue().getOlimpiada();
            return new SimpleStringProperty(olimpiada != null ? olimpiada.getNombre() : "");
        });

        TableColumn<Evento, String> colDeporte = new TableColumn<>("Deporte");
        colDeporte.setCellValueFactory(cellData -> {
            Deporte deporte = cellData.getValue().getDeporte();
            return new SimpleStringProperty(deporte != null ? deporte.getNombre() : "");
        });

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdEvento, colNombre, colOlimpiada, colDeporte);

        // Cargar los datos en la tabla
            ObservableList<Evento> eventos = EventoDAO.getAll();

        tabla.setItems(eventos);
    }

    @FXML
    void generarOlimpiadas()  {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Olimpiada
        TableColumn<Olimpiada, Integer> colIdOlimpiada = new TableColumn<>("ID Olimpiada");
        colIdOlimpiada.setCellValueFactory(new PropertyValueFactory<>("idOlimpiada"));

        TableColumn<Olimpiada, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<Olimpiada, Integer> colAnio = new TableColumn<>("Año");
        colAnio.setCellValueFactory(new PropertyValueFactory<>("anio"));

        TableColumn<Olimpiada, String> colTemporada = new TableColumn<>("Temporada");
        colTemporada.setCellValueFactory(new PropertyValueFactory<>("temporada"));

        TableColumn<Olimpiada, String> colCiudad = new TableColumn<>("Ciudad");
        colCiudad.setCellValueFactory(new PropertyValueFactory<>("ciudad"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colIdOlimpiada, colNombre, colAnio, colTemporada, colCiudad);

        // Cargar los datos en la tabla
            ObservableList<Olimpiada> olimpiadas = OlimpiadaDAO.getAll();

        tabla.setItems(olimpiadas);
    }

    @FXML
    void generarParticipaciones()  {
        // Limpiar columnas y filtro
        tfNombre.setText(null);
        tabla.getColumns().clear();

        // Crear columnas para la tabla Participacion
        TableColumn<Participacion, String> colDeportista = new TableColumn<>("Deportista");
        colDeportista.setCellValueFactory(cellData -> {
            Deportista deportista = cellData.getValue().getDeportista();
            return new SimpleStringProperty(deportista != null ? deportista.getNombre() : "");
        });

        TableColumn<Participacion, String> colEvento = new TableColumn<>("Evento");
        colEvento.setCellValueFactory(cellData -> {
            Evento evento = cellData.getValue().getEvento();
            return new SimpleStringProperty(evento != null ? evento.getNombre() : "");
        });

        TableColumn<Participacion, String> colEquipo = new TableColumn<>("Equipo");
        colEquipo.setCellValueFactory(cellData -> {
            Equipo equipo = cellData.getValue().getEquipo();
            return new SimpleStringProperty(equipo != null ? equipo.getNombre() : "");
        });

        TableColumn<Participacion, Integer> colEdad = new TableColumn<>("Edad");
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));

        TableColumn<Participacion, String> colMedalla = new TableColumn<>("Medalla");
        colMedalla.setCellValueFactory(new PropertyValueFactory<>("medalla"));

        // Agregar columnas a la tabla
        tabla.getColumns().addAll(colDeportista, colEvento, colEquipo, colEdad, colMedalla);

        // Cargar los datos en la tabla
        ObservableList<Participacion> participaciones = ParticipacionDAO.findAll();
        tabla.setItems(participaciones);
    }

}
