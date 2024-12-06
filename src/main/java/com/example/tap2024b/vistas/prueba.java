package com.example.tap2024b.vistas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ByteArrayInputStream;
import java.sql.*;

public class prueba extends Stage {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/spotify";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "123";
    private TableView<Album> tableView;

    public prueba() {
        setTitle("Album Table");
        tableView = new TableView<>();
        setupTableView();
        loadDataFromDatabase();

        Scene scene = new Scene(tableView, 600, 400);
        setScene(scene);
        this.show();
    }

    private void setupTableView() {
        TableColumn<Album, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idAlbum"));

        TableColumn<Album, String> albumColumn = new TableColumn<>("Album");
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("album"));

        TableColumn<Album, String> fechaColumn = new TableColumn<>("Fecha Lanzamiento");
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaLanzamiento"));

        TableColumn<Album, ImageView> imagenColumn = new TableColumn<>("Imagen");
        imagenColumn.setCellValueFactory(new PropertyValueFactory<>("imagen"));

        tableView.getColumns().addAll(idColumn, albumColumn, fechaColumn, imagenColumn);
    }

    private void loadDataFromDatabase() {
        ObservableList<Album> data = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM album")) {

            while (resultSet.next()) {
                int idAlbum = resultSet.getInt("id_album");
                String album = resultSet.getString("album");
                String fechaLanzamiento = resultSet.getString("fecha_lanzamiento");
                byte[] imagenBytes = resultSet.getBytes("imagen"); // Leer los datos binarios BLOB

                // Convertir byte[] en ImageView
                ImageView imageView = null;
                if (imagenBytes != null && imagenBytes.length > 0) {
                    try {
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(imagenBytes);
                        Image image = new Image(inputStream);
                        imageView = new ImageView(image);
                        imageView.setFitWidth(100); // Ajusta el ancho de la imagen
                        imageView.setFitHeight(100); // Ajusta la altura de la imagen
                        imageView.setPreserveRatio(true); // Mantiene la relación de aspecto
                    } catch (Exception e) {
                        System.err.println("Error al cargar la imagen para ID: " + idAlbum);
                        e.printStackTrace();
                    }
                }

                data.add(new Album(idAlbum, album, fechaLanzamiento, imageView));
            }

            tableView.setItems(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Album {
        private int idAlbum;
        private String album;
        private String fechaLanzamiento;
        private ImageView imagen;

        public Album(int idAlbum, String album, String fechaLanzamiento, ImageView imagen) {
            this.idAlbum = idAlbum;
            this.album = album;
            this.fechaLanzamiento = fechaLanzamiento;
            this.imagen = imagen;
        }

        public int getIdAlbum() {
            return idAlbum;
        }

        public String getAlbum() {
            return album;
        }

        public String getFechaLanzamiento() {
            return fechaLanzamiento;
        }

        public ImageView getImagen() {
            return imagen;
        }
    }
}


