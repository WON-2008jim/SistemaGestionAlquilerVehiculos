/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */

import com.mycompany.sistemagestionalquilervehiculos.Vehiculo;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class GestorVehiculos {

    public boolean agregarVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO Vehiculo (marca, modelo, vin, placa, anio, kilometraje, tarifa_diaria, estado, id_categoria, id_sucursal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                
                if (vehiculo == null) {
                    throw new IllegalArgumentException("Error en agregar datos para el vehiculo");
                }
               
                
                
                pstmt.setString(1, vehiculo.getMarca());
                pstmt.setString(2, vehiculo.getModelo());
                pstmt.setString(3, vehiculo.getVin());
                pstmt.setString(4, vehiculo.getPlaca());
                pstmt.setInt(5, vehiculo.getAnio());
                pstmt.setDouble(6, vehiculo.getKilometraje());
                pstmt.setDouble(7, vehiculo.getTarifaDiaria());
                pstmt.setString(8, vehiculo.getEstado());
                pstmt.setInt(9, vehiculo.getCategoria() != null ? vehiculo.getCategoria().getIdCategoria() : 1);
                pstmt.setInt(10, vehiculo.getSucursal() != null ? vehiculo.getSucursal().getIdSucursal() : 1);
                
                pstmt.executeUpdate();
                System.out.println("Vehiculo registrado exitosamente en la BD." );
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar vehiculo: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA
    public Vehiculo buscarVehiculo(String placa) {
        String sql = "SELECT * FROM vehiculo WHERE id_vehiculo = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, placa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Vehiculo v = new Vehiculo();
                        v.setIdVehiculo(rs.getInt("id_vehiculo"));
                        v.setModelo(rs.getString("Modelo"));
                        v.setVin(rs.getString("Vin"));
                        v.setPlaca(rs.getString("Placa"));
                        v.setAnio(rs.getInt("Anio"));
                        v.setKilometraje(rs.getDouble("Kilometraje"));
                        v.setTarifaDiaria(rs.getDouble("Tarifa_diaria"));
                        v.setEstado(rs.getString("Estado"));
                        
                   
                        return v;
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el vehiculo en la BD: " + e.getMessage());
        }
        return null;
    }

    // C - CAMBIO
    public boolean modificarVehiculo(String placa, String nuevoEstado, Double nuevaTarifaDiaria) {
        String sql = "UPDATE vehiculo SET estado = ?, tarifa_diaria = ? WHERE placa = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoEstado);
                pstmt.setDouble(2, nuevaTarifaDiaria);
                pstmt.setString(3, placa);
                
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Vehiculo actualizado correctamente.");
                    return true;
                }
               
            }
        } catch (SQLException e) {
            System.out.println("Error: al modificar vehiculo " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarVehiculo(String placa) {
        String sql = "DELETE FROM vehiculo WHERE placa = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, placa);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Vehiculo de placa No: " + placa + " eliminado de la BD.");
                    return true;
                }
                System.out.println("Error: al eliminar vehiculo no encontrado para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar vehiculo en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Vehiculo> getListaVehiculo() {
        List<Vehiculo> listaVehiculo = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    Vehiculo v = new Vehiculo();
                   v.setIdVehiculo(rs.getInt("id_vehiculo"));
                   v.setModelo(rs.getString("Modelo"));
                   v.setVin(rs.getString("Vin"));
                   v.setPlaca(rs.getString("Placa"));
                   v.setAnio(rs.getInt("Anio"));
                   v.setKilometraje(rs.getDouble("Kilometraje"));
                   v.setTarifaDiaria(rs.getDouble("Tarifa_diaria"));
                   v.setEstado(rs.getString("Estado"));
                    listaVehiculo.add(v);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de vehiculo: " + e.getMessage());
        }
        return listaVehiculo;
    }
}
    