package com.example.tap2024b.models;

import javafx.scene.image.Image;

import java.sql.Statement;
import java.util.Date;

public class AlbumDAO {
    private int id_album;
    private String album;
    private Date fecha_lanzamiento;
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

    public Date getFecha_lanzamiento() {
        return fecha_lanzamiento;
    }

    public void setFecha_lanzamiento(Date fecha_lanzamiento) {
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public Image getImg_album() {
        return img_album;
    }

    public void setImg_album(Image img_album) {
        this.img_album = img_album;
    }
    public int insert(){
        int rowCount;
        String query= "insert into album(album, fecha_lanzamiento, foto)"+
                " values('"+this.album+"','"+this.fecha_lanzamiento+"','"+this.img_album+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (Exception e){
            rowCount = 0;
            e.printStackTrace();
        }

        return rowCount;

    }
}
