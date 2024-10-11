package com.example.tap2024b.components;

import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.vistas.FormCliente;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;

import java.util.Objects;
import java.util.Optional;

public class ButtonCell extends TableCell<ClienteDAO, String> {
    Button btnCelda;

    public ButtonCell(String str){
        btnCelda = new Button(str);
        btnCelda.setOnAction(event -> EventoVoton(str));
    }

    private void EventoVoton(String str) {
        ClienteDAO objCliente = this.getTableView().getItems().get(this.getIndex());
        if (str.equals("Editar")){
            new FormCliente(this.getTableView(),objCliente);

        } else{
            Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
            alerta.setTitle("Mensaje del sistema");
            alerta.setContentText("¿Deseas eliminar el registro seleccionado?");
            Optional<ButtonType> opcion = alerta.showAndWait();
            if(opcion.get() == ButtonType.OK){
                objCliente.delete();
                this.getTableView().setItems(objCliente.selectAll());
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
