package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
