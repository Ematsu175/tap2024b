package com.example.tap2024b.vistas;

import com.example.tap2024b.components.ButtonCellCancion;
import com.example.tap2024b.components.ButtonCellVenta;
import com.example.tap2024b.models.CancionDAO;
import com.example.tap2024b.models.ClienteDAO;
import com.example.tap2024b.models.Conexion;
import com.example.tap2024b.models.VentaDAO;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ListaVenta extends Stage {
    private TableView<VentaDAO> tbvVenta;
    private Button btnAgregar;
    private ToolBar tlbMenu;
    private VBox vBox;
    private Scene escena;
    private TableView<VentaDAO> tbvCarrito;
    private ObservableList<VentaDAO> carrito;
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

        Button btnHistorial = new Button("Ver Historial");
        btnHistorial.setOnAction(event -> mostrarHistorialCompras());

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

        TableColumn<VentaDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<VentaDAO, String> tbcDuracion = new TableColumn<>("Duración");
        tbcDuracion.setCellValueFactory(new PropertyValueFactory<>("duracion"));

        TableColumn<VentaDAO, Float> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<VentaDAO, String> tbcGenero = new TableColumn<>("Género");
        tbcGenero.setCellValueFactory(new PropertyValueFactory<>("nombreGenero"));

        TableColumn<VentaDAO, String> tbcAdd = new TableColumn<>("Editar");
        tbcAdd.setCellFactory(col -> new ButtonCellVenta("Añadir", carrito));

        tbvVenta.getColumns().addAll(tbcCancion, tbcDuracion, tbcCosto, tbcGenero, tbcAdd);

        tbvVenta.setItems(objVen.selectAll());
    }


    private void CrearCarrito() {
        TableColumn<VentaDAO, String> tbcCancionCarrito = new TableColumn<>("Canción");
        tbcCancionCarrito.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<VentaDAO, Float> tbcCostoCarrito = new TableColumn<>("Costo");
        tbcCostoCarrito.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<VentaDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(col -> new TableCell<>() {
            private final Button btnEliminar = new Button("Eliminar");

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    return;
                }

                VentaDAO venta = (VentaDAO) getTableRow().getItem();

                if ("Total".equals(venta.getCancion())) {
                    setGraphic(null);
                } else {
                    btnEliminar.setOnAction(event -> {
                        carrito.remove(venta);
                    });
                    setGraphic(btnEliminar);
                }
            }
        });

        tbvCarrito.getColumns().addAll(tbcCancionCarrito, tbcCostoCarrito, tbcEliminar);
        tbvCarrito.setItems(carrito);

        carrito.addListener((javafx.collections.ListChangeListener<VentaDAO>) change -> actualizarTotalCarrito());
    }


    private void actualizarTotalCarrito() {
        float total = 0;
        for (VentaDAO venta : carrito) {
            total += venta.getCosto();
        }

        VentaDAO totalRow = new VentaDAO();
        totalRow.setCancion("Total");
        totalRow.setCosto(total);

        ObservableList<VentaDAO> updatedCarrito = FXCollections.observableArrayList(carrito);
        updatedCarrito.add(totalRow);

        tbvCarrito.setItems(updatedCarrito);
    }

    private void realizarCompra() {
        if (carrito.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "El carrito está vacío.");
            alert.showAndWait();
            return;
        }

        int idVenta = v.insertarVenta();

        if (idVenta > 0) {
            for (VentaDAO cancion : carrito) {
                v.insertarDetalle(idVenta, cancion.getId_cancion(), cancion.getCosto());
            }

            generarOrdenCompraPDF(idVenta);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Compra realizada con éxito. Se ha generado el PDF de la orden de compra.");
            alert.showAndWait();
            carrito.clear();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al realizar la compra.");
            alert.showAndWait();
        }
    }

    private void mostrarHistorialCompras() {
        Stage ventanaHistorial = new Stage();
        TableView<VentaDAO> tbvHistorial = new TableView<>();
        TableView<VentaDAO> tbvDetalles = new TableView<>();

        TableColumn<VentaDAO, Integer> tbcIdVenta = new TableColumn<>("ID Venta");
        tbcIdVenta.setCellValueFactory(new PropertyValueFactory<>("id_venta"));

        TableColumn<VentaDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        tbvHistorial.getColumns().addAll(tbcIdVenta, tbcFecha);
        tbvHistorial.setItems(obtenerHistorialCompras());

        TableColumn<VentaDAO, String> tbcCancion = new TableColumn<>("Canción");
        tbcCancion.setCellValueFactory(new PropertyValueFactory<>("cancion"));

        TableColumn<VentaDAO, Float> tbcMonto = new TableColumn<>("Monto");
        tbcMonto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        tbvDetalles.getColumns().addAll(tbcCancion, tbcMonto);

        tbvHistorial.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                tbvDetalles.setItems(obtenerDetallesCompra(newSelection.getId_venta()));
                agregarFilaTotal(tbvDetalles);
            }
        });

        VBox vbox = new VBox(new Label("Historial de Compras"), tbvHistorial, new Label("Detalles de la Compra"), tbvDetalles);
        Scene escena = new Scene(vbox, 600, 400);
        ventanaHistorial.setScene(escena);
        ventanaHistorial.setTitle("Historial de Compras");
        ventanaHistorial.show();
    }


    private void agregarFilaTotal(TableView<VentaDAO> tbvDetalles) {
        float total = 0;

        for (VentaDAO detalle : tbvDetalles.getItems()) {
            total += detalle.getCosto();
        }

        VentaDAO totalRow = new VentaDAO();
        totalRow.setCancion("Total");
        totalRow.setCosto(total);

        ObservableList<VentaDAO> items = FXCollections.observableArrayList(tbvDetalles.getItems());
        items.add(totalRow);
        tbvDetalles.setItems(items);
    }




    private ObservableList<VentaDAO> obtenerHistorialCompras() {
        ObservableList<VentaDAO> historial = FXCollections.observableArrayList();
        String query = "SELECT v.id_venta, v.fecha, SUM(vd.monto_total) as total " +
                "FROM venta v " +
                "JOIN venta_detalle vd ON v.id_venta = vd.id_venta " +
                "WHERE v.id_cliente = ? " +
                "GROUP BY v.id_venta, v.fecha";
        try (PreparedStatement stmt = Conexion.connection.prepareStatement(query)) {
            stmt.setInt(1, UsuarioSesion.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VentaDAO venta = new VentaDAO();
                venta.setId_venta(rs.getInt("id_venta"));
                venta.setFecha(rs.getString("fecha"));
                venta.setTotal(rs.getFloat("total")); // Asume que existe el atributo "total" en VentaDAO
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

        ClienteDAO cliente = obtenerDatosPersonales();

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

    private void generarOrdenCompraPDF(int idVenta) {
        Document document = new Document();

        try {
            String filePath = "C:/Users/emyva/Downloads/orden_compra_" + idVenta + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            Paragraph title = new Paragraph("Orden de Compra");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            ClienteDAO cliente = obtenerDatosPersonales();
            document.add(new Paragraph("Cliente: " + cliente.getNombre()));
            document.add(new Paragraph("Email: " + cliente.getEmail()));
            document.add(new Paragraph("Teléfono: " + cliente.getTelefono()));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            PdfPCell header1 = new PdfPCell(new Paragraph("Canción"));
            PdfPCell header2 = new PdfPCell(new Paragraph("Monto"));
            table.addCell(header1);
            table.addCell(header2);

            ObservableList<VentaDAO> detalles = obtenerDetallesCompra(idVenta);
            float total = 0;

            for (VentaDAO detalle : detalles) {
                table.addCell(detalle.getCancion());
                table.addCell(String.valueOf(detalle.getCosto()));
                total += detalle.getCosto();
            }

            PdfPCell totalCell = new PdfPCell(new Paragraph("Total"));
            totalCell.setColspan(1);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(totalCell);
            table.addCell(String.valueOf(total));

            document.add(table);

            document.add(new Paragraph("\nGracias por su compra."));
            System.out.println("PDF generado correctamente: " + filePath);

        } catch (Exception e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            e.printStackTrace();
        } finally {
            document.close();
        }
    }



}
