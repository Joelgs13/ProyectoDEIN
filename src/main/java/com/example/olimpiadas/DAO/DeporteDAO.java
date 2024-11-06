package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DeporteDAO {

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


    public static boolean deleteDeporte(int id) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("DELETE FROM Deporte WHERE id_deporte = ?");
            pstmt.setInt(1, id);

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
