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
/**
 *
 * @author jimmy
 */
public class GestorEmpleados {
    // Método para autenticar al empleado en la Base de Datos
    public Empleado autenticar(String usuario, String clave) {
        String sql = "SELECT * FROM empleado WHERE usuario = ? AND clave = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, usuario);
                pstmt.setString(2, clave);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Empleado emp = new Empleado();
                        emp.setIdEmpleado(rs.getInt("id_empleado"));
                        emp.setNombre(rs.getString("nombre"));
                        emp.setRol(rs.getString("rol"));
                        emp.setUsuario(rs.getString("usuario"));
                        emp.setClave(rs.getString("clave"));
                        return emp; // Retorna el objeto empleado si las credenciales son correctas
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al autenticar empleado en la BD: " + e.getMessage());
        }
        return null; // Retorna null si falla la autenticación
    }
}
