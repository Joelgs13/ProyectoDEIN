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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Optional;

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
        configurarDobleClicTabla();
        configurarEscapeTecla();
    }


    private void configurarDobleClicTabla() {
        tabla.setRowFactory(tv -> {
            TableRow<Object> fila = new TableRow<>();
            fila.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !fila.isEmpty()) {
                    Object objetoSeleccionado = fila.getItem();
                    manejarDobleClic(objetoSeleccionado);
                }
            });
            return fila;
        });
    }

    private void manejarDobleClic(Object objeto) {
        if (objeto instanceof Deporte) {
            editarDeporte(null);
        } else if (objeto instanceof Deportista) {
            editarDeportista(null);
        } else if (objeto instanceof Evento) {
            editarEvento(null);
        } else if (objeto instanceof Equipo) {
            editarEquipo(null);
        } else if (objeto instanceof Olimpiada) {
            editarOlimpiada(null);
        } else if (objeto instanceof Participacion) {
            editarParticipacion(null);
        } else {
            showError("Tipo de objeto no soportado para edición.");
        }
    }

    private void configurarEscapeTecla() {
        tabla.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE && tabla.getSelectionModel().getSelectedItem() != null) {
                Object objetoSeleccionado = tabla.getSelectionModel().getSelectedItem();
                manejarEliminar(objetoSeleccionado);
            }
        });
    }

    // Método para manejar la eliminación del objeto seleccionado
    private void manejarEliminar(Object objeto) {
        if (objeto instanceof Deporte) {
            borrarDeporte(null);
        } else if (objeto instanceof Deportista) {
            borrarDeportista(null);
        } else if (objeto instanceof Evento) {
            borrarEvento(null);
        } else if (objeto instanceof Equipo) {
            borrarEquipo(null);
        } else if (objeto instanceof Olimpiada) {
            borrarOlimpiada(null);
        } else if (objeto instanceof Participacion) {
            borrarParticipacion(null);
        } else {
            showError("Tipo de objeto no soportado para eliminación.");
        }
    }

    private void showError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
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
        try {
            // Cargar el FXML de la ventana modal para Deportista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deportista.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            DeportistaController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Deportista");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void aniadirEquipo(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Equipo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/equipo.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            EquipoController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Equipo");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void aniadirEvento(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Evento
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/evento.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            EventoController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Evento");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void aniadirOlimpiada(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Olimpiada
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/olimpiada.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            OlimpiadaController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Olimpiada");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void aniadirParticipacion(ActionEvent event) {
        try {
            // Cargar el FXML de la ventana modal para Participación
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/participacion.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana modal
            ParticipacionController controller = loader.getController();

            // Pasar el stage de la ventana principal al controlador modal
            Stage stage = new Stage();
            controller.setStage(stage);

            // Crear y mostrar la escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Agregar Participación");
            stage.showAndWait();

            // Actualizar la tabla o cualquier otra vista después de la acción
            cambiarDeTabla(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void borrarDeporte(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            Deporte deporteSeleccionado = (Deporte) tabla.getSelectionModel().getSelectedItem();

            if (deporteSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este deporte?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = DeporteDAO.deleteDeporte(deporteSeleccionado.getIdDeporte());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El deporte ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el deporte.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un deporte para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto deporte");
            warningAlert.showAndWait();
        }
    }

    @FXML
    void borrarDeportista(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) { // Verificar que la selección sea "Deportistas"
            Deportista deportistaSeleccionado = (Deportista) tabla.getSelectionModel().getSelectedItem();

            if (deportistaSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este deportista?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = DeportistaDAO.deleteDeportista(deportistaSeleccionado.getIdDeportista());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El deportista ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el deportista.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un deportista para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto deportista.");
            warningAlert.showAndWait();
        }
    }


    @FXML
    void borrarEquipo(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            Equipo equipoSeleccionado = (Equipo) tabla.getSelectionModel().getSelectedItem();

            if (equipoSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este equipo?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = EquipoDAO.deleteEquipo(equipoSeleccionado.getIdEquipo());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El equipo ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el equipo.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un equipo para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto equipo.");
            warningAlert.showAndWait();
        }
    }


    @FXML
    void borrarEvento(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            Evento eventoSeleccionado = (Evento) tabla.getSelectionModel().getSelectedItem();

            if (eventoSeleccionado != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar este evento?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = EventoDAO.deleteEvento(eventoSeleccionado.getIdEvento());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("El evento ha sido eliminado.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar el evento.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona un evento para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto evento.");
            warningAlert.showAndWait();
        }
    }


    @FXML
    void borrarOlimpiada(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Olimpiadas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            Olimpiada olimpiadaSeleccionada = (Olimpiada) tabla.getSelectionModel().getSelectedItem();

            if (olimpiadaSeleccionada != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar esta olimpiada?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    // Eliminar la olimpiada utilizando el DAO
                    boolean exito = OlimpiadaDAO.deleteOlimpiada(olimpiadaSeleccionada.getIdOlimpiada());
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La olimpiada ha sido eliminada.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar la olimpiada.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una olimpiada
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona una olimpiada para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            // Mostrar mensaje si la tabla seleccionada no es de Olimpiadas
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona una olimpiada.");
            warningAlert.showAndWait();
        }
    }


    @FXML
    void borrarParticipacion(ActionEvent event) {
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Participaciones")) {
            Participacion participacionSeleccionada = (Participacion) tabla.getSelectionModel().getSelectedItem();

            if (participacionSeleccionada != null) {
                // Confirmar la eliminación
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar Eliminación");
                alert.setHeaderText(null);
                alert.setContentText("¿Estás seguro de que quieres eliminar esta participación?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    boolean exito = ParticipacionDAO.deleteParticipacion(
                            participacionSeleccionada.getDeportista().getIdDeportista(),
                            participacionSeleccionada.getEvento().getIdEvento()
                    );
                    if (exito) {
                        // Actualizar la tabla después de eliminar
                        cambiarDeTabla(null);
                        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                        successAlert.setTitle("Éxito");
                        successAlert.setHeaderText(null);
                        successAlert.setContentText("La participación ha sido eliminada.");
                        successAlert.showAndWait();
                    } else {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setTitle("Error");
                        errorAlert.setHeaderText(null);
                        errorAlert.setContentText("No se pudo eliminar la participación.");
                        errorAlert.showAndWait();
                    }
                }
            } else {
                Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                warningAlert.setTitle("Advertencia");
                warningAlert.setHeaderText(null);
                warningAlert.setContentText("Por favor, selecciona una participación para eliminar.");
                warningAlert.showAndWait();
            }
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Advertencia");
            warningAlert.setHeaderText(null);
            warningAlert.setContentText("Por favor, selecciona un objeto participación.");
            warningAlert.showAndWait();
        }
    }


    @FXML
    void editarDeporte(ActionEvent event) {
        // Obtener el deporte seleccionado de la tabla
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            Deporte deporteSeleccionado = (Deporte) tabla.getSelectionModel().getSelectedItem();

            if (deporteSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deporte.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    DeporteController controller = loader.getController();

                    // Pasar el deporte seleccionado al controlador para editar
                    controller.setDeporte(deporteSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Deporte");
                    stage.showAndWait();
                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un deporte
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un deporte para editar.");
                alert.showAndWait();
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un deporte.");
            alert.showAndWait();
        }

    }


    @FXML
    void editarDeportista(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Deportistas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            Deportista deportistaSeleccionado = (Deportista) tabla.getSelectionModel().getSelectedItem();

            if (deportistaSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/deportista.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    DeportistaController controller = loader.getController();

                    // Pasar el deportista seleccionado al controlador para editar
                    controller.setDeportista(deportistaSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Deportista");
                    stage.showAndWait();
                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un deportista
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un deportista para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un deportista.");
            alert.showAndWait();
        }
    }


    @FXML
    void editarEquipo(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Equipos
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            Equipo equipoSeleccionado = (Equipo) tabla.getSelectionModel().getSelectedItem();

            if (equipoSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar el equipo
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/equipo.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    EquipoController controller = loader.getController();

                    // Pasar el equipo seleccionado al controlador para editar
                    controller.setEquipo(equipoSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Equipo");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un equipo
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un equipo para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un equipo.");
            alert.showAndWait();
        }
    }


    @FXML
    void editarEvento(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Eventos
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            Evento eventoSeleccionado = (Evento) tabla.getSelectionModel().getSelectedItem();

            if (eventoSeleccionado != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar el evento
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/evento.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    EventoController controller = loader.getController();

                    // Pasar el evento seleccionado al controlador para editar
                    controller.setEvento(eventoSeleccionado);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Evento");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado un evento
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione un evento para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione un evento.");
            alert.showAndWait();
        }
    }


    @FXML
    void editarOlimpiada(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Olimpiadas
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            Olimpiada olimpiadaSeleccionada = (Olimpiada) tabla.getSelectionModel().getSelectedItem();

            if (olimpiadaSeleccionada != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar la olimpiada
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/olimpiada.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    OlimpiadaController controller = loader.getController();

                    // Pasar la olimpiada seleccionada al controlador para editar
                    controller.setOlimpiada(olimpiadaSeleccionada);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Olimpiada");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una olimpiada
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, seleccione una olimpiada para editar.");
                alert.showAndWait();
            }
        } else {
            // Mostrar mensaje si la tabla seleccionada no es de Olimpiadas
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una olimpiada.");
            alert.showAndWait();
        }
    }


    @FXML
    void editarParticipacion(ActionEvent event) {
        // Verificar que la tabla seleccionada es de Participaciones
        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Participaciones")) {
            Participacion participacionSeleccionada = (Participacion) tabla.getSelectionModel().getSelectedItem();

            if (participacionSeleccionada != null) {
                try {
                    // Cargar el FXML de la ventana modal para editar la participación
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/olimpiadas/fxml/participacion.fxml"));
                    Parent root = loader.load();

                    // Obtener el controlador de la ventana modal
                    ParticipacionController controller = loader.getController();

                    // Pasar la participación seleccionada al controlador para editar
                    controller.setParticipacion(participacionSeleccionada);

                    // Pasar el stage de la ventana principal al controlador modal
                    Stage stage = new Stage();
                    controller.setStage(stage);

                    // Crear y mostrar la escena
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Editar Participación");
                    stage.showAndWait();

                    cambiarDeTabla(null); // Actualizar la tabla después de la edición
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                // Mostrar mensaje si no se ha seleccionado una participación
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, selecciona una participación para editar.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Advertencia");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecciona una participación.");
            alert.showAndWait();
        }
    }


    @FXML
    void filtrar(KeyEvent event) {
        String textoBusqueda = tfNombre.getText() != null ? tfNombre.getText().toLowerCase() : ""; // Asegúrate de que el texto no sea null

        if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deportistas")) {
            ObservableList<Deportista> todosDeportistas = DeportistaDAO.findAll();
            tabla.setItems(todosDeportistas.filtered(deportista ->
                    deportista.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Deporte")) {
            ObservableList<Deporte> todosDeportes = DeporteDAO.findAll();
            tabla.setItems(todosDeportes.filtered(deporte ->
                    deporte.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Equipos")) {
            ObservableList<Equipo> todosEquipos = EquipoDAO.findAll();
            tabla.setItems(todosEquipos.filtered(equipo ->
                    equipo.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Olimpiadas")) {
            ObservableList<Olimpiada> todasOlimpiadas = OlimpiadaDAO.getAll();
            tabla.setItems(todasOlimpiadas.filtered(olimpiada ->
                    olimpiada.getNombre().toLowerCase().contains(textoBusqueda)));

        } else if (cbTablaElegida.getSelectionModel().getSelectedItem().equals("Eventos")) {
            ObservableList<Evento> todosEventos = EventoDAO.getAll();
            tabla.setItems(todosEventos.filtered(evento ->
                    evento.getNombre().toLowerCase().contains(textoBusqueda)));

        } else {
            ObservableList<Participacion> todasParticipaciones = ParticipacionDAO.findAll();
            tabla.setItems(todasParticipaciones.filtered(participacion ->
                    participacion.getDeportista().getNombre().toLowerCase().contains(textoBusqueda)));
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
