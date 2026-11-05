/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */

import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GestorSucursales {
    public boolean agregarSucursal(Sucursal sucursal) {
        String sql = "INSERT INTO sucursal (nombre, direccion, telefono) VALUES (?, ?, ?)";
        try {
            if (sucursal == null) {
                throw new IllegalArgumentException("La sucursal no es válida.");
            }
            
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_sucursal"})) {
                pstmt.setString(1, sucursal.getNombre());
                pstmt.setString(2, sucursal.getDireccion());
                pstmt.setString(3, sucursal.getTelefono());
                
                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        sucursal.setIdSucursal(keys.getInt(1));
                    }
                }
                System.out.println("Sucursal registrada exitosamente en la BD.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar sucursal: " + e.getMessage());
            return false;
        }
    }

    // BUSCAR SUCURSAL POR ID
    public Sucursal buscarSucursalPorId(int idSucursal) {
        String sql = "SELECT * FROM sucursal WHERE id_sucursal = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idSucursal);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearSucursal(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar la sucursal por ID: " + e.getMessage());
        }
        return null;
    }

    // MODIFICAR SUCURSAL COMPLETA
    public boolean actualizarSucursal(Sucursal s) {
        String sql = "UPDATE sucursal SET nombre = ?, direccion = ?, telefono = ? WHERE id_sucursal = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, s.getNombre());
                pstmt.setString(2, s.getDireccion());
                pstmt.setString(3, s.getTelefono());
                pstmt.setInt(4, s.getIdSucursal());
                
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Sucursal actualizada correctamente.");
                    return true;
                }
                System.out.println("Error: Sucursal no encontrada para modificar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al actualizar sucursal: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR SUCURSAL POR ID
    public boolean eliminarSucursal(int idSucursal) {
        String sql = "DELETE FROM sucursal WHERE id_sucursal = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idSucursal);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Sucursal con ID " + idSucursal + " eliminada de la BD.");
                    return true;
                }
                System.out.println("Error: Sucursal no encontrada para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar sucursal en la BD (puede tener vehículos o contratos asociados): " + e.getMessage());
            return false;
        }
    }

    // LISTAR SUCURSALES
    public List<Sucursal> getListaSucursales() {
        List<Sucursal> listaSucursales = new ArrayList<>();
        String sql = "SELECT * FROM sucursal ORDER BY id_sucursal ASC";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    listaSucursales.add(mapearSucursal(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de sucursales: " + e.getMessage());
        }
        return listaSucursales;
    }

    // MAPEO PRIVADO (DRY)
    private Sucursal mapearSucursal(ResultSet rs) throws SQLException {
        Sucursal s = new Sucursal();
        s.setIdSucursal(rs.getInt("id_sucursal"));
        s.setNombre(rs.getString("nombre"));
        s.setDireccion(rs.getString("direccion"));
        s.setTelefono(rs.getString("telefono"));
        return s;
    }

    // CARGAR TABLA EN LA VISTA SWING
    public void cargarTablaSucursales(javax.swing.table.DefaultTableModel modelo) {
        modelo.setRowCount(0); // Limpiar filas anteriores
        List<Sucursal> lista = getListaSucursales();
        for (Sucursal s : lista) {
            Object[] fila = {
                s.getIdSucursal(),
                s.getNombre(),
                s.getDireccion(),
                s.getTelefono()
            };
            modelo.addRow(fila);
        }
    }
}
