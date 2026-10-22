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
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorMantenimientos {

 public boolean agregarManteniminto(Mantenimiento mantenimiento) {
        String sql = "INSERT INTO mantenimiento (fecha, tipo_mantenimiento, descripcion, costo, id_vehiculo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                if (mantenimiento == null) {
                    throw new IllegalArgumentException("Error en agregar datos para el mantenimiento");
                }
               
                
                
                pstmt.setDate(1, new java.sql.Date(mantenimiento.getFecha().getTime()));
                pstmt.setString(2, mantenimiento.getTipoMantenimiento());
                pstmt.setString(3, mantenimiento.getDescripcion());
                pstmt.setDouble(4, mantenimiento.getCosto());
                pstmt.setInt(5, mantenimiento.getVehiculo() != null ? mantenimiento.getVehiculo().getIdVehiculo() : 1);
                
                
                pstmt.executeUpdate();
                System.out.println("Mantenimiento registrado exitosamente en la BD." );
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA
    public Mantenimiento buscarMantenimiento(int idMantenimiento) {
        String sql = "SELECT * FROM mantenimiento WHERE id_mantenimiento = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idMantenimiento);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Mantenimiento m = new Mantenimiento();
                        m.setIdMantenimiento(rs.getInt("id_mantenimiento"));
                        m.setFecha(rs.getDate("fecha"));
                        m.setTipoMantenimiento(rs.getString("tipo_mantnimiento"));
                        m.setDescripcion(rs.getString("descripcion"));
                        m.setCosto(rs.getDouble("costo"));
                        
                   
                        return m;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el mantenimiento en la BD: " + e.getMessage());
        }
        return null;
    }

    // C - CAMBIO
    public boolean modificarMantenimiento(int idMantenimiento, String nuevaDescripcion, Double nuevoCosto) {
        String sql = "UPDATE mantenimiento SET descripcion = ?, costo = ? WHERE id_mantenimiento = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevaDescripcion);
                pstmt.setDouble(2, nuevoCosto);
                pstmt.setInt(3, idMantenimiento);
                
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Mantenimiento actualizado correctamente.");
                    return true;
                }
               
            }
        } catch (SQLException e) {
            System.out.println("Error: al modificar mantenimiento " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarMantenimiento(int idMantenimiento) {
        String sql = "DELETE FROM mantenimiento WHERE id_mantenimiento = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idMantenimiento);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Mantenimiento No: " + idMantenimiento + " eliminado de la BD.");
                    return true;
                }
                System.out.println("Error: al eliminar mantenimiento no encontrado para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar mantenimiento en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Mantenimiento> getListaMantenimiento() {
        List<Mantenimiento> listaMantenimiento = new ArrayList<>();
        String sql = "SELECT * FROM mantenimiento";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {                    Mantenimiento m = new Mantenimiento();
                    m.setIdMantenimiento(rs.getInt("id_mantenimiento"));
                    m.setFecha(rs.getDate("fecha"));
                    m.setTipoMantenimiento(rs.getString("tipo_mantenimiento"));
                    m.setDescripcion(rs.getString("descripcion"));
                    m.setCosto(rs.getDouble("costo"));
                    listaMantenimiento.add(m);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de mantenimiento: " + e.getMessage());
        }
        return listaMantenimiento;
    }
}
