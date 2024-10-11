package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.Reader;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClienteDAO {
    private int id_cliente;
    private String nombre, email;
    private String telefono;

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int insert(){
        int rowCount;
        String query= "insert into cliente(nombre, telefono, email)"+
                      " values('"+this.nombre+"','"+this.telefono+"','"+this.email+"')";
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
        String query= "update cliente set nombre='"+this.nombre+
                "', telefono='"+this.telefono+"', email='"+this.email+
                "' where id_cliente='"+id_cliente+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public void delete(){
        String query= "delete from cliente where id_cliente='"+id_cliente+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public ObservableList<ClienteDAO> selectAll(){
        ClienteDAO objCte;
        String query = "select * from cliente";
        ObservableList<ClienteDAO> listaC = FXCollections.observableArrayList();
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objCte = new ClienteDAO();
                objCte.id_cliente = res.getInt(1);
                objCte.nombre = res.getString(2);
                objCte.telefono = res.getString(3);
                objCte.email = res.getString(4);
                listaC.add(objCte);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return listaC;

    }
}
