package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.model.Participacion;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * La clase {@code ParticipacionDAO} proporciona los métodos necesarios para interactuar con la base de datos
 * en relación a las participaciones de los deportistas en eventos. Implementa operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre las participaciones.
 */
public class ParticipacionDAO {

    /**
     * Agrega una nueva participación a la base de datos.
     *
     * @param participacion El objeto {@link Participacion} que se desea agregar a la base de datos.
     * @return {@code true} si la participación fue agregada exitosamente, {@code false} en caso contrario.
     */
    public static boolean addParticipacion(Participacion participacion) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            String consulta = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?, ?, ?, ?, ?)";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, participacion.getDeportista().getIdDeportista());
            pstmt.setInt(2, participacion.getEvento().getIdEvento());
            pstmt.setInt(3, participacion.getEquipo().getIdEquipo());
            pstmt.setInt(4, participacion.getEdad());
            pstmt.setString(5, participacion.getMedalla());

            return pstmt.executeUpdate() > 0;  // Retorna true si se insertó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false en caso de error
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.CloseConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza una participación en la base de datos.
     *
     * @param participacion El objeto {@link Participacion} con los nuevos valores que se desean actualizar.
     * @return {@code true} si la participación fue actualizada exitosamente, {@code false} en caso contrario.
     */
    public static boolean updateParticipacion(Participacion participacion) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            String consulta = "UPDATE Participacion SET id_equipo = ?, edad = ?, medalla = ? WHERE id_deportista = ? AND id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, participacion.getEquipo().getIdEquipo());
            pstmt.setInt(2, participacion.getEdad());
            pstmt.setString(3, participacion.getMedalla());
            pstmt.setInt(4, participacion.getDeportista().getIdDeportista());
            pstmt.setInt(5, participacion.getEvento().getIdEvento());

            return pstmt.executeUpdate() > 0;  // Retorna true si se actualizó correctamente

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false en caso de error
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.CloseConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Elimina una participación de la base de datos, dada la identificación del deportista y el evento.
     *
     * @param idDeportista El id del deportista cuya participación se quiere eliminar.
     * @param idEvento El id del evento cuya participación se quiere eliminar.
     * @return {@code true} si la participación fue eliminada exitosamente, {@code false} en caso contrario.
     */
    public static boolean deleteParticipacion(int idDeportista, int idEvento) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            String consulta = "DELETE FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, idDeportista);
            pstmt.setInt(2, idEvento);

            return pstmt.executeUpdate() > 0;  // Retorna true si se eliminó al menos un registro

        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Retorna false en caso de error
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (connection != null) connection.CloseConexion();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Obtiene todas las participaciones registradas en la base de datos.
     *
     * @return Una lista observable de objetos {@link Participacion} que representan todas las participaciones.
     */
    public static ObservableList<Participacion> findAll() {
        ConexionBBDD connection;
        ObservableList<Participacion> participacions = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista, id_evento, id_equipo, edad, medalla FROM Participacion";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deportista = rs.getInt(1);
                int id_evento = rs.getInt(2);
                int id_equipo = rs.getInt(3);
                int edad = rs.getInt(4);
                String medalla = rs.getString(5);

                // Recupera el objeto Deportista
                Deportista deportista = DeportistaDAO.getById(id_deportista);
                // Recupera el objeto Evento
                Evento evento = EventoDAO.getById(id_evento);
                // Recupera el objeto Equipo
                Equipo equipo = EquipoDAO.getById(id_equipo);

                Participacion participacion = new Participacion(deportista, evento, equipo, edad, medalla);
                participacions.add(participacion);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return participacions;
    }
}
