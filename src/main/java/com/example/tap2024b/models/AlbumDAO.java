package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AlbumDAO {
    private int id_album;
    private String album;
    private String fecha_lanzamiento;
    private Image img_album;

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

    public String getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(String fecha_lanzamiento) {
        this.fecha_lanzamiento = String.valueOf(fecha_lanzamiento);
    }

    public Image getImg_album() {
        return img_album;
    }

    public void setImg_album(Image img_album) {
        this.img_album = img_album;
    }

    public int insert() {
        int rowCount;
        // Cambio de 'foto' a 'imagen' para que coincida con el nombre de la columna en la base de datos
        String query = "INSERT INTO album(album, fecha_lanzamiento, imagen) VALUES(?, ?, ?)";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            // Establecer los valores de las columnas
            stmt.setString(1, this.album);
            stmt.setString(2, this.fecha_lanzamiento);

            // Convertir la imagen a un arreglo de bytes y establecerlo en el PreparedStatement
            stmt.setBytes(3, imageToByteArray(this.img_album));

            rowCount = stmt.executeUpdate();
        } catch (Exception e) {
            rowCount = 0;
            e.printStackTrace();
        }

        return rowCount;
    }

    public void update(){
        String query= "update album set album='"+this.album+"', fecha_lanzamiento='"+this.fecha_lanzamiento+"' ,imagen='"+this.img_album+"' where id_album='"+this.id_album+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(){
        String query= "delete from album where id_album='"+id_album+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public ObservableList<AlbumDAO> selectAll() {
        AlbumDAO objAlb;
        String query = "SELECT * FROM album";
        ObservableList<AlbumDAO> listaAlb = FXCollections.observableArrayList();

        try (Statement stmt = Conexion.connection.createStatement()) {
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objAlb = new AlbumDAO();
                objAlb.setId_album(res.getInt("id_album"));
                objAlb.setAlbum(res.getString("album"));
                objAlb.setFecha_lanzamiento(res.getString("fecha_lanzamiento"));

                // Obtener la imagen desde el BLOB
                InputStream inputStream = res.getBlob("imagen").getBinaryStream();

                if (inputStream != null) {
                    Image image = new Image(inputStream);
                    objAlb.setImg_album(image);  // Almacenar la imagen como un Image
                } else {
                    System.out.println("No se encontró la imagen en la base de datos.");
                }

                listaAlb.add(objAlb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaAlb;
    }



    public byte[] imageToByteArray(Image image) {
        // Creamos un ByteArrayOutputStream donde escribiremos la imagen en formato de bytes
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Obtenemos el ancho y alto de la imagen
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        // Creamos un PixelReader para acceder a los píxeles de la imagen
        PixelReader pixelReader = image.getPixelReader();

        if (pixelReader != null) {
            // Iteramos sobre todos los píxeles de la imagen
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Leemos el color de cada píxel
                    Color color = pixelReader.getColor(x, y);

                    // Convertimos el color a sus componentes RGBA (rojo, verde, azul, alfa)
                    byteArrayOutputStream.write((int)(color.getRed() * 255));
                    byteArrayOutputStream.write((int)(color.getGreen() * 255));
                    byteArrayOutputStream.write((int)(color.getBlue() * 255));
                    byteArrayOutputStream.write((int)(color.getOpacity() * 255)); // Alfa (transparencia)
                }
            }
        }

        // Retornamos el arreglo de bytes que representa la imagen
        return byteArrayOutputStream.toByteArray();
    }
}
