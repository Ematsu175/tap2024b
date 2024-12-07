package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellCancion;
import com.example.tap2024b.components.ButtonCellVenta;
import com.example.tap2024b.models.CancionDAO;
import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.models.VentaDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListaVenta extends Stage {
    private TableView<VentaDAO> tbvVenta;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private TableView<VentaDAO> tbvCarrito; // Tabla para el carrito
    private ObservableList<VentaDAO> carrito; // Lista del carrito
    private Button btnComprar;
    private VentaDAO v = new VentaDAO();


    public ListaVenta() {
        CrearUI();

        tbvVenta.refresh();
        this.setTitle("Venta");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tlbMenu = new ToolBar();

        // Botón para ver historial de compras
        Button btnHistorial = new Button("Ver Historial");
        btnHistorial.setOnAction(event -> mostrarHistorialCompras());

        // Botón para ver datos personales
        Button btnDatosPersonales = new Button("Datos Personales");
        btnDatosPersonales.setOnAction(event -> mostrarDatosPersonales());

        tlbMenu.getItems().addAll(btnHistorial, btnDatosPersonales);

        tbvVenta = new TableView<>();
        tbvCarrito = new TableView<>();
        carrito = FXCollections.observableArrayList();
        CrearTable();
        CrearCarrito();

        btnComprar = new Button("Comprar");
        btnComprar.setOnAction(event -> realizarCompra());

        vBox = new VBox(tlbMenu, tbvVenta, tbvCarrito, btnComprar);
        escena = new Scene(vBox, 800, 600);
    }
    private void CrearTable() {
        VentaDAO objVen = new VentaDAO();

        // Columna para el nombre de la canción
        TableColumn<VentaDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        // Columna para la duración
        TableColumn<VentaDAO, String> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        // Columna para el costo
        TableColumn<VentaDAO, Float> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        // Columna para el nombre del género
        TableColumn<VentaDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        // Columna para editar
        TableColumn<VentaDAO, String> tbcAdd = new TableColumn<>("Editar");
        tbcAdd.setCellFactory(col -> new ButtonCellVenta("Añadir", carrito));

        // Agregar columnas al TableView
        tbvVenta.getColumns().addAll(tbcCancion, tbcDuracion, tbcCosto, tbcGenero, tbcAdd);

        // Configurar los datos iniciales
        tbvVenta.setItems(objVen.selectAll());
    }


    private void CrearCarrito() {
        TableColumn<VentaDAO, String> tbcCancionCarrito = new TableColumn<>("Canción");
        tbcCancionCarrito.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<VentaDAO, Float> tbcCostoCarrito = new TableColumn<>("Costo");
        tbcCostoCarrito.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<VentaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(new Callback<TableColumn<VentaDAO, String>, TableCell<VentaDAO, String>>() {
            @Override
            public TableCell<VentaDAO, String> call(TableColumn<VentaDAO, String> ventaDAOStringTableColumn) {
                return new ButtonCellVenta("Eliminar", carrito);
            }
        });

        tbvCarrito.getColumns().addAll(tbcCancionCarrito, tbcCostoCarrito, tbcEliminar);
        tbvCarrito.setItems(carrito);
    }

    private void realizarCompra() {
        if (carrito.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "El carrito está vacío.");
            alert.showAndWait();
            return;
        }

        // Insertar en la tabla `venta`
        int idVenta = v.insertarVenta();

        if (idVenta > 0) {
            // Insertar en `venta_detalle`
            for (VentaDAO cancion : carrito) {
                v.insertarDetalle(idVenta, cancion.getId_cancion(), cancion.getCosto());
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Compra realizada con éxito.");
            alert.showAndWait();
            carrito.clear(); // Limpiar carrito después de la compra
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al realizar la compra.");
            alert.showAndWait();
        }
    }

    private void mostrarHistorialCompras() {
        Stage ventanaHistorial = new Stage();
        TableView<VentaDAO> tbvHistorial = new TableView<>();
        TableView<VentaDAO> tbvDetalles = new TableView<>();

        // Configurar columnas para las compras
        TableColumn<VentaDAO, Integer> tbcIdVenta = new TableColumn<>("ID Venta");
        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("id_venta"));

        TableColumn<VentaDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tbvHistorial.getColumns().addAll(tbcIdVenta, tbcFecha);
        tbvHistorial.setItems(obtenerHistorialCompras());

        // Configurar columnas para los detalles
        TableColumn<VentaDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<VentaDAO, Float> tbcMonto = new TableColumn<>("Monto");
        tbcMonto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tbvDetalles.getColumns().addAll(tbcCancion, tbcMonto);

        tbvHistorial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tbvDetalles.setItems(obtenerDetallesCompra(newSelection.getId_venta()));
            }
        });

        VBox vbox = new VBox(new Label("Historial de Compras"), tbvHistorial, new Label("Detalles de la Compra"), tbvDetalles);
        Scene escena = new Scene(vbox, 600, 400);
        ventanaHistorial.setScene(escena);
        ventanaHistorial.setTitle("Historial de Compras");
        ventanaHistorial.show();
    }

    private ObservableList<VentaDAO> obtenerHistorialCompras() {
        ObservableList<VentaDAO> historial = FXCollections.observableArrayList();
        String query = "SELECT id_venta, fecha FROM venta WHERE id_cliente = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, UsuarioSesion.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VentaDAO venta = new VentaDAO();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setFecha(rs.getString("fecha"));
                historial.add(venta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historial;
    }

    private ObservableList<VentaDAO> obtenerDetallesCompra(int idVenta) {
        ObservableList<VentaDAO> detalles = FXCollections.observableArrayList();
        String query = "SELECT c.cancion, vd.monto_total FROM venta_detalle vd " +
                "JOIN cancion c ON vd.id_cancion = c.id_cancion WHERE vd.id_venta = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, idVenta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VentaDAO detalle = new VentaDAO();
                detalle.setCancion(rs.getString("cancion"));
                detalle.setCosto(rs.getFloat("monto_total"));
                detalles.add(detalle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detalles;
    }

    private void mostrarDatosPersonales() {
        Stage ventanaDatos = new Stage();

        // Obtener datos del usuario
        ClienteDAO cliente = obtenerDatosPersonales();

        // Crear elementos visuales
        Label lblNombre = new Label("Nombre: " + cliente.getNombre());
        Label lblEmail = new Label("Email: " + cliente.getEmail());
        Label lblTelefono = new Label("Teléfono: " + cliente.getTelefono());

        VBox vbox = new VBox(lblNombre, lblEmail, lblTelefono);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        Scene escena = new Scene(vbox, 300, 200);
        ventanaDatos.setScene(escena);
        ventanaDatos.setTitle("Datos Personales");
        ventanaDatos.show();
    }

    private ClienteDAO obtenerDatosPersonales() {
        ClienteDAO cliente = new ClienteDAO();
        String query = "SELECT cliente, email, telefono FROM cliente WHERE id_cliente = ?";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, UsuarioSesion.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setNombre(rs.getString("cliente"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cliente;
    }

}
