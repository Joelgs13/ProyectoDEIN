package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.BBDD.ConexionBBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipoDAO {

    public void insert(Equipo equipo) throws SQLException {
        String sql = "INSERT INTO Equipo (nombre, iniciales) VALUES (?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getIniciales());
            stmt.executeUpdate();
        }
    }

    public void update(Equipo equipo) throws SQLException {
        String sql = "UPDATE Equipo SET nombre = ?, iniciales = ? WHERE id_equipo = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipo.getNombre());
            stmt.setString(2, equipo.getIniciales());
            stmt.setInt(3, equipo.getIdEquipo());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Equipo WHERE id_equipo = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public Equipo findById(int id) throws SQLException {
        String sql = "SELECT * FROM Equipo WHERE id_equipo = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"), rs.getString("iniciales"));
            }
        }
        return null;
    }

    public List<Equipo> findAll() throws SQLException {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM Equipo";
        try (Connection connection = ConexionBBDD.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                equipos.add(new Equipo(rs.getInt("id_equipo"), rs.getString("nombre"), rs.getString("iniciales")));
            }
        }
        return equipos;
    }
}
