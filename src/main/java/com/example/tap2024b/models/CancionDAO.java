package com.example.tap2024b.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CancionDAO {
    private int id_cancion;
    private String cancion, duracion;
    private float costo;
    private int id_genero;
    private String nombreGenero;

    public String getNombreGenero() {
        return nombreGenero;
    }

    public void setNombreGenero(String nombreGenero) {
        this.nombreGenero = nombreGenero;
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

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public int getId_genero() {
        return id_genero;
    }

    public void setId_genero(int id_genero) {
        this.id_genero = id_genero;
    }

    public int insert() {
        int rowCount = 0;
        String query = "INSERT INTO cancion (cancion, duracion, costo, id_genero) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, this.cancion);
            stmt.setString(2, this.duracion);
            stmt.setFloat(3, this.costo);
            stmt.setInt(4, this.id_genero);

            rowCount = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rowCount;
    }


    public void update() {
        String query = "UPDATE cancion SET cancion = ?, duracion = ?, costo = ?, id_genero = ? WHERE id_cancion = ?";

        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setString(1, this.cancion);
            stmt.setString(2, this.duracion);
            stmt.setFloat(3, this.costo);
            stmt.setInt(4, this.id_genero);
            stmt.setInt(5, this.id_cancion);

            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void delete(){
        String query= "delete from cancion where id_cancion='"+id_cancion+"'";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public ObservableList<CancionDAO> selectAll() {
        ObservableList<CancionDAO> listaCan = FXCollections.observableArrayList();
        String query = "SELECT c.id_cancion, c.cancion, c.duracion, c.costo, g.genero, g.id_genero " +
                "FROM cancion c " +
                "JOIN genero g ON c.id_genero = g.id_genero";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                CancionDAO objCan = new CancionDAO();

                objCan.setId_cancion(res.getInt("id_cancion"));
                objCan.setCancion(res.getString("cancion"));
                objCan.setDuracion(res.getString("duracion"));
                objCan.setCosto(res.getFloat("costo"));
                objCan.setId_genero(res.getInt("id_genero"));

                objCan.setNombreGenero(res.getString("genero"));

                listaCan.add(objCan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaCan;
    }

    @Override
    public String toString() {
        return this.cancion;
    }

}
