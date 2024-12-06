package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class InterpreteDao {
    private int id_artista;
    private String artista;
    private int id_cancion;
    private String cancion;
    private int originalIdArtista;
    private int originalIdCancion;

    public int getId_artista() {
        return id_artista;
    }

    public void setId_artista(int id_artista) {
        this.id_artista = id_artista;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public int getId_cancion() {
        return id_cancion;
    }

    public void setId_cancion(int id_cancion) {
        this.id_cancion = id_cancion;
    }

    public String getCancion() {
        return cancion;
    }

    public void setCancion(String cancion) {
        this.cancion = cancion;
    }

    public int getOriginalIdArtista() {
        return originalIdArtista;
    }

    public void setOriginalIdArtista(int originalIdArtista) {
        this.originalIdArtista = originalIdArtista;
    }

    public int getOriginalIdCancion() {
        return originalIdCancion;
    }

    public void setOriginalIdCancion(int originalIdCancion) {
        this.originalIdCancion = originalIdCancion;
    }

    public int insert() {
        int rowCount = 0;
        String query = "INSERT INTO interpretacion (id_artista, id_cancion) VALUES (?, ?)";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_artista); // ID del álbum
            stmt.setInt(2, this.id_cancion); // ID de la canción

            System.out.println("Insertando relación: id_artista=" + this.id_artista + ", id_cancion=" + this.id_cancion);
            rowCount = stmt.executeUpdate();
            System.out.println("Filas afectadas (insert): " + rowCount);
        } catch (Exception e) {
            System.err.println("Error al insertar relación:");
            e.printStackTrace();
        }

        return rowCount;
    }


    public void update() {
        String query = "UPDATE interpretacion SET id_artista = ?, id_cancion = ? WHERE id_artista = ? AND id_cancion = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_artista); // Nuevo ID del álbum
            stmt.setInt(2, this.id_cancion); // Nuevo ID de la canción
            stmt.setInt(3, this.originalIdArtista); // ID original del álbum
            stmt.setInt(4, this.originalIdCancion); // ID original de la canción

            System.out.println("Actualizando relación: id_album=" + this.id_artista + ", id_cancion=" + this.originalIdCancion +
                    " -> Nuevo id_album=" + this.id_artista + ", Nuevo id_cancion=" + this.id_cancion);

            int rowCount = stmt.executeUpdate();
            System.out.println("Filas afectadas (update): " + rowCount);
        } catch (Exception e) {
            System.err.println("Error al actualizar relación:");
            e.printStackTrace();
        }
    }



    public void delete() {
        String query = "DELETE FROM interpretacion WHERE id_artista = ? AND id_cancion = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_artista);
            stmt.setInt(2, this.id_cancion);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<InterpreteDao> selectAll() {
        ObservableList<InterpreteDao> lista = FXCollections.observableArrayList();
        String query = "SELECT ac.id_artista, a.artista, ac.id_cancion, c.cancion " +
                "FROM interpretacion ac " +
                "JOIN artista a ON ac.id_artista = a.id_artista " +
                "JOIN cancion c ON ac.id_cancion = c.id_cancion";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                InterpreteDao obj = new InterpreteDao();
                obj.setId_artista(res.getInt("id_artista"));
                obj.setArtista(res.getString("artista"));
                obj.setId_cancion(res.getInt("id_cancion"));
                obj.setCancion(res.getString("cancion"));
                lista.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}
