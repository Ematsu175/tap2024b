package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ArtistaDAO {
    private int id_artista;
    private String artista;

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

    public int insert(){
        int rowCount;
        String query= "insert into artista(artista)"+
                " values('"+this.artista+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            rowCount = stmt.executeUpdate(query);
        } catch (Exception e){
            rowCount = 0;
            e.printStackTrace();
        }

        return rowCount;

    }
    public void update(){
        String query= "update artista set artista='"+this.artista+
                "' where id_artista='"+id_artista+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(){
        String query= "delete from artista where id_artista='"+id_artista+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public ObservableList<ArtistaDAO> selectAll(){
        ArtistaDAO objArt;
        String query = "select * from artista";
        ObservableList<ArtistaDAO> listaA = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objArt = new ArtistaDAO();
                objArt.id_artista = res.getInt(1);
                objArt.artista = res.getString(2);
                listaA.add(objArt);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaA;

    }
    @Override
    public String toString() {
        return this.artista;
    }
}
