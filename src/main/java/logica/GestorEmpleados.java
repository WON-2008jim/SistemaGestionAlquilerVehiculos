/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;
import com.mycompany.sistemagestionalquilervehiculos.Empleado;
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author jimmy
 */
public class GestorEmpleados {
    public Empleado autenticar(String usuario, String clave) {
        String sql = "SELECT * FROM empleado WHERE usuario = ? AND clave = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usuario);
                pstmt.setString(2, clave);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearEmpleado(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar empleado en la BD: " + e.getMessage());
        }
        return null; 
    }

    // AGREGAR EMPLEADO
    public boolean agregarEmpleado(Empleado emp) {
        String sql = "INSERT INTO empleado (nombre, rol, telefono, usuario, clave, id_sucursal) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_empleado"})) {
                pstmt.setString(1, emp.getNombre());
                pstmt.setString(2, emp.getRol());
                pstmt.setString(3, emp.getTelefono());
                pstmt.setString(4, emp.getUsuario());
                pstmt.setString(5, emp.getClave());
                pstmt.setInt(6, emp.getIdSucursal());
                
                pstmt.executeUpdate();

                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        emp.setIdEmpleado(keys.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error al registrar empleado: " + e.getMessage());
            return false;
        }
    }

    // ACTUALIZAR EMPLEADO
    public boolean actualizarEmpleado(Empleado emp) {
        String sql = "UPDATE empleado SET nombre = ?, rol = ?, telefono = ?, usuario = ?, clave = ?, id_sucursal = ? WHERE id_empleado = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, emp.getNombre());
                pstmt.setString(2, emp.getRol());
                pstmt.setString(3, emp.getTelefono());
                pstmt.setString(4, emp.getUsuario());
                pstmt.setString(5, emp.getClave());
                pstmt.setInt(6, emp.getIdSucursal());
                pstmt.setInt(7, emp.getIdEmpleado());
                
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar empleado: " + e.getMessage());
            return false;
        }
    }

    // ELIMINAR EMPLEADO
    public boolean eliminarEmpleado(int idEmpleado) {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idEmpleado);
                return pstmt.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al eliminar empleado: " + e.getMessage());
            return false;
        }
    }

    // LISTAR EMPLEADOS
    public List<Empleado> getListaEmpleados() {
        List<Empleado> lista = new ArrayList<>();
        String sql = "SELECT * FROM empleado ORDER BY id_empleado ASC";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapearEmpleado(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
        }
        return lista;
    }

    // MAPEO PRIVADO (DRY)
    private Empleado mapearEmpleado(ResultSet rs) throws SQLException {
        Empleado e = new Empleado();
        e.setIdEmpleado(rs.getInt("id_empleado"));
        e.setNombre(rs.getString("nombre"));
        e.setRol(rs.getString("rol"));
        e.setTelefono(rs.getString("telefono"));
        e.setUsuario(rs.getString("usuario"));
        e.setClave(rs.getString("clave"));
        e.setIdSucursal(rs.getInt("id_sucursal"));
        return e;
    }

    // CARGAR TABLA EN SWING
    public void cargarTablaEmpleados(javax.swing.table.DefaultTableModel modelo) {
        modelo.setRowCount(0);
        List<Empleado> lista = getListaEmpleados();
        for (Empleado e : lista) {
            Object[] fila = {
                e.getIdEmpleado(),
                e.getNombre(),
                e.getRol(),
                e.getTelefono(),
                e.getUsuario(),
                e.getIdSucursal()
            };
            modelo.addRow(fila);
        }
    }
}
