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

    public static boolean addEvento(Evento evento) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("INSERT INTO Evento (nombre, id_olimpiada, id_deporte) VALUES (?, ?, ?)");
            pstmt.setString(1, evento.getNombre());
            pstmt.setInt(2, evento.getOlimpiada().getIdOlimpiada());
            pstmt.setInt(3, evento.getDeporte().getIdDeporte());

            return pstmt.executeUpdate() > 0;  // Retorna true si se insertó correctamente

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


    public static boolean updateEvento(Evento evento) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = new ConexionBBDD();
            pstmt = connection.getConnection().prepareStatement("UPDATE Evento SET nombre = ?, id_olimpiada = ?, id_deporte = ? WHERE id_evento = ?");
            pstmt.setString(1, evento.getNombre());
            pstmt.setInt(2, evento.getOlimpiada().getIdOlimpiada());
            pstmt.setInt(3, evento.getDeporte().getIdDeporte());
            pstmt.setInt(4, evento.getIdEvento());

            return pstmt.executeUpdate() > 0;  // Retorna true si se actualizó correctamente

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


    public static boolean deleteEvento(int idEvento) {
        ConexionBBDD connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión
            connection = new ConexionBBDD();

            // Comprobar si el evento está asociado a alguna participación
            String consulta = "SELECT count(*) as cont FROM Participacion WHERE id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, idEvento);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int cont = rs.getInt("cont");
                rs.close();

                if (cont > 0) {
                    // Si el evento está asociado a alguna participación, no se puede eliminar
                    return false;
                }
            }

            // Eliminar el evento de la base de datos si no tiene participaciones asociadas
            String deleteQuery = "DELETE FROM Evento WHERE id_evento = ?";
            pstmt = connection.getConnection().prepareStatement(deleteQuery);
            pstmt.setInt(1, idEvento);

            // Ejecutar la eliminación
            return pstmt.executeUpdate() > 0;  // Retorna true si la eliminación fue exitosa

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
