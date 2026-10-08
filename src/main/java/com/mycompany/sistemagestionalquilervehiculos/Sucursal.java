/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class Sucursal {
    private int idSucursal;
    private String nombre;
    private String direccion;
    private String telefono;

    public Sucursal() {

    }

    public Sucursal(int idSucursal, String nombre, String direccion, String telefono) {
        this.idSucursal = idSucursal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    /**
     * @return the idSucursal
     */
    public int getIdSucursal() {
        return idSucursal;
    }

    /**
     * @param idSucursal the idSucursal to set
     */
    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void registrarSucursal() {
        System.out.println("Sucursal registrada exitosamente: " + nombre + " (ID: " + idSucursal + ")");
    }

    public void actualizarContacto(String direccion, String telefono) {
        this.direccion = direccion;
        this.telefono = telefono;
        System.out.println("Contacto actualizado exitosamente. Direccion: " + direccion + " | Telefono: " + telefono);
    }

    public String obtenerInfo() {
        return "Sucursal: " + nombre + " | Direccion: " + direccion + " | Telefono: " + telefono;
    }

    @Override
    public String toString() {
        return "Sucursal{" +
                "idSucursal=" + idSucursal +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
