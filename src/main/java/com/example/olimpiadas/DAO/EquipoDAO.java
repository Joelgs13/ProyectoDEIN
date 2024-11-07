package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

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



    public static Equipo getById(int id)  {
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
                equipo = new Equipo(id_equipo,nombre,iniciales);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return equipo;
    }

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
