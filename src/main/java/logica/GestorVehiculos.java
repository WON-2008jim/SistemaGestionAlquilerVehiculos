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
public class GestorVehiculos {
    // Almacenamiento en memoria requerido para el Entregable 2[cite: 10]
    private List<Vehiculo> listaVehiculos = new ArrayList<>();

    // A - ALTA (Agregar vehículo)
    public boolean agregarVehiculo(Vehiculo vehiculo) {
        try {
            if (vehiculo == null || vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
                throw new IllegalArgumentException("El vehículo no es válido o su placa está vacía.");
            }
            // Verificar si ya existe la placa
            if (buscarVehiculo(vehiculo.getPlaca()) != null) {
                System.out.println("Error: Ya existe un vehículo registrado con la placa " + vehiculo.getPlaca());
                return false;
            }
            listaVehiculos.add(vehiculo);
            System.out.println("Vehículo con placa " + vehiculo.getPlaca() + " agregado exitosamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Excepción controlada al agregar vehículo: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA (Buscar por placa)
    public Vehiculo buscarVehiculo(String placa) {
        try {
            for (Vehiculo v : listaVehiculos) {
                if (v.getPlaca().equalsIgnoreCase(placa)) {
                    return v;
                }
            }
        } catch (Exception e) {
            System.out.println("Error durante la búsqueda: " + e.getMessage());
        }
        return null;
    }

    // C - CAMBIO (Modificar datos del vehículo)
    public boolean modificarVehiculo(String placa, String nuevoEstado, double nuevaTarifa) {
        try {
            Vehiculo v = buscarVehiculo(placa);
            if (v != null) {
                v.setEstado(nuevoEstado);
                v.setTarifaDiaria(nuevaTarifa);
                System.out.println("Vehículo con placa " + placa + " modificado correctamente.");
                return true;
            }
            System.out.println("Error: No se encontró el vehículo para modificar.");
            return false;
        } catch (Exception e) {
            System.out.println("Excepción controlada al modificar: " + e.getMessage());
            return false;
        }
    }

    // D - BAJA (Eliminar vehículo)
    public boolean eliminarVehiculo(String placa) {
        try {
            Vehiculo v = buscarVehiculo(placa);
            if (v != null) {
                listaVehiculos.remove(v);
                System.out.println("Vehículo con placa " + placa + " eliminado de la memoria.");
                return true;
            }
            System.out.println("Error: Vehículo no encontrado para eliminar.");
            return false;
        } catch (Exception e) {
            System.out.println("Excepción controlada al eliminar: " + e.getMessage());
            return false;
        }
    }

    public List<Vehiculo> getListaVehiculos() {
        return listaVehiculos;
    }
}
