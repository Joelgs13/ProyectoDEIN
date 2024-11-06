package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.BBDD.ConexionBBDD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeportistaDAO {

    public void insert(Deportista deportista) throws SQLException {
        String sql = "INSERT INTO Deportista (nombre, sexo, peso, altura, foto) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deportista.getNombre());
            stmt.setString(2, String.valueOf(deportista.getSexo()));
            stmt.setObject(3, deportista.getPeso(), Types.INTEGER);
            stmt.setObject(4, deportista.getAltura(), Types.INTEGER);
            stmt.setBlob(5, deportista.getFoto());
            stmt.executeUpdate();
        }
    }

    public void update(Deportista deportista) throws SQLException {
        String sql = "UPDATE Deportista SET nombre = ?, sexo = ?, peso = ?, altura = ?, foto = ? WHERE id_deportista = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, deportista.getNombre());
            stmt.setString(2, String.valueOf(deportista.getSexo()));
            stmt.setObject(3, deportista.getPeso(), Types.INTEGER);
            stmt.setObject(4, deportista.getAltura(), Types.INTEGER);
            stmt.setBlob(5, deportista.getFoto());
            stmt.setInt(6, deportista.getIdDeportista());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Deportista WHERE id_deportista = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public static Deportista getById(int id) throws SQLException {
        ConexionBBDD connection;
        Deportista deportista = null;
        try {
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista,nombre,sexo,peso,altura,foto FROM Deportista WHERE id_deportista = ?";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int id_deportista = rs.getInt(1);
                String nombre = rs.getString(2);
                char sexo = rs.getString(3).charAt(0);
                int peso = rs.getInt(4);
                int altura = rs.getInt(5);
                Blob foto = rs.getBlob(6);
                deportista = new Deportista(id_deportista,nombre,sexo,peso,altura,foto);
            }
            rs.close();
            connection.CloseConexion();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportista;
    }

    public static ObservableList<Deportista> findAll() {
        ConexionBBDD connection;
        ObservableList<Deportista> deportistas = FXCollections.observableArrayList();
        try{
            connection = new ConexionBBDD();
            String consulta = "SELECT id_deportista,nombre,sexo,peso,altura,foto FROM Deportista";
            PreparedStatement pstmt = connection.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id_deportista = rs.getInt(1);
                String nombre = rs.getString(2);
                char sexo = rs.getString(3).charAt(0);
                int peso = rs.getInt(4);
                int altura = rs.getInt(5);
                Blob foto = rs.getBlob(6);
                Deportista deportista = new Deportista(id_deportista,nombre,sexo,peso,altura,foto);
                deportistas.add(deportista);
            }
            rs.close();
            connection.CloseConexion();
        }catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return deportistas;
    }
}
