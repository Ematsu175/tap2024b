package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumDAO {
    // Propiedades
    private int id_album;
    private String album;
    private String fecha_lanzamiento;
    private Image img_album;
    private InputStream imageStream;
    private String imagePath;
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public InputStream getImageStream() {
        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {
        this.imageStream = imageStream;
    }

    public int getId_album() { return id_album; }
    public void setId_album(int id_album) { this.id_album = id_album; }
    public String getAlbum() { return album; }
    public void setAlbum(String album) { this.album = album; }
    public String getFecha_lanzamiento() { return fecha_lanzamiento; }
    public void setFecha_lanzamiento(String fecha_lanzamiento) { this.fecha_lanzamiento = fecha_lanzamiento; }
    public Image getImg_album() { return img_album; }
    public void setImg_album(Image img_album) { this.img_album = img_album; }

    // Insertar un álbum con imagen
    public int insert() {
        int rowCount = 0;

        try {
            // Construcción de la consulta SQL
            String query = "INSERT INTO album (album, fecha_lanzamiento, imagen) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
                stmt.setString(1, this.album); // Nombre del álbum
                stmt.setString(2, this.fecha_lanzamiento); // Fecha de lanzamiento

                // Leer los datos de la imagen como bytes
                if (this.imagePath != null) {
                    File file = new File(this.imagePath);
                    FileInputStream fis = new FileInputStream(file);
                    stmt.setBinaryStream(3, fis, (int) file.length()); // Insertar los datos binarios de la imagen
                } else {
                    stmt.setNull(3, java.sql.Types.BLOB); // Si no hay imagen, insertar NULL
                }

                rowCount = stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowCount;
    }

    public int update() {
        int rowCount = 0;

        try {
            // Construcción de la consulta SQL
            String query = "UPDATE album SET album = ?, fecha_lanzamiento = ?, imagen = ? WHERE id_album = ?";

            try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
                stmt.setString(1, this.album); // Nombre del álbum
                stmt.setString(2, this.fecha_lanzamiento); // Fecha de lanzamiento

                // Leer los datos de la imagen como bytes si se proporcionó una nueva imagen
                if (this.imagePath != null) {
                    File file = new File(this.imagePath);
                    FileInputStream fis = new FileInputStream(file);
                    stmt.setBinaryStream(3, fis, (int) file.length()); // Insertar los datos binarios de la imagen
                } else {
                    stmt.setNull(3, java.sql.Types.BLOB); // Si no hay nueva imagen, insertar NULL
                }

                stmt.setInt(4, this.id_album); // ID del álbum a actualizar

                rowCount = stmt.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowCount;
    }



    // Eliminar un álbum
    public void delete() {
        String query = "DELETE FROM album WHERE id_album = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, this.id_album);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Seleccionar todos los álbumes
    public ObservableList<AlbumDAO> selectAll() {
        ObservableList<AlbumDAO> listaAlb = FXCollections.observableArrayList();
        String query = "SELECT * FROM album";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                AlbumDAO objAlb = new AlbumDAO();
                objAlb.id_album = res.getInt("id_album");
                objAlb.album = res.getString("album");
                objAlb.fecha_lanzamiento = res.getString("fecha_lanzamiento");

                // Leer los datos binarios de la imagen
                InputStream inputStream = res.getBinaryStream("imagen");
                if (inputStream != null) {
                    try {
                        Image image = new Image(inputStream);
                        objAlb.setImg_album(image);
                    } catch (Exception e) {
                        System.err.println("Error al convertir el InputStream a Image: " + e.getMessage());
                    }
                } else {
                    System.out.println("El campo imagen es NULL para el registro con id_album " + objAlb.id_album);
                }

                listaAlb.add(objAlb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaAlb;
    }



}

