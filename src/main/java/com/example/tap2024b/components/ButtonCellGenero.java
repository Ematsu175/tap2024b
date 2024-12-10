package com.example.tap2024b.components;

import com.example.tap2024b.models.ArtistaDAO;
import com.example.tap2024b.models.GeneroDAO;
import com.example.tap2024b.vistas.FormArtista;
import com.example.tap2024b.vistas.FormGenero;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCellGenero extends TableCell<GeneroDAO, String> {
    Button btnCelda;

    public ButtonCellGenero(String str){
        btnCelda = new Button(str);
        if (str.equals("Editar")) {
            btnCelda.setStyle("-fx-background-color: yellow; -fx-text-fill: black; -fx-font-weight: bold;");
        } else if (str.equals("Eliminar")) {
            btnCelda.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold;");
        }
        btnCelda.setOnAction(event -> EventoVoton(str));
    }

    private void EventoVoton(String str) {
        GeneroDAO objGenero= this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormGenero(this.getTableView(),objGenero);

        } else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Mensaje del sistema");
            alerta.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if(opcion.get() == ButtonType.OK){
                objGenero.delete();
                this.getTableView().setItems(objGenero.selectAll());
                this.getTableView().refresh();
            }
        }
    }

    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}
