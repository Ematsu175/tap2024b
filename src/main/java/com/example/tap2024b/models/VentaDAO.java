package com.example.tap2024b.models;

import com.example.tap2024b.vistas.UsuarioSesion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class VentaDAO {
    private int id_cancion;
    private String cancion, duracion;
    private float costo;
    private int id_genero;
    private String nombreGenero;

    private int id_venta;
    private String fecha;
    private float total;

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

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

    public int insertarVenta() {
        int idVenta = 0;
        String query = "INSERT INTO venta (fecha, id_cliente) VALUES (CURRENT_DATE(), ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, UsuarioSesion.getIdCliente()); // Obtener el ID del cliente autenticado
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                idVenta = keys.getInt(1); // Obtener el ID de la venta generada
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return idVenta;
    }


    public void insertarDetalle(int idVenta, int idCancion, float monto) {
        String query = "INSERT INTO venta_detalle (id_venta, id_cancion, monto_total) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idVenta);
            stmt.setInt(2, idCancion);
            stmt.setFloat(3, monto);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public ObservableList<VentaDAO> selectAll() {
        ObservableList<VentaDAO> listaVen = FXCollections.observableArrayList();
        String query = "SELECT c.id_cancion, c.cancion, c.duracion, c.costo, g.genero, g.id_genero " +
                "FROM cancion c " +
                "JOIN genero g ON c.id_genero = g.id_genero";

        try (Statement stmt = Conexion.connection.createStatement();
             ResultSet res = stmt.executeQuery(query)) {

            while (res.next()) {
                VentaDAO objVen = new VentaDAO();

                objVen.setId_cancion(res.getInt("id_cancion"));  // ID de la canción
                objVen.setCancion(res.getString("cancion"));     // Nombre de la canción
                objVen.setDuracion(res.getString("duracion"));   // Duración
                objVen.setCosto(res.getFloat("costo"));          // Costo
                objVen.setId_genero(res.getInt("id_genero"));    // ID del género

                objVen.setNombreGenero(res.getString("genero"));

                listaVen.add(objVen);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaVen;
    }


    @Override
    public String toString() {
        return this.cancion; // Retorna el nombre de la canción para mostrarlo en el ComboBox
    }
}
