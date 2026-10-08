/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */

import java.util.Date;

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE ALQUILER DE VEHÍCULOS    ");
        System.out.println("==================================================\n");

        // 1. Instanciar Sucursal y CategoriaVehiculo
        Sucursal sucursalCentral = new Sucursal(1, "Sucursal Central", "Zona 10, Ciudad de Guatemala", "2200-1122");
        CategoriaVehiculo catSedan = new CategoriaVehiculo(101, "Sedán", "Vehículos confortables de 4 puertas", 350.00);

        // 2. Instanciar Empleado y probar autenticación
        Empleado empleado = new Empleado(1, "Carlos Gómez", "Asesor de Ventas", "cgomez", "pass123");
        boolean loginExitoso = empleado.autenticar("cgomez", "pass123");
        System.out.println("Autenticación de empleado: " + (loginExitoso ? "Exitosa (" + empleado.obtenerPerfil() + ")" : "Fallida"));
        System.out.println();

        // 3. Instanciar Cliente y validar licencia
        Cliente cliente = new Cliente(1, "Juan Pérez", "1234567890101", "8765432-1", "5555-4444", "juan.perez@email.com", "L-98765");
        cliente.registrarClientes();
        System.out.println("¿Licencia válida?: " + cliente.validarLicencia());
        System.out.println();

        // 4. Instanciar Vehículo
        Vehiculo vehiculo = new Vehiculo(
            1, "Toyota", "Corolla", "1NXBR12E3456789", "P-123ABC",
            2023, 15000.0, 350.00, "Disponible", catSedan, sucursalCentral
        );
        System.out.println("Vehículo registrado: " + vehiculo.getMarca() + " " + vehiculo.getModelo() + " [Placa: " + vehiculo.getPlaca() + "]");
        System.out.println("¿Disponible?: " + vehiculo.consultarDisponibilidad());
        System.out.println();

        // 5. Instanciar Contrato de Alquiler y asociar entidades
        Date fechaInicio = new Date();
        Date fechaFin = new Date(System.currentTimeMillis() + (3L * 24 * 60 * 60 * 1000)); // 3 días después
        ContratoAlquiler contrato = new ContratoAlquiler(
            5001, fechaInicio, fechaFin, 3, 1050.00, "Activo", cliente, vehiculo, empleado
        );

        contrato.asociarCliente(cliente);
        contrato.asociarVehiculo(vehiculo);
        contrato.agregarDetalle("Seguro de cobertura total", 150.00);
        vehiculo.cambiarEstado("Alquilado");
        System.out.println();

        // 6. Instanciar DetalleContrato
        DetalleContrato detalleContrato = new DetalleContrato(1, "Silla de bebé adicional", 50.00);
        System.out.println(detalleContrato.obtenerDetalle());
        System.out.println();

        // 7. Generar Factura y DetalleFactura
        Factura factura = new Factura(1001, "F-001", new Date(), 0.0);
        factura.generarFactura(contrato);

        DetalleFactura detalleFactura = new DetalleFactura(1, "Alquiler 3 días Toyota Corolla", 1050.00);
        detalleFactura.setFactura(factura);
        System.out.println(detalleFactura.mostrarConcepto());

        System.out.println("\n--- IMPRESIÓN DE FACTURA ---");
        System.out.println(factura.imprimirFactura());
        System.out.println("----------------------------\n");

        // 8. Finalizar contrato, actualizar kilometraje y probar Mantenimiento
        contrato.finalizarContrato();
        vehiculo.cambiarEstado("Disponible");
        vehiculo.registrarKilometraje(15800.0);
        System.out.println();

        Mantenimiento mantenimiento = new Mantenimiento(1, new Date(), "Preventivo", "Cambio de aceite y filtro", 450.00);
        mantenimiento.registrarMantenimiento(vehiculo);
        mantenimiento.finalizarMantenimiento();
        System.out.println();

        System.out.println("==================================================");
        System.out.println("   PRUEBA INTEGRAL COMPLETADA EXITOSAMENTE        ");
        System.out.println("==================================================");   
    }
}
