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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class GestorContratos {
    
    public void cargarTablaContratos(DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de cargar
        String sql = "SELECT c.id_contrato, cli.nombre AS cliente, v.marca || ' - ' || v.modelo || ' (' || v.placa || ')' AS vehiculo, " +
                     "e.nombre AS empleado, c.fecha_inicio, c.fecha_fin, c.dias_alquiler, c.total, c.estado " +
                     "FROM contrato_alquiler c " +
                     "JOIN cliente cli ON c.id_cliente = cli.id_cliente " +
                     "JOIN vehiculo v ON c.id_vehiculo = v.id_vehiculo " +
                     "JOIN empleado e ON c.id_empleado = e.id_empleado";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("id_contrato"),
                        rs.getString("cliente"),
                        rs.getString("vehiculo"),
                        rs.getString("empleado"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getInt("dias_alquiler"),
                        String.format("%.2f", rs.getDouble("total")),
                        rs.getString("estado")
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar la tabla de contratos: " + e.getMessage());
        }
    }
    public int[] obtenerEstadisticasContratos() {
        int contratosActivos = 0;
        double ingresosTotales = 0.0;
        int proximasFinalizaciones = 0;
        
        String sql = "SELECT estado, total, fecha_fin FROM contrato_alquiler";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                java.time.LocalDate hoy = java.time.LocalDate.now();
                
                while (rs.next()) {
                    String estado = rs.getString("estado");
                    double total = rs.getDouble("total");
                    java.sql.Date fechaFin = rs.getDate("fecha_fin");
                    
                    if ("Activo".equalsIgnoreCase(estado) || "Reservado".equalsIgnoreCase(estado)) {
                        contratosActivos++;
                        ingresosTotales += total;
                    }
                    
                    if (fechaFin != null && ("Activo".equalsIgnoreCase(estado))) {
                        java.time.LocalDate fFin = fechaFin.toLocalDate();
                        long diasRestantes = java.time.temporal.ChronoUnit.DAYS.between(hoy, fFin);
                        if (diasRestantes >= 0 && diasRestantes <= 3) {
                            proximasFinalizaciones++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener estadísticas: " + e.getMessage());
        }
        
        return new int[] { contratosActivos, (int) ingresosTotales, proximasFinalizaciones };
    }

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
                String sqlUpdateVehiculo = "UPDATE vehiculo SET estado = 'Ocupado' WHERE id_vehiculo = ?";
                try (PreparedStatement pstmtVeh = conn.prepareStatement(sqlUpdateVehiculo)) {
                    pstmtVeh.setInt(1, contrato.getVehiculo().getIdVehiculo());
                    pstmtVeh.executeUpdate();
                }

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
        String sqlContrato = "UPDATE contrato_alquiler SET estado = ? WHERE id_contrato = ?";
        String sqlVehiculo = "UPDATE vehiculo SET estado = 'Disponible' WHERE id_vehiculo = (SELECT id_vehiculo FROM contrato_alquiler WHERE id_contrato = ?)";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            conn.setAutoCommit(false); // Transacción segura
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlContrato);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlVehiculo)) {
                
                pstmt1.setString(1, nuevoEstado);
                pstmt1.setInt(2, idContrato);
                pstmt1.executeUpdate();
                
                if ("Finalizado".equalsIgnoreCase(nuevoEstado)) {
                    pstmt2.setInt(1, idContrato);
                    pstmt2.executeUpdate();
                }
                
                conn.commit();
                System.out.println("Contrato " + idContrato + " actualizado a " + nuevoEstado);
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al cambiar estado del contrato: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarContrato(int idContrato) {
        String sqlVehiculo = "UPDATE vehiculo SET estado = 'Disponible' WHERE id_vehiculo = (SELECT id_vehiculo FROM contrato_alquiler WHERE id_contrato = ?)";
        String sqlDelete = "DELETE FROM contrato_alquiler WHERE id_contrato = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlVehiculo);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlDelete)) {
                
                pstmt1.setInt(1, idContrato);
                pstmt1.executeUpdate();
                
                pstmt2.setInt(1, idContrato);
                int filas = pstmt2.executeUpdate();
                
                conn.commit();
                if (filas > 0) {
                    System.out.println("Contrato " + idContrato + " eliminado y vehículo liberado.");
                    return true;
                }
                return false;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar contrato: " + e.getMessage());
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
    public boolean renovarContrato(int idContrato, Date nuevaFechaFin, int nuevosDias, double nuevoTotal) {
    String sql = "UPDATE contrato_alquiler SET fecha_fin = ?, dias_alquiler = ?, total = ?, estado = 'Activo' WHERE id_contrato = ?";
    try {
        Connection conn = ConexionBD.getInstancia().getConexion();
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, new java.sql.Date(nuevaFechaFin.getTime()));
            pstmt.setInt(2, nuevosDias);
            pstmt.setDouble(3, nuevoTotal);
            pstmt.setInt(4, idContrato);

            int filas = pstmt.executeUpdate();
            if (filas > 0) {
                System.out.println("Contrato " + idContrato + " renovado exitosamente.");
                return true;
            }
            return false;
        }
    } catch (SQLException e) {
        System.out.println("Excepción al renovar contrato: " + e.getMessage());
        return false;
    }
}
}