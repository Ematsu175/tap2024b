package com.example.tap2024b.models;

import java.sql.Statement;

public class ClienteDAO {
    private int id_cliente;
    private String nombre, email;
    private char telefono;

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

    public char getTelefono() {
        return telefono;
    }

    public void setTelefono(char telefono) {
        this.telefono = telefono;
    }

    public void insert(){
        String query= "insert into cliente(nombre, telefono, email)"+
                      " values('"+this.nombre+"','"+this.telefono+"','"+this.email+"')";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public void update(){
        String query= "update cliente set nombre='"+this.nombre+
                "' telefono='"+this.telefono+"', email='"+this.email+
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
    public void selectAll(){

    }
}
