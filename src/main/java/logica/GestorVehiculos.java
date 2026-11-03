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
import com.mycompany.sistemagestionalquilervehiculos.CategoriaVehiculo;
import com.mycompany.sistemagestionalquilervehiculos.Sucursal;
import java.util.ArrayList;
import java.util.List;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GestorVehiculos {
    public boolean agregarVehiculo(Vehiculo vehiculo) {
        String sql = "INSERT INTO vehiculo (marca, modelo, vin, placa, anio, kilometraje, tarifa_diaria, estado, id_categoria, id_sucursal) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            if (vehiculo == null) {
                throw new IllegalArgumentException("El vehículo no es válido.");
            }
            
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_vehiculo"})) {
                pstmt.setString(1, vehiculo.getMarca());
                pstmt.setString(2, vehiculo.getModelo());
                pstmt.setString(3, vehiculo.getVin());
                pstmt.setString(4, vehiculo.getPlaca());
                pstmt.setInt(5, vehiculo.getAnio());
                pstmt.setDouble(6, vehiculo.getKilometraje());
                pstmt.setDouble(7, vehiculo.getTarifaDiaria());
                pstmt.setString(8, vehiculo.getEstado() == null ? "Disponible" : vehiculo.getEstado());
                pstmt.setInt(9, vehiculo.getCategoria() != null ? vehiculo.getCategoria().getIdCategoria() : 1);
                pstmt.setInt(10, vehiculo.getSucursal() != null ? vehiculo.getSucursal().getIdSucursal() : 1);
                
                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        vehiculo.setIdVehiculo(keys.getInt(1));
                    }
                }
                System.out.println("Vehículo registrado exitosamente en la BD.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar vehículo: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA POR PLACA
    public Vehiculo buscarVehiculoPorPlaca(String placa) {
        String sql = "SELECT * FROM vehiculo WHERE placa = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, placa);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearVehiculo(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el vehículo en la BD: " + e.getMessage());
        }
        return null;
    }

    // B - BÚSQUEDA POR ID
    public Vehiculo buscarVehiculoPorId(int idVehiculo) {
        String sql = "SELECT * FROM vehiculo WHERE id_vehiculo = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idVehiculo);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearVehiculo(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar el vehículo por ID: " + e.getMessage());
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
                    System.out.println("Vehículo actualizado correctamente.");
                    return true;
                }
                System.out.println("Error: Vehículo no encontrado para modificar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Error al modificar vehículo: " + e.getMessage());
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
                    System.out.println("Vehículo de placa " + placa + " eliminado de la BD.");
                    return true;
                }
                System.out.println("Error: Vehículo no encontrado para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar vehículo en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Vehiculo> getListaVehiculos() {
        List<Vehiculo> listaVehiculos = new ArrayList<>();
        String sql = "SELECT * FROM vehiculo";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            // CORREGIDO: Se cambia la coma (,) por punto y coma (;) entre PreparedStatement y ResultSet
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    listaVehiculos.add(mapearVehiculo(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de vehículos: " + e.getMessage());
        }
        return listaVehiculos;
    }

    // MAPEO PRIVADO (DRY)
    private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
        Vehiculo v = new Vehiculo();
        v.setIdVehiculo(rs.getInt("id_vehiculo"));
        v.setMarca(rs.getString("marca"));
        v.setModelo(rs.getString("modelo"));
        v.setVin(rs.getString("vin"));
        v.setPlaca(rs.getString("placa"));
        v.setAnio(rs.getInt("anio"));
        v.setKilometraje(rs.getDouble("kilometraje"));
        v.setTarifaDiaria(rs.getDouble("tarifa_diaria"));
        v.setEstado(rs.getString("estado"));

        CategoriaVehiculo cat = new CategoriaVehiculo();
        cat.setIdCategoria(rs.getInt("id_categoria"));
        v.setCategoria(cat);

        Sucursal suc = new Sucursal();
        suc.setIdSucursal(rs.getInt("id_sucursal"));
        v.setSucursal(suc);

        return v;
    }
}
    