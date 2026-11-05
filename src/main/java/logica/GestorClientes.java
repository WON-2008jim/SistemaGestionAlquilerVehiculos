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
import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class GestorClientes {
    // A - ALTA (Con recuperación de ID autoincremental)
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (nombre, dpi, nit, telefono, correo, num_licencia) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            if (cliente == null || cliente.getDpi() == null || cliente.getDpi().trim().isEmpty()) {
                throw new IllegalArgumentException("El cliente o su DPI no son válidos.");
            }
            if (buscarClientePorDpi(cliente.getDpi()) != null) {
                System.out.println("Error: Ya existe un cliente con el DPI " + cliente.getDpi());
                return false;
            }

            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql, new String[]{"id_cliente"})) {
                pstmt.setString(1, cliente.getNombre());
                pstmt.setString(2, cliente.getDpi());
                pstmt.setString(3, cliente.getNit());
                pstmt.setString(4, cliente.getTelefono());
                pstmt.setString(5, cliente.getCorreo());
                pstmt.setString(6, cliente.getNumLicencia());
                
                pstmt.executeUpdate();

                // Recuperar el ID generado automáticamente por la BD
                try (ResultSet keys = pstmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        cliente.setIdCliente(keys.getInt(1));
                    }
                }

                System.out.println("Cliente " + cliente.getNombre() + " registrado exitosamente en la BD.");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Excepción al registrar cliente: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA POR DPI
    public Cliente buscarClientePorDpi(String dpi) {
        String sql = "SELECT * FROM cliente WHERE dpi = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, dpi);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearCliente(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente en la BD: " + e.getMessage());
        }
        return null;
    }

    // B - BÚSQUEDA POR ID
    public Cliente buscarClientePorId(int idCliente) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setInt(1, idCliente);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        return mapearCliente(rs);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar cliente por ID en la BD: " + e.getMessage());
        }
        return null;
    }

    // C - CAMBIO
    public boolean modificarCliente(String dpi, String nuevoTelefono, String nuevoCorreo) {
        String sql = "UPDATE cliente SET telefono = ?, correo = ? WHERE dpi = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, nuevoTelefono);
                pstmt.setString(2, nuevoCorreo);
                pstmt.setString(3, dpi);
                
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Cliente con DPI " + dpi + " modificado correctamente.");
                    return true;
                }
                System.out.println("Error: Cliente no encontrado para modificar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al modificar cliente en la BD: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA
    public boolean eliminarCliente(String dpi) {
        String sql = "DELETE FROM cliente WHERE dpi = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, dpi);
                int filas = pstmt.executeUpdate();
                if (filas > 0) {
                    System.out.println("Cliente con DPI " + dpi + " eliminado de la BD.");
                    return true;
                }
                System.out.println("Error: Cliente no encontrado para eliminar.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Excepción al eliminar cliente en la BD: " + e.getMessage());
            return false;
        }
    }

    // LISTAR
    public List<Cliente> getListaClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {
                
                while (rs.next()) {
                    listaClientes.add(mapearCliente(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de clientes: " + e.getMessage());
        }
        return listaClientes;
    }

    // MAPEO PRIVADO (Principio DRY - Don't Repeat Yourself)
    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setIdCliente(rs.getInt("id_cliente"));
        c.setNombre(rs.getString("nombre"));
        c.setDpi(rs.getString("dpi"));
        c.setNit(rs.getString("nit"));
        c.setTelefono(rs.getString("telefono"));
        c.setCorreo(rs.getString("correo"));
        c.setNumLicencia(rs.getString("num_licencia"));
        return c;
    }
    
    public void cargarTablaClientes(javax.swing.table.DefaultTableModel modelo) {
        modelo.setRowCount(0); // Limpiar filas anteriores
        List<Cliente> lista = getListaClientes();
        for (Cliente c : lista) {
            Object[] fila = {
                c.getIdCliente(),
                c.getNombre(),
                c.getDpi(),
                c.getNit(),
                c.getTelefono(),
                c.getCorreo(),
                c.getNumLicencia()
            };
            modelo.addRow(fila);
        }
    }

    // Método para actualizar todos los campos del cliente desde la interfaz
    public boolean actualizarClienteCompleto(Cliente c) {
        String sql = "UPDATE cliente SET nombre = ?, nit = ?, telefono = ?, correo = ?, num_licencia = ? WHERE dpi = ?";
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, c.getNombre());
                pstmt.setString(2, c.getNit());
                pstmt.setString(3, c.getTelefono());
                pstmt.setString(4, c.getCorreo());
                pstmt.setString(5, c.getNumLicencia());
                pstmt.setString(6, c.getDpi());
            
                return pstmt.executeUpdate() > 0;
                }
            } catch (SQLException e) {
                System.out.println("Excepción al actualizar cliente: " + e.getMessage());
                return false;
            }
        }
    }
