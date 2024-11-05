package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Participacion;
import com.example.olimpiadas.BBDD.ConexionBBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipacionDAO {

    public void insert(Participacion participacion) throws SQLException {
        String sql = "INSERT INTO Participacion (id_deportista, id_evento, id_equipo, edad, medalla) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, participacion.getIdDeportista());
            stmt.setInt(2, participacion.getIdEvento());
            stmt.setInt(3, participacion.getIdEquipo());
            stmt.setInt(4, participacion.getEdad());
            stmt.setString(5, participacion.getMedalla());
            stmt.executeUpdate();
        }
    }

    public void update(Participacion participacion) throws SQLException {
        String sql = "UPDATE Participacion SET id_evento = ?, id_equipo = ?, edad = ?, medalla = ? WHERE id_deportista = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, participacion.getIdEvento());
            stmt.setInt(2, participacion.getIdEquipo());
            stmt.setInt(3, participacion.getEdad());
            stmt.setString(4, participacion.getMedalla());
            stmt.setInt(5, participacion.getIdDeportista());
            stmt.executeUpdate();
        }
    }

    public void delete(int idDeportista, int idEvento) throws SQLException {
        String sql = "DELETE FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDeportista);
            stmt.setInt(2, idEvento);
            stmt.executeUpdate();
        }
    }

    public Participacion getById(int idDeportista, int idEvento) throws SQLException {
        String sql = "SELECT * FROM Participacion WHERE id_deportista = ? AND id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDeportista);
            stmt.setInt(2, idEvento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Participacion(
                    rs.getInt("id_deportista"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_equipo"),
                    rs.getInt("edad"),
                    rs.getString("medalla")
                );
            }
        }
        return null;
    }

    public List<Participacion> getAll() throws SQLException {
        List<Participacion> participaciones = new ArrayList<>();
        String sql = "SELECT * FROM Participacion";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                participaciones.add(new Participacion(
                    rs.getInt("id_deportista"),
                    rs.getInt("id_evento"),
                    rs.getInt("id_equipo"),
                    rs.getInt("edad"),
                    rs.getString("medalla")
                ));
            }
        }
        return participaciones;
    }
}
