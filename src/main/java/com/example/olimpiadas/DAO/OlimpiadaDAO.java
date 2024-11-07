package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Olimpiada;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

/**
 * La clase {@code OlimpiadaDAO} proporciona los métodos para interactuar con la base de datos
 * relacionada con los objetos {@link Olimpiada}. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * para gestionar las olimpiadas en la base de datos.
 */
public class OlimpiadaDAO {

    /**
     * Agrega una nueva olimpiada a la base de datos.
     *
     * @param olimpiada El objeto {@link Olimpiada} que se desea agregar a la base de datos.
     * @return {@code true} si la olimpiada fue agregada exitosamente, {@code false} en caso contrario.
     */
    public static boolean addOlimpiada(Olimpiada olimpiada) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("INSERT INTO Olimpiada (nombre, anio, temporada, ciudad) VALUES (?, ?, ?, ?)");
            pstmt.setString(1, olimpiada.getNombre());
            pstmt.setInt(2, olimpiada.getAnio());
            pstmt.setString(3, olimpiada.getTemporada());  // El tipo Temporada ya es un String
            pstmt.setString(4, olimpiada.getCiudad());

            return pstmt.executeUpdate() > 0; // Retorna true si la inserción fue exitosa

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
     * Actualiza los datos de una olimpiada en la base de datos.
     *
     * @param olimpiada El objeto {@link Olimpiada} con los nuevos valores que se desean actualizar.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public static boolean updateOlimpiada(Olimpiada olimpiada) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("UPDATE Olimpiada SET nombre = ?, anio = ?, temporada = ?, ciudad = ? WHERE id_olimpiada = ?");
            pstmt.setString(1, olimpiada.getNombre());
            pstmt.setInt(2, olimpiada.getAnio());
            pstmt.setString(3, olimpiada.getTemporada());
            pstmt.setString(4, olimpiada.getCiudad());
            pstmt.setInt(5, olimpiada.getIdOlimpiada());

            return pstmt.executeUpdate() > 0;  // Retorna true si la actualización fue exitosa

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
     * Elimina una olimpiada de la base de datos si no está asociada a ningún evento.
     *
     * @param idOlimpiada El id de la olimpiada que se desea eliminar.
     * @return {@code true} si la olimpiada fue eliminada exitosamente, {@code false} si está asociada a algún evento.
     */
    public static boolean deleteOlimpiada(int idOlimpiada) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión
            connection = new ConexionBBDD();

            // Comprobar si la olimpiada está asociada a algún evento
            String consulta = "SELECT count(*) as cont FROM Evento WHERE id_olimpiada = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, idOlimpiada);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();

                if (cont > 0) {
                    // Si la olimpiada está asociada a algún evento, no se puede eliminar
                    return false;
                }
            }

            // Eliminar la olimpiada de la base de datos si no tiene eventos asociados
            String deleteQuery = "DELETE FROM Olimpiada WHERE id_olimpiada = ?";
            pstmt = connection.getConnection().prepareStatement(deleteQuery);
            pstmt.setInt(1, idOlimpiada);

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
     * Obtiene una olimpiada por su id desde la base de datos.
     *
     * @param id El id de la olimpiada que se desea obtener.
     * @return Un objeto {@link Olimpiada} con los datos correspondientes, o {@code null} si no se encuentra la olimpiada.
     */
    public static Olimpiada getById(int id) {
        ConexionBBDD connection;
        Olimpiada olimpiada = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_olimpiada,nombre,anio,temporada,ciudad FROM Olimpiada WHERE id_olimpiada = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_olimpiada = rs.getInt(1);
                String nombre = rs.getString(2);
                int anio = rs.getInt(3);
                String temporada = rs.getString(4);
                String ciudad = rs.getString(5);
                olimpiada = new Olimpiada(id_olimpiada, nombre, anio, temporada, ciudad);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return olimpiada;
    }

    /**
     * Obtiene todas las olimpiadas registradas en la base de datos.
     *
     * @return Una lista observable de objetos {@link Olimpiada} que representan todas las olimpiadas.
     */
    public static ObservableList<Olimpiada> getAll()  {
        ConexionBBDD connection;
        ObservableList<Olimpiada> olimpiadas = FXCollections.observableArrayList();
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_olimpiada, nombre, anio, temporada, ciudad FROM Olimpiada";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_olimpiada = rs.getInt(1);
                String nombre = rs.getString(2);
                int anio = rs.getInt(3);
                String temporada = rs.getString(4);
                String ciudad = rs.getString(5);
                Olimpiada olimpiada = new Olimpiada(id_olimpiada, nombre, anio, temporada, ciudad);
                olimpiadas.add(olimpiada);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return olimpiadas;
    }
}
