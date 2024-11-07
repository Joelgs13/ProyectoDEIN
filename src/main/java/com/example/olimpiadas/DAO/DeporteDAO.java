package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DeporteDAO {

    /**
     * Agrega un nuevo deporte a la base de datos.
     *
     * @param d El objeto {@link Deporte} que se desea agregar a la base de datos.
     * @return {@code true} si el deporte se agregó exitosamente, {@code false} en caso contrario.
     */
    public static boolean addDeporte(Deporte d) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("INSERT INTO Deporte (nombre) VALUES (?)");
            pstmt.setString(1, d.getNombre());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Actualiza un deporte existente en la base de datos.
     *
     * @param d El objeto {@link Deporte} con los nuevos valores que se desean actualizar en la base de datos.
     * @return {@code true} si el deporte se actualizó exitosamente, {@code false} en caso contrario.
     */
    public static boolean updateDeporte(Deporte d) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("UPDATE Deporte SET nombre = ? WHERE id_deporte = ?");
            pstmt.setString(1, d.getNombre());
            pstmt.setInt(2, d.getIdDeporte());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
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
     * Elimina un deporte de la base de datos si no está asociado a ningún evento.
     *
     * @param idDeporte El id del deporte a eliminar.
     * @return {@code true} si el deporte fue eliminado exitosamente, {@code false} si no se puede eliminar (por estar asociado a un evento).
     */
    public static boolean deleteDeporte(int idDeporte) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión
            connection = new ConexionBBDD();

            // Comprobar si el deporte está asociado a algún evento (similar al código original)
            String consulta = "SELECT count(*) as cont FROM Evento WHERE id_deporte = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, idDeporte);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();

                if (cont > 0) {
                    // Si hay eventos asociados, no se puede eliminar el deporte
                    return false;
                }
            }

            // Eliminar el deporte de la base de datos si no hay eventos asociados
            String deleteQuery = "DELETE FROM Deporte WHERE id_deporte = ?";
            pstmt = connection.getConnection().prepareStatement(deleteQuery);
            pstmt.setInt(1, idDeporte);

            // Ejecutar la eliminación
            return pstmt.executeUpdate() > 0;  // Retorna true si la eliminación fue exitosa

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
     * Obtiene un deporte por su id desde la base de datos.
     *
     * @param id El id del deporte que se desea obtener.
     * @return Un objeto {@link Deporte} con los datos correspondientes, o {@code null} si no se encuentra el deporte.
     */
    public static Deporte getById(int id) {
        ConexionBBDD connection;
        Deporte deporte = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deporte,nombre FROM Deporte WHERE id_deporte = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_deporte = rs.getInt(1);
                String nombre = rs.getString(2);
                deporte = new Deporte(id_deporte,nombre);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deporte;
    }

    /**
     * Obtiene todos los deportes registrados en la base de datos.
     *
     * @return Una lista observable de objetos {@link Deporte} que representan todos los deportes.
     */
    public static ObservableList<Deporte> findAll() {
        ConexionBBDD connection;
        ObservableList<Deporte> deportes = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deporte, nombre FROM Deporte";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deporte = rs.getInt(1);
                String nombre = rs.getString(2);
                Deporte deporte = new Deporte(id_deporte, nombre);
                deportes.add(deporte);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportes;
    }
}
