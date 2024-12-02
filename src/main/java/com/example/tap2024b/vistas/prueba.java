package com.example.tap2024b.vistas;

import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.util.Callback;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class prueba extends Stage {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/spotify";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "123";

    public prueba() {
        // Crear la TableView y configurar las columnas
        TableView<Data> table = new TableView<>();
        TableColumn<Data, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Data, Image> imageColumn = new TableColumn<>("Imagen");

        // Configurar columna ID
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        // Configurar columna de imagen
        imageColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Data, Image>, ObservableValue<Image>>() {
            @Override
            public ObservableValue<Image> call(TableColumn.CellDataFeatures<Data, Image> param) {
                return new ReadOnlyObjectWrapper<>(param.getValue().getImage());
            }
        });

        // Personalizar la celda de la imagen
        imageColumn.setCellFactory(new Callback<TableColumn<Data, Image>, TableCell<Data, Image>>() {
            @Override
            public TableCell<Data, Image> call(TableColumn<Data, Image> param) {
                return new TableCell<Data, Image>() {
                    @Override
                    protected void updateItem(Image item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setGraphic(null);
                        } else {
                            ImageView imageView = new ImageView(item);
                            imageView.setFitWidth(100);
                            imageView.setFitHeight(100);
                            setGraphic(imageView);
                        }
                    }
                };
            }
        });

        // Añadir las columnas a la tabla
        table.getColumns().add(idColumn);
        table.getColumns().add(imageColumn);

        // Obtener los datos de la base de datos
        List<Data> dataList = fetchDataFromDatabase();
        table.getItems().addAll(dataList);

        // Crear un contenedor VBox y agregar la tabla
        VBox vbox = new VBox(table);
        Scene scene = new Scene(vbox);

        // Configurar la escena y la ventana (Stage)
        this.setTitle("Tabla de Base de Datos");
        this.setScene(scene);
        this.show();
    }

    private List<Data> fetchDataFromDatabase() {
        List<Data> dataList = new ArrayList<>();
        String query = "SELECT id, imagen FROM imagenes"; // Ajusta el nombre de tu tabla

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                Blob blob = rs.getBlob("imagen");
                Image image = null;
                if (blob != null) {
                    image = new Image(blob.getBinaryStream());
                }
                dataList.add(new Data(id, image));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataList;
    }

    // Asegúrate de que esta clase esté pública y accesible
    public static class Data {
        private final Integer id;
        private final Image image;

        public Data(Integer id, Image image) {
            this.id = id;
            this.image = image;
        }

        public Integer getId() {
            return id;
        }

        public Image getImage() {
            return image;
        }
    }
}
