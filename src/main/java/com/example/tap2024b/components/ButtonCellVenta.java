package com.example.tap2024b.components;

import com.example.tap2024b.models.CancionDAO;
import com.example.tap2024b.models.VentaDAO;
import com.example.tap2024b.vistas.FormCancion;
import com.example.tap2024b.vistas.ListaVenta;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCellVenta extends TableCell<VentaDAO, String> {
    private final Button btnCelda;
    private final ObservableList<VentaDAO> carrito;

    public ButtonCellVenta(String str, ObservableList<VentaDAO> carrito) {
        this.carrito = carrito;
        btnCelda = new Button(str);
        if (str.equals("Añadir")) {
            btnCelda.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        }
        btnCelda.setOnAction(event -> EventoVoton(str));
    }

    private void EventoVoton(String str) {
        VentaDAO objCancion = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Añadir")) {
            if (carrito.stream().anyMatch(c -> c.getId_cancion() == objCancion.getId_cancion())) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Esta canción ya está en el carrito.");
                alert.showAndWait();
            } else {
                carrito.add(objCancion);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canción añadida al carrito.");
                alert.showAndWait();
            }
        } else if (str.equals("Eliminar")) {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Mensaje del sistema");
            alerta.setContentText("¿Deseas eliminar esta canción del carrito?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if (opcion.isPresent() && opcion.get() == ButtonType.OK) {
                carrito.remove(objCancion);
                this.getTableView().refresh();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Canción eliminada del carrito.");
                alert.showAndWait();
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            this.setGraphic(btnCelda);
        } else {
            this.setGraphic(null);
        }
    }
}

