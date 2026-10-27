/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */
import com.mycompany.sistemagestionalquilervehiculos.ContratoAlquiler;
import com.mycompany.sistemagestionalquilervehiculos.Factura;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorFacturas {

    // Se usa para cargar el contrato completo asociado a cada factura
    private final GestorContratos gestorContratos = new GestorContratos();

    // A - ALTA
    public boolean agregarFactura(Factura factura) {
        String sql = "INSERT INTO factura (numero_serie, fecha_emision, total_factura, id_contrato) VALUES (?, ?, ?, ?)";
        try {
            if (factura == null || factura.getNumeroSerie() == null || factura.getNumeroSerie().trim().isEmpty()) {
                throw new IllegalArgumentException("La factura o su número de serie no son válidos.");
            }
            if (factura.getContrato() == null || factura.getContrato().getIdContrato() <= 0) {
                throw new IllegalArgumentException("La factura debe estar asociada a un contrato válido.");
            }
            if (buscarFacturaPorNumeroSerie(factura.getNumeroSerie()) != null) {
                System.out.println("Error: Ya existe una factura con el número de serie " + factura.getNumeroSerie());
                return false;
            }

            // Si no se definió total o fecha, se toman del contrato y de la fecha actual
            if (factura.getTotalFactura() <= 0) {
                factura.setTotalFactura(factura.calcularTotal());
            }
            if (factura.getFechaEmision() == null) {
                factura.setFechaEmision(new Date());
            }

            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_factura"})) {
                pstmt.setString(1, factura.getNumeroSerie());
                pstmt.setDate(2, new java.sql.Date(factura.getFechaEmision().getTime()));
                pstmt.setDouble(3, factura.getTotalFactura());
                pstmt.setInt(4, factura.getContrato().getIdContrato());

                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        factura.setIdFactura(keys.getInt(1));
                    }
                }
                System.out.println("Factura " + factura.getNumeroSerie() + " registrada exitosamente en la BD.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar factura: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA POR NÚMERO DE SERIE
    public Factura buscarFacturaPorNumeroSerie(String numeroSerie) {
        String sql = "SELECT * FROM factura WHERE numero_serie = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, numeroSerie);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearFactura(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar factura en la BD: " + e.getMessage());
        }
        return null;
    }

    // B - BÚSQUEDA POR ID
    public Factura buscarFacturaPorId(int idFactura) {
        String sql = "SELECT * FROM factura WHERE id_factura = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idFactura);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearFactura(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar factura por ID en la BD: " + e.getMessage());
        }
        return null;
    }

    // B - BÚSQUEDA POR CONTRATO
    public List<Factura> buscarFacturasPorContrato(int idContrato) {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura WHERE id_contrato = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idContrato);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearFactura(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar facturas del contrato: " + e.getMessage());
        }
        return lista;
    }

    // C - CAMBIO
    public boolean modificarFactura(String numeroSerie, Date nuevaFechaEmision, double nuevoTotal) {
        String sql = "UPDATE factura SET fecha_emision = ?, total_factura = ? WHERE numero_serie = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDate(1, new java.sql.Date(nuevaFechaEmision.getTime()));
                pstmt.setDouble(2, nuevoTotal);
                pstmt.setString(3, numeroSerie);

                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Factura " + numeroSerie + " modificada correctamente.");
                    return true;
                }
                System.out.println("Error: Factura no encontrada para modificar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al modificar factura en la BD: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarFactura(String numeroSerie) {
        String sql = "DELETE FROM factura WHERE numero_serie = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, numeroSerie);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Factura " + numeroSerie + " eliminada de la BD.");
                    return true;
                }
                System.out.println("Error: Factura no encontrada para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar factura en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Factura> getListaFacturas() {
        List<Factura> listaFacturas = new ArrayList<>();
        String sql = "SELECT * FROM factura";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    listaFacturas.add(mapearFactura(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de facturas: " + e.getMessage());
        }
        return listaFacturas;
    }

    // Convierte una fila del ResultSet en un objeto Factura (con su contrato cargado)
    private Factura mapearFactura(ResultSet rs) throws SQLException {
        Factura f = new Factura();
        f.setIdFactura(rs.getInt("id_factura"));
        f.setNumeroSerie(rs.getString("numero_serie"));
        f.setFechaEmision(rs.getDate("fecha_emision"));
        f.setTotalFactura(rs.getDouble("total_factura"));

        int idContrato = rs.getInt("id_contrato");
        ContratoAlquiler contrato = gestorContratos.buscarContratoPorId(idContrato);
        if (contrato == null) {
            contrato = new ContratoAlquiler();
            contrato.setIdContrato(idContrato);
        }
        f.setContrato(contrato);
        return f;
    }
}