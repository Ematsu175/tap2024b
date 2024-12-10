package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class GeneroDAO {
    private int id_genero;
    private String genero;

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int insert(){
        int rowCount;
        String query= "insert into genero(genero)"+
                " values('"+this.genero+"')";
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
        String query= "update genero set genero='"+this.genero+
                "' where id_genero='"+id_genero+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(){
        String query= "delete from genero where id_genero='"+id_genero+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public ObservableList<GeneroDAO> selectAll(){
        GeneroDAO objGen;
        String query = "select * from genero";
        ObservableList<GeneroDAO> listaG = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objGen = new GeneroDAO();
                objGen.id_genero = res.getInt(1);
                objGen.genero = res.getString(2);
                listaG.add(objGen);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaG;

    }
    @Override
    public String toString() {
        return this.genero;
    }
}
