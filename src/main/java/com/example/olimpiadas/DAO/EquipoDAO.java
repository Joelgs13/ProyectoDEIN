package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * La clase {@code EquipoDAO} proporciona los métodos para interactuar con la base de datos
 * relacionada con los objetos {@link Equipo}. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * para gestionar los equipos en la base de datos.
 */
public class EquipoDAO {

    /**
     * Agrega un nuevo equipo a la base de datos.
     *
     * @param equipo El objeto {@link Equipo} que se desea agregar a la base de datos.
     * @return {@code true} si el equipo fue agregado exitosamente, {@code false} en caso contrario.
     */
    public static boolean addEquipo(Equipo equipo) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("INSERT INTO Equipo (nombre, iniciales) VALUES (?, ?)");
            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getIniciales());

            return pstmt.executeUpdate() > 0; // Retorna true si se insertó correctamente

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
     * Actualiza los datos de un equipo en la base de datos.
     *
     * @param equipo El objeto {@link Equipo} con los nuevos valores que se desean actualizar.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public static boolean updateEquipo(Equipo equipo) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("UPDATE Equipo SET nombre = ?, iniciales = ? WHERE id_equipo = ?");
            pstmt.setString(1, equipo.getNombre());
            pstmt.setString(2, equipo.getIniciales());
            pstmt.setInt(3, equipo.getIdEquipo());

            return pstmt.executeUpdate() > 0; // Retorna true si se actualizó correctamente

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
     * Elimina un equipo de la base de datos si no está asociado a ninguna participación.
     *
     * @param idEquipo El id del equipo que se desea eliminar.
     * @return {@code true} si el equipo fue eliminado exitosamente, {@code false} si está asociado a alguna participación.
     */
    public static boolean deleteEquipo(int idEquipo) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión
            connection = new ConexionBBDD();

            // Comprobar si el equipo está asociado a alguna participación
            String consulta = "SELECT count(*) as cont FROM Participacion WHERE id_equipo = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, idEquipo);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();

                if (cont > 0) {
                    // Si el equipo está asociado a alguna participación, no se puede eliminar
                    return false;
                }
            }

            // Eliminar el equipo de la base de datos si no tiene participaciones asociadas
            String deleteQuery = "DELETE FROM Equipo WHERE id_equipo = ?";
            pstmt = connection.getConnection().prepareStatement(deleteQuery);
            pstmt.setInt(1, idEquipo);

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
     * Obtiene un equipo por su id desde la base de datos.
     *
     * @param id El id del equipo que se desea obtener.
     * @return Un objeto {@link Equipo} con los datos correspondientes, o {@code null} si no se encuentra el equipo.
     */
    public static Equipo getById(int id) {
        ConexionBBDD connection;
        Equipo equipo = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_equipo,nombre,iniciales FROM Equipo WHERE id_equipo = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_equipo = rs.getInt(1);
                String nombre = rs.getString(2);
                String iniciales = rs.getString(3);
                equipo = new Equipo(id_equipo, nombre, iniciales);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipo;
    }

    /**
     * Obtiene todos los equipos registrados en la base de datos.
     *
     * @return Una lista observable de objetos {@link Equipo} que representan todos los equipos.
     */
    public static ObservableList<Equipo> findAll() {
        ConexionBBDD connection;
        ObservableList<Equipo> equipos = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_equipo, nombre, iniciales FROM Equipo";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_equipo = rs.getInt(1);
                String nombre = rs.getString(2);
                String iniciales = rs.getString(3);
                Equipo equipo = new Equipo(id_equipo, nombre, iniciales);
                equipos.add(equipo);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipos;
    }
}
