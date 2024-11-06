package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deporte;
import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import com.example.olimpiadas.model.Olimpiada;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoDAO {

    public void delete(int idEvento) throws SQLException {
        String sql = "DELETE FROM Evento WHERE id_evento = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEvento);
            stmt.executeUpdate();
        }
    }

    public static Evento getById(int id) throws SQLException {
        ConexionBBDD connection;
        Evento evento = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_evento,nombre,id_olimpiada,id_deporte FROM Evento WHERE id_evento = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_evento = rs.getInt("id_evento");
                String nombre = rs.getString("nombre");
                int id_olimpiada = rs.getInt("id_olimpiada");
                int id_deporte = rs.getInt("id_deporte");

                //Olimpiada
                Olimpiada olimpiada = OlimpiadaDAO.getById(id_olimpiada);
                //Deporte
                Deporte deporte = DeporteDAO.getById(id_deporte);

                evento = new Evento(id_evento,nombre,olimpiada,deporte);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return evento;
    }

    public static ObservableList<Evento> getAll() {
        ConexionBBDD connection;
        ObservableList<Evento> eventos = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_evento,nombre,id_olimpiada,id_deporte FROM Evento";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_evento = rs.getInt(1);
                String nombre = rs.getString(2);
                int id_olimpiada = rs.getInt(3);
                int id_deporte = rs.getInt(4);

                //Olimpiada
                Olimpiada olimpiada = OlimpiadaDAO.getById(id_olimpiada);
                //Deporte
                Deporte deporte = DeporteDAO.getById(id_deporte);

                Evento evento = new Evento(id_evento,nombre,olimpiada,deporte);
                eventos.add(evento);
            }
            rs.close();
            connection.CloseConexion();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return eventos;
    }
}
