package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Olimpiada;
import com.example.olimpiadas.BBDD.ConexionBBDD;

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

    public Olimpiada getById(int idOlimpiada) throws SQLException {
        String sql = "SELECT * FROM Olimpiada WHERE id_olimpiada = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idOlimpiada);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Olimpiada(
                    rs.getInt("id_olimpiada"),
                    rs.getString("nombre"),
                    rs.getInt("anio"),
                    rs.getString("temporada"),
                    rs.getString("ciudad")
                );
            }
        }
        return null;
    }

    public List<Olimpiada> getAll() throws SQLException {
        List<Olimpiada> olimpiadas = new ArrayList<>();
        String sql = "SELECT * FROM Olimpiada";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                olimpiadas.add(new Olimpiada(
                    rs.getInt("id_olimpiada"),
                    rs.getString("nombre"),
                    rs.getInt("anio"),
                    rs.getString("temporada"),
                    rs.getString("ciudad")
                ));
            }
        }
        return olimpiadas;
    }
}
