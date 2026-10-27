/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package logica;

/**
 *
 * @author jimmy
 */
import com.mycompany.sistemagestionalquilervehiculos.Cliente;
import com.mycompany.sistemagestionalquilervehiculos.ContratoAlquiler;
import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GestorContratos {

    // A - ALTA
    public boolean agregarContrato(ContratoAlquiler contrato) {
        String sql = "INSERT INTO contrato_alquiler (id_cliente, id_vehiculo, id_empleado, fecha_inicio, fecha_fin, dias_alquiler, monto_base, total, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (contrato == null || contrato.getCliente() == null
                    || contrato.getVehiculo() == null || contrato.getEmpleado() == null) {
                throw new IllegalArgumentException("El contrato, el cliente, el vehículo o el empleado no son válidos.");
            }
            if (contrato.getFechaInicio() == null || contrato.getFechaFin() == null
                    || contrato.getFechaFin().before(contrato.getFechaInicio())) {
                throw new IllegalArgumentException("Las fechas del contrato no son válidas.");
            }
            if (contrato.getDiasAlquiler() <= 0 || contrato.getMontoBase() <= 0) {
                throw new IllegalArgumentException("Los días de alquiler y el monto base deben ser mayores a 0.");
            }

            // Si el total no fue calculado, se toma del monto base
            if (contrato.getTotal() <= 0) {
                contrato.setTotal(contrato.calcularTotal());
            }

            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_contrato"})) {
                pstmt.setInt(1, contrato.getCliente().getIdCliente());
                pstmt.setInt(2, contrato.getVehiculo().getIdVehiculo());
                pstmt.setInt(3, contrato.getEmpleado().getIdEmpleado());
                pstmt.setDate(4, new java.sql.Date(contrato.getFechaInicio().getTime()));
                pstmt.setDate(5, new java.sql.Date(contrato.getFechaFin().getTime()));
                pstmt.setInt(6, contrato.getDiasAlquiler());
                pstmt.setDouble(7, contrato.getMontoBase());
                pstmt.setDouble(8, contrato.getTotal());
                pstmt.setString(9, contrato.getEstado() == null ? "Activo" : contrato.getEstado());

                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        contrato.setIdContrato(keys.getInt(1));
                    }
                }
                System.out.println("Contrato " + contrato.getIdContrato() + " registrado exitosamente en la BD.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar contrato: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA
    public ContratoAlquiler buscarContratoPorId(int idContrato) {
        String sql = "SELECT * FROM contrato_alquiler WHERE id_contrato = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idContrato);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearContrato(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar contrato en la BD: " + e.getMessage());
        }
        return null;
    }

    // B - BÚSQUEDA POR CLIENTE
    public List<ContratoAlquiler> buscarContratosPorCliente(int idCliente) {
        List<ContratoAlquiler> lista = new ArrayList<>();
        String sql = "SELECT * FROM contrato_alquiler WHERE id_cliente = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idCliente);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        lista.add(mapearContrato(rs));
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar contratos del cliente: " + e.getMessage());
        }
        return lista;
    }

    // C - CAMBIO
    public boolean modificarContrato(int idContrato, Date nuevaFechaFin, int nuevosDias, String nuevoEstado) {
        String sql = "UPDATE contrato_alquiler SET fecha_fin = ?, dias_alquiler = ?, estado = ? WHERE id_contrato = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setDate(1, new java.sql.Date(nuevaFechaFin.getTime()));
                pstmt.setInt(2, nuevosDias);
                pstmt.setString(3, nuevoEstado);
                pstmt.setInt(4, idContrato);

                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Contrato " + idContrato + " modificado correctamente.");
                    return true;
                }
                System.out.println("Error: Contrato no encontrado para modificar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al modificar contrato en la BD: " + e.getMessage());
            return false;
        }
    }

    // C - CAMBIO DE ESTADO (por ejemplo, finalizar el contrato)
    public boolean cambiarEstadoContrato(int idContrato, String nuevoEstado) {
        String sql = "UPDATE contrato_alquiler SET estado = ? WHERE id_contrato = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoEstado);
                pstmt.setInt(2, idContrato);

                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Estado del contrato " + idContrato + " cambiado a " + nuevoEstado + ".");
                    return true;
                }
                System.out.println("Error: Contrato no encontrado para cambiar estado.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al cambiar estado del contrato: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarContrato(int idContrato) {
        String sql = "DELETE FROM contrato_alquiler WHERE id_contrato = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idContrato);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Contrato " + idContrato + " eliminado de la BD.");
                    return true;
                }
                System.out.println("Error: Contrato no encontrado para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar contrato en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<ContratoAlquiler> getListaContratos() {
        List<ContratoAlquiler> listaContratos = new ArrayList<>();
        String sql = "SELECT * FROM contrato_alquiler";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    listaContratos.add(mapearContrato(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de contratos: " + e.getMessage());
        }
        return listaContratos;
    }

    // Convierte una fila del ResultSet en un ContratoAlquiler.
    // Cliente, Vehiculo y Empleado se crean solo con su id (relación por llave foránea).
    private ContratoAlquiler mapearContrato(ResultSet rs) throws SQLException {
        ContratoAlquiler c = new ContratoAlquiler();
        c.setIdContrato(rs.getInt("id_contrato"));
        c.setFechaInicio(rs.getDate("fecha_inicio"));
        c.setFechaFin(rs.getDate("fecha_fin"));
        c.setDiasAlquiler(rs.getInt("dias_alquiler"));
        c.setMontoBase(rs.getDouble("monto_base"));
        c.setTotal(rs.getDouble("total"));
        c.setEstado(rs.getString("estado"));

        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        c.setCliente(cliente);

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setIdVehiculo(rs.getInt("id_vehiculo"));
        c.setVehiculo(vehiculo);

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(rs.getInt("id_empleado"));
        c.setEmpleado(empleado);

        return c;
    }
}