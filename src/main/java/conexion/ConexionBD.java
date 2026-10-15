/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

/**
 *
 * @author jimmy
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConexionBD {
    private static ConexionBD instancia = null;
    private Connection conexion = null;
    
    // Parámetros de conexión a la base de datos
    private final String URL = "jdbc:postgresql://localhost:5432/alquilerVehiculos"; 
    private final String USUARIO = "postgres"; 
    private final String CONTRASENA = "depuradores";
    
    // Constructor privado
    private ConexionBD() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("¡Conexión establecida exitosamente con la base de datos alquilerVehiculos!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver JDBC de PostgreSQL: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de SQL al conectar con la base de datos: " + e.getMessage());
        }
    }
    
    // Método público para obtener la única instancia
    public static ConexionBD getInstancia() {
        try {
            if (instancia == null || instancia.conexion == null || instancia.conexion.isClosed()) {
                instancia = new ConexionBD();
            }
        } catch (SQLException e) {
            System.out.println("Error al verificar el estado de la conexión: " + e.getMessage());
        }
        return instancia;
    }
    
    // Método para obtener el objeto Connection para las consultas SQL
    public Connection getConexion() {
        return conexion;
    }
    
    // Método para cerrar la conexión de forma segura
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                instancia = null;
                System.out.println("Conexión cerrada correctamente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
