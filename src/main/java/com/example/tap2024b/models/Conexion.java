package com.example.tap2024b.models;
import java.sql.DriverManager;
import java.sql.Connection;
public class Conexion {
    static private String DB="spotify";
    static private String USER="admin";
    static private String PASS="123";
    static private String Host="localhost";
    static private String PORT="3306";
    public static Connection connection;

    public static void CrearConexion(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://"+Host+":"+PORT+"/"+DB,USER,PASS);
            System.out.println("Conexion establcida a la base de datos");
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
