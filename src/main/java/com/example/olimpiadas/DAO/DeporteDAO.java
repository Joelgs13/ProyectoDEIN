package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.BBDD.ConexionBBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Deporte> findAll() throws SQLException {
        List<Deporte> deportes = new ArrayList<>();
        String sql = "SELECT * FROM Deporte";
        try (Connection connection = ConexionBBDD.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                deportes.add(new Deporte(rs.getInt("id_deporte"), rs.getString("nombre")));
            }
        }
        return deportes;
    }
}
