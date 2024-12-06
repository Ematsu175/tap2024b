package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumCancionDAO {
    private int id_album;
    private String album;
    private int id_cancion;
    private String cancion;
    private int originalIdAlbum;
    private int originalIdCancion;

    public int getOriginalIdAlbum() {
        return originalIdAlbum;
    }

    public void setOriginalIdAlbum(int originalIdAlbum) {
        this.originalIdAlbum = originalIdAlbum;
    }

    public int getOriginalIdCancion() {
        return originalIdCancion;
    }

    public void setOriginalIdCancion(int originalIdCancion) {
        this.originalIdCancion = originalIdCancion;
    }

    public int getId_album() {
        return id_album;
    }

    public void setId_album(int id_album) {
        this.id_album = id_album;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
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

    public int insert() {
        int rowCount = 0;
        String query = "INSERT INTO album_cancion (id_album, id_cancion) VALUES (?, ?)";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_album); // ID del álbum
            stmt.setInt(2, this.id_cancion); // ID de la canción

            System.out.println("Insertando relación: id_album=" + this.id_album + ", id_cancion=" + this.id_cancion);
            rowCount = stmt.executeUpdate();
            System.out.println("Filas afectadas (insert): " + rowCount);
        } catch (Exception e) {
            System.err.println("Error al insertar relación:");
            e.printStackTrace();
        }

        return rowCount;
    }


    public void update() {
        String query = "UPDATE album_cancion SET id_album = ?, id_cancion = ? WHERE id_album = ? AND id_cancion = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_album); // Nuevo ID del álbum
            stmt.setInt(2, this.id_cancion); // Nuevo ID de la canción
            stmt.setInt(3, this.originalIdAlbum); // ID original del álbum
            stmt.setInt(4, this.originalIdCancion); // ID original de la canción

            System.out.println("Actualizando relación: id_album=" + this.originalIdAlbum + ", id_cancion=" + this.originalIdCancion +
                    " -> Nuevo id_album=" + this.id_album + ", Nuevo id_cancion=" + this.id_cancion);

            int rowCount = stmt.executeUpdate();
            System.out.println("Filas afectadas (update): " + rowCount);
        } catch (Exception e) {
            System.err.println("Error al actualizar relación:");
            e.printStackTrace();
        }
    }



    public void delete() {
        String query = "DELETE FROM album_cancion WHERE id_album = ? AND id_cancion = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_album);
            stmt.setInt(2, this.id_cancion);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<AlbumCancionDAO> selectAll() {
        ObservableList<AlbumCancionDAO> lista = FXCollections.observableArrayList();
        String query = "SELECT ac.id_album, a.album, ac.id_cancion, c.cancion " +
                "FROM album_cancion ac " +
                "JOIN album a ON ac.id_album = a.id_album " +
                "JOIN cancion c ON ac.id_cancion = c.id_cancion";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                AlbumCancionDAO obj = new AlbumCancionDAO();
                obj.setId_album(res.getInt("id_album"));
                obj.setAlbum(res.getString("album"));
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
