package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class DeporteDAO {
    
    public void insert(Deporte deporte) throws SQLException {
        String sql = "INSERT INTO Deporte (nombre) VALUES (?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deporte.getNombre());
            stmt.executeUpdate();
        }
    }

    public void update(Deporte deporte) throws SQLException {
        String sql = "UPDATE Deporte SET nombre = ? WHERE id_deporte = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deporte.getNombre());
            stmt.setInt(2, deporte.getIdDeporte());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Deporte WHERE id_deporte = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Deporte findById(int id) throws SQLException {
        String sql = "SELECT * FROM Deporte WHERE id_deporte = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Deporte(rs.getInt("id_deporte"), rs.getString("nombre"));
            }
        }
        return null;
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
