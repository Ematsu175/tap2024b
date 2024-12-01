package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FormCliente extends Stage {
    private Scene escena;
    private TextField txtNombre, txtEmail, txtTelefono;
    private PasswordField txtPass;
    private Button btnGuardar;
    private VBox vbox;
    private ClienteDAO objCliente;
    private TableView<ClienteDAO> tbvClientes;

    public FormCliente(TableView<ClienteDAO> tbv, ClienteDAO objc){
        tbvClientes = tbv;
        CrearUI();
        if (objc != null) {
            this.objCliente =  objc;
            txtNombre.setText(objCliente.getNombre());
            txtEmail.setText(objCliente.getEmail());
            txtTelefono.setText(objCliente.getTelefono());
            txtPass.setText(objCliente.getContrasena());
            this.setTitle("Editar Cliente");
        }else {
            this.objCliente = new ClienteDAO();
            this.setTitle("Insertar cliente");
        }
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
        txtPass = new PasswordField();
        txtPass.setPromptText("Ingres la contraseña");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarCliente());
        vbox = new VBox(txtNombre, txtEmail, txtTelefono, txtPass, btnGuardar);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);
        escena = new Scene(vbox, 250, 250);
    }

    private void GuardarCliente() {
        objCliente.setEmail(txtEmail.getText());
        objCliente.setNombre(txtNombre.getText());
        objCliente.setTelefono(txtTelefono.getText());
        objCliente.setContrasena(txtPass.getText());
        String msj;
        Alert.AlertType type;

        if (objCliente.getId_cliente()>0){
            objCliente.update();
        } else {
            if (objCliente.insert() > 0){
                msj= "Registro insertado";
                type = Alert.AlertType.INFORMATION;
            } else {
                msj = "Ocurrio un error al insertar";
                type = Alert.AlertType.ERROR;
            }
            Alert alerta = new Alert(type);
            alerta.setTitle("Mensaje del Sistema");
            alerta.setContentText(msj);
            alerta.showAndWait();
        }

        tbvClientes.setItems(objCliente.selectAll());
        tbvClientes.refresh();
        this.close();
    }
}
