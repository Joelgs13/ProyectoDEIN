package com.example.olimpiadas.DAO;

import com.example.olimpiadas.model.Deportista;
import com.example.olimpiadas.BBDD.ConexionBBDD;

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

    public Deportista findById(int id) throws SQLException {
        String sql = "SELECT * FROM Deportista WHERE id_deportista = ?";
        try (Connection connection = ConexionBBDD.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Deportista(rs.getInt("id_deportista"), rs.getString("nombre"), rs.getString("sexo").charAt(0),
                        (Integer) rs.getObject("peso"), (Integer) rs.getObject("altura"), rs.getBlob("foto"));
            }
        }
        return null;
    }

    public List<Deportista> findAll() throws SQLException {
        List<Deportista> deportistas = new ArrayList<>();
        String sql = "SELECT * FROM Deportista";
        try (Connection connection = ConexionBBDD.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                deportistas.add(new Deportista(rs.getInt("id_deportista"), rs.getString("nombre"),
                        rs.getString("sexo").charAt(0), (Integer) rs.getObject("peso"),
                        (Integer) rs.getObject("altura"), rs.getBlob("foto")));
            }
        }
        return deportistas;
    }
}
