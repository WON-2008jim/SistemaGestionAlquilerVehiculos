/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logica;

/**
 *
 * @author jimmy
 */
import com.mycompany.sistemagestionalquilervehiculos.ContratoAlquiler;
import java.util.ArrayList;
import java.util.List;
public class GestorContratos {
    private List<ContratoAlquiler> listaContratos = new ArrayList<>();

    public boolean agregarContrato(ContratoAlquiler contrato) {
        try {
            if (contrato == null) {
                throw new IllegalArgumentException("El contrato no puede ser nulo.");
            }
            listaContratos.add(contrato);
            System.out.println("Contrato N° " + contrato.getIdContrato() + " registrado correctamente en memoria.");
            return true;
        } catch (Exception e) {
            System.out.println("Error al registrar contrato: " + e.getMessage());
            return false;
        }
    }

    public ContratoAlquiler buscarContrato(int idContrato) {
        for (ContratoAlquiler c : listaContratos) {
            if (c.getIdContrato() == idContrato) {
                return c;
            }
        }
        return null;
    }

    public boolean eliminarContrato(int idContrato) {
        try {
            ContratoAlquiler c = buscarContrato(idContrato);
            if (c != null) {
                listaContratos.remove(c);
                System.out.println("Contrato N° " + idContrato + " eliminado.");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Error al eliminar contrato: " + e.getMessage());
            return false;
        }
    }

    public List<ContratoAlquiler> getListaContratos() {
        return listaContratos;
    }
}
