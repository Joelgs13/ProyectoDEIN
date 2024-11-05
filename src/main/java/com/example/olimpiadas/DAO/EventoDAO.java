package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.BBDD.ConexionBBDD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public void insert(Evento evento) throws SQLException {
        String sql = "INSERT INTO Evento (nombre, id_olimpiada, id_deporte) VALUES (?, ?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evento.getNombre());
            stmt.setInt(2, evento.getIdOlimpiada());
            stmt.setInt(3, evento.getIdDeporte());
            stmt.executeUpdate();
        }
    }

    public void update(Evento evento) throws SQLException {
        String sql = "UPDATE Evento SET nombre = ?, id_olimpiada = ?, id_deporte = ? WHERE id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, evento.getNombre());
            stmt.setInt(2, evento.getIdOlimpiada());
            stmt.setInt(3, evento.getIdDeporte());
            stmt.setInt(4, evento.getIdEvento());
            stmt.executeUpdate();
        }
    }

    public void delete(int idEvento) throws SQLException {
        String sql = "DELETE FROM Evento WHERE id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEvento);
            stmt.executeUpdate();
        }
    }

    public Evento getById(int idEvento) throws SQLException {
        String sql = "SELECT * FROM Evento WHERE id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEvento);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Evento(
                    rs.getInt("id_evento"),
                    rs.getString("nombre"),
                    rs.getInt("id_olimpiada"),
                    rs.getInt("id_deporte")
                );
            }
        }
        return null;
    }

    public List<Evento> getAll() throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String sql = "SELECT * FROM Evento";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                eventos.add(new Evento(
                    rs.getInt("id_evento"),
                    rs.getString("nombre"),
                    rs.getInt("id_olimpiada"),
                    rs.getInt("id_deporte")
                ));
            }
        }
        return eventos;
    }
}
