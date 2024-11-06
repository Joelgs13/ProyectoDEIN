package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Olimpiada;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OlimpiadaDAO {

    public void insert(Olimpiada olimpiada) throws SQLException {
        String sql = "INSERT INTO Olimpiada (nombre, anio, temporada, ciudad) VALUES (?, ?, ?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, olimpiada.getNombre());
            stmt.setInt(2, olimpiada.getAnio());
            stmt.setString(3, olimpiada.getTemporada());
            stmt.setString(4, olimpiada.getCiudad());
            stmt.executeUpdate();
        }
    }

    public void update(Olimpiada olimpiada) throws SQLException {
        String sql = "UPDATE Olimpiada SET nombre = ?, anio = ?, temporada = ?, ciudad = ? WHERE id_olimpiada = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, olimpiada.getNombre());
            stmt.setInt(2, olimpiada.getAnio());
            stmt.setString(3, olimpiada.getTemporada());
            stmt.setString(4, olimpiada.getCiudad());
            stmt.setInt(5, olimpiada.getIdOlimpiada());
            stmt.executeUpdate();
        }
    }

    public void delete(int idOlimpiada) throws SQLException {
        String sql = "DELETE FROM Olimpiada WHERE id_olimpiada = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOlimpiada);
            stmt.executeUpdate();
        }
    }

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
                olimpiada = new Olimpiada(id_olimpiada,nombre,anio,temporada,ciudad);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return olimpiada;
    }

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
