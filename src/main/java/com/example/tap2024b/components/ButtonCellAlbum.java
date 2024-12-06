package com.example.tap2024b.components;

import com.example.tap2024b.models.AlbumDAO;
import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.vistas.FormAlbum;
import com.example.tap2024b.vistas.FormCliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Optional;

public class ButtonCellAlbum extends TableCell<AlbumDAO, String> {
    Button btnCelda;

    public ButtonCellAlbum(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> EventoVoton(str));
    }

    private void EventoVoton(String str) {
        AlbumDAO objAlbum = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")) {
            new FormAlbum(this.getTableView(), objAlbum);
        } else {
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Mensaje del sistema");
            alerta.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if (opcion.get() == ButtonType.OK) {
                objAlbum.delete();
                this.getTableView().setItems(objAlbum.selectAll());
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
