/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */
import com.mycompany.sistemagestionalquilervehiculos.Factura;
import java.util.ArrayList;
import java.util.List;
public class GestorFacturas {
    private List<Factura> listaFacturas = new ArrayList<>();

    // A - ALTA
    public boolean agregarFactura(Factura factura) {
        try {
            if (factura == null) {
                throw new IllegalArgumentException("La factura no puede ser nula.");
            }
            listaFacturas.add(factura);
            System.out.println("Factura N° " + factura.getNumeroSerie() + " agregada correctamente.");
            return true;
        } catch (Exception e) {
            System.out.println("Error a"
                    + "l agregar factura: " + e.getMessage());
            return false;
        }
    }

    // B - BÚSQUEDA
    public Factura buscarFactura(int idFactura) {
        for (Factura f : listaFacturas) {
            if (f.getIdFactura() == idFactura) {
                return f;
            }
        }
        return null;
    }

    // D - BAJA
    public boolean eliminarFactura(int idFactura) {
        try {
            Factura f = buscarFactura(idFactura);
            if (f != null) {
                listaFacturas.remove(f);
                System.out.println("Factura eliminada de la memoria.");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al eliminar factura: " + e.getMessage());
            return false;
        }
    }

    public List<Factura> getListaFacturas() {
        return listaFacturas;
    }
}
