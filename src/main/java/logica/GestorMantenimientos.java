/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */
import com.mycompany.sistemagestionalquilervehiculos.Mantenimiento;
import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class GestorMantenimientos {
    public void cargarTablaMantenimientos(DefaultTableModel modeloTabla) {
        modeloTabla.setRowCount(0);
        String sql = "SELECT m.id_mantenimiento, s.nombre AS sucursal, " +
                     "v.marca || ' - ' || v.modelo || ' (' || v.placa || ')' AS vehiculo, " +
                     "e.nombre AS mecanico, m.tipo_mantenimiento, m.fecha_ingreso, " +
                     "m.fecha_estimada, m.costo, m.estado " +
                     "FROM mantenimiento m " +
                     "JOIN sucursal s ON m.id_sucursal = s.id_sucursal " +
                     "JOIN vehiculo v ON m.id_vehiculo = v.id_vehiculo " +
                     "JOIN empleado e ON m.id_mecanico = e.id_empleado " +
                     "ORDER BY m.id_mantenimiento DESC";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                
                while (rs.next()) {
                    modeloTabla.addRow(new Object[]{
                        rs.getInt("id_mantenimiento"),
                        rs.getString("sucursal"),
                        rs.getString("vehiculo"),
                        rs.getString("mecanico"),
                        rs.getString("tipo_mantenimiento"),
                        rs.getDate("fecha_ingreso"),
                        rs.getDate("fecha_estimada"),
                        String.format("Q. %.2f", rs.getDouble("costo")),
                        rs.getString("estado")
                    });
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar la tabla de mantenimientos: " + e.getMessage());
        }
    }

    // REGISTRAR CITA / MANTENIMIENTO (Y PASAR VEHÍCULO A 'En mantenimiento')
    public boolean agregarMantenimiento(int idSucursal, int idVehiculo, int idMecanico, String tipoServicio, java.util.Date fIngreso, java.util.Date fEstimada, double costo, String estado) {
        String sqlMantenimiento = "INSERT INTO mantenimiento (id_sucursal, id_vehiculo, id_mecanico, tipo_mantenimiento, fecha_ingreso, fecha_estimada, costo, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlVehiculo = "UPDATE vehiculo SET estado = 'En mantenimiento' WHERE id_vehiculo = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            conn.setAutoCommit(false); // Transacción segura
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlMantenimiento);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlVehiculo)) {
                
                pstmt1.setInt(1, idSucursal);
                pstmt1.setInt(2, idVehiculo);
                pstmt1.setInt(3, idMecanico);
                pstmt1.setString(4, tipoServicio);
                pstmt1.setDate(5, new java.sql.Date(fIngreso.getTime()));
                pstmt1.setDate(6, new java.sql.Date(fEstimada.getTime()));
                pstmt1.setDouble(7, costo);
                pstmt1.setString(8, estado);
                pstmt1.executeUpdate();
                
                pstmt2.setInt(1, idVehiculo);
                pstmt2.executeUpdate();
                
                conn.commit();
                System.out.println("Mantenimiento registrado con éxito.");
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    // FINALIZAR SERVICIO (LIBERA EL VEHÍCULO A 'Disponible')
    public boolean finalizarMantenimiento(int idMantenimiento, int idVehiculo) {
        String sqlUpdateManto = "UPDATE mantenimiento SET estado = 'Finalizado' WHERE id_mantenimiento = ?";
        String sqlVehiculo = "UPDATE vehiculo SET estado = 'Disponible' WHERE id_vehiculo = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            conn.setAutoCommit(false);
            
            try (PreparedStatement pstmt1 = conn.prepareStatement(sqlUpdateManto);
                 PreparedStatement pstmt2 = conn.prepareStatement(sqlVehiculo)) {
                
                pstmt1.setInt(1, idMantenimiento);
                pstmt1.executeUpdate();
                
                pstmt2.setInt(1, idVehiculo);
                pstmt2.executeUpdate();
                
                conn.commit();
                return true;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            System.out.println("Error al finalizar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    // ACTUALIZAR ESTADO DEL MANTENIMIENTO
    public boolean actualizarEstadoMantenimiento(int idMantenimiento, String nuevoEstado) {
        String sql = "UPDATE mantenimiento SET estado = ? WHERE id_mantenimiento = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoEstado);
                pstmt.setInt(2, idMantenimiento);
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar estado: " + e.getMessage());
            return false;
        }
    }
}
