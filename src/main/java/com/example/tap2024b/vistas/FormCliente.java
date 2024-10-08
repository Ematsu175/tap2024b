package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {
    private Scene escena;
    private TextField txtNombre, txtEmail, txtTelefono;
    private Button btnGuardar;
    private VBox vbox;
    private ClienteDAO objCliente;

    public FormCliente(){
        objCliente = new ClienteDAO();
        CrearUI();
        this.setTitle("");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        txtNombre = new TextField();
        txtNombre.setPromptText("Nombre del cliente");
        txtEmail = new TextField();
        txtEmail.setPromptText("Email del cliente");
        txtTelefono = new TextField();
        txtTelefono.setPromptText("Telefono del cliente");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCliente());
        vbox = new VBox(txtNombre, txtEmail, txtTelefono, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 150, 150);
    }

    private void GuardarCliente() {
        objCliente.setEmail(txtEmail.getText());
        objCliente.setNombre(txtNombre.getText());
        objCliente.setTelefono(txtTelefono.getText());
        String msj;
        Alert.AlertType type;
        if (objCliente.insert() > 0){
            msj= "Registro insertado";
            type = Alert.AlertType.INFORMATION;
        } else {
            msj = "Ocurrio un error al insertar";
            type = Alert.AlertType.ERROR;
        }
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setContentText(msj);
        alerta.showAndWait();

    }
}
