package com.example.tap2024b.vistas;

import com.example.tap2024b.models.ClienteDAO;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Spotify extends Stage {
    private Scene escena;
    private VBox vboxLogin;
    private TextField txtUsuario;
    private PasswordField txtPass;
    private Button btnLogin;
    private Label lblUsuario;
    private Label lblPass;
    private Image img;
    private ImageView imvLogo;
    private ClienteDAO obj = new ClienteDAO();
    public Spotify(){
        CrearUI();
        this.setTitle("Spotify");
        this.setScene(escena);
        this.show();
    }
    private void CrearUI(){
        img = new Image(getClass().getResource("/images/spotify.png").toString());
        imvLogo = new ImageView(img);
        imvLogo.setFitWidth(200);
        imvLogo.setFitHeight(200);
        imvLogo.setPreserveRatio(true);
        lblUsuario = new Label("Usuario");
        txtUsuario = new TextField();
        txtUsuario.setMaxWidth(200);
        lblPass = new Label("Contraseña");
        txtPass = new PasswordField();
        txtPass.setMaxWidth(200);
        btnLogin = new Button("Login");
        btnLogin.setOnAction(event -> IniciarSesion());

        vboxLogin = new VBox(imvLogo,lblUsuario, txtUsuario, lblPass, txtPass, btnLogin);
        vboxLogin.setAlignment(javafx.geometry.Pos.CENTER);
        vboxLogin.setSpacing(10);

        escena=new Scene(vboxLogin, 500, 500);

    }

    public void IniciarSesion() {
        String correo = txtUsuario.getText();
        String pass = txtPass.getText();
        Label lblMensaje = new Label();

        if (correo.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Por favor ingresa usuario y contraseña");
            System.out.println("Ingresa usuario contraseña");
            return;
        }

        int idCliente = obj.RealizarLogin(correo, pass); // Obtiene el ID del cliente
        if (idCliente != -1) {
            lblMensaje.setText("¡Login exitoso!");
            System.out.println("Login exitoso");

            // Almacenar el ID del cliente para su uso posterior
            UsuarioSesion.setIdCliente(idCliente);

            if (correo.equals("emanuel@gmail.com")) {
                SpotifyAdmin spa = new SpotifyAdmin();
                spa.show();
            } else {
                SpotifyUsuario spu = new SpotifyUsuario();
                spu.show();
            }

            Stage ventanaActual = (Stage) btnLogin.getScene().getWindow();
            ventanaActual.close();
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos");
            System.out.println("Usuario o contraseña incorrectos");
        }
    }


    /*public void IniciarSesion(){
        String correo = txtUsuario.getText();
        String pass = txtPass.getText();
        Label lblMensaje=new Label();
        ClienteDAO obj = new ClienteDAO();

        if (correo.isEmpty() || pass.isEmpty()) {
            lblMensaje.setText("Por favor ingresa usuario y contraseña");
            System.out.println("Ingresa usuario contraseña");
            return;
        }
        if (obj.RealizarLogin(correo, pass)) {
            lblMensaje.setText("¡Login exitoso!");
            System.out.println("Login exitoso");
            if (correo.equals("emanuel@gmail.com")){
                SpotifyAdmin spa = new SpotifyAdmin();
                spa.show();
                Stage ventanaActual = (Stage) btnLogin.getScene().getWindow();
                ventanaActual.close();
            } else {
                SpotifyUsuario spu = new SpotifyUsuario();
                spu.show();
                Stage ventanaActual = (Stage) btnLogin.getScene().getWindow();
                ventanaActual.close();
            }
        } else {
            lblMensaje.setText("Usuario o contraseña incorrectos");
            System.out.println("Usuario o contraseña incorrectos");
        }

    }*/

}
