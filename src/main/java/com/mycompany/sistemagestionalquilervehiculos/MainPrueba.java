/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
import logica.GestorClientes;
public class MainPrueba {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO PRUEBA DE CONEXIÓN Y GESTOR ===");
        
        GestorClientes gestor = new GestorClientes();
        
        // 1. Crear un cliente de prueba
        Cliente c = new Cliente();
        c.setNombre("Carlos Mendoza");
        c.setDpi("2548789650101");
        c.setNit("457812-3");
        c.setTelefono("5412-8796");
        c.setCorreo("carlos.mendoza@email.com");
        c.setNumLicencia("LIC-2026-XYZ");
        
        // 2. Intentar agregarlo a la Base de Datos
        boolean resultado = gestor.agregarCliente(c);
        
        if (resultado) {
            System.out.println("¡Prueba de INSERT exitosa!");
        } else {
            System.out.println("El registro no se pudo completar (puede que el DPI ya exista).");
        }
        
        // 3. Consultar y listar los clientes almacenados en la BD
        System.out.println("\n--- LISTA ACTUAL DE CLIENTES EN POSTGRESQL ---");
        for (Cliente clienteBD : gestor.getListaClientes()) {
            System.out.println("ID: " + clienteBD.getIdCliente() + 
                               " | Nombre: " + clienteBD.getNombre() + 
                               " | DPI: " + clienteBD.getDpi() + 
                               " | Licencia: " + clienteBD.getNumLicencia());
        }
    }
}
