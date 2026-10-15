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
import java.util.ArrayList;
import java.util.List;
public class GestorMantenimientos {
    private List<Mantenimiento> listaMantenimientos = new ArrayList<>();

    // A - ALTA (Registrar mantenimiento)
    public boolean agregarMantenimiento(Mantenimiento mantenimiento) {
        try {
            if (mantenimiento == null) {
                throw new IllegalArgumentException("El objeto mantenimiento no puede ser nulo.");
            }
            listaMantenimientos.add(mantenimiento);
            System.out.println("Mantenimiento registrado en memoria exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Excepción al registrar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA
    public Mantenimiento buscarMantenimiento(int idMantenimiento) {
        try {
            for (Mantenimiento m : listaMantenimientos) {
                if (m.getIdMantenimiento() == idMantenimiento) {
                    return m;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al buscar mantenimiento: " + e.getMessage());
        }
        return null;
    }

    // C - CAMBIO (Modificar costo o descripción)
    public boolean modificarMantenimiento(int idMantenimiento, String nuevaDesc, double nuevoCosto) {
        // Nota: puedes ajustar los parámetros según necesites modificar
        try {
            Mantenimiento m = buscarMantenimiento(idMantenimiento);
            if (m != null) {
                m.setDescripcion(nuevaDesc);
                System.out.println("Mantenimiento actualizado correctamente.");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al modificar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA (Eliminar registro de mantenimiento)
    public boolean eliminarMantenimiento(int idMantenimiento) {
        try {
            Mantenimiento m = buscarMantenimiento(idMantenimiento);
            if (m != null) {
                listaMantenimientos.remove(m);
                System.out.println("Mantenimiento N° " + idMantenimiento + " eliminado de la lista.");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al eliminar mantenimiento: " + e.getMessage());
            return false;
        }
    }

    public List<Mantenimiento> getListaMantenimientos() {
        return listaMantenimientos;
    }
}
