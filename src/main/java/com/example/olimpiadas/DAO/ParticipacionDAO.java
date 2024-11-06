package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.model.Equipo;
import com.example.olimpiadas.model.Evento;
import com.example.olimpiadas.model.Participacion;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipacionDAO {


    public static ObservableList<Participacion> findAll() {
        ConexionBBDD connection;
        ObservableList<Participacion> participacions = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista,id_evento,id_equipo,edad,medalla FROM Participacion";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deportista = rs.getInt(1);
                int id_evento = rs.getInt(2);
                int id_equipo = rs.getInt(3);
                int edad = rs.getInt(4);
                String medalla = rs.getString(5);

                //Deportista
                Deportista deportista = DeportistaDAO.getById(id_deportista);
                //Evento
                Evento evento = EventoDAO.getById(id_evento);
                //Equipo
                Equipo equipo = EquipoDAO.getById(id_equipo);

                Participacion participacion = new Participacion(deportista,evento,equipo,edad,medalla);
                participacions.add(participacion);
            }
            rs.close();
            connection.CloseConexion();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return participacions;
    }
}
