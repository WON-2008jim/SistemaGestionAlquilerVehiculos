/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class Cliente {
    private int idCliente;
    private String nombre;
    private String dpi;
    private String nit;
    private String telefono;
    private String correo;
    private String numLicencia;
    
    public Cliente(){
        
    }
    public Cliente(int idCliente, String nombre, String dpi, String nit, String telefono, String correo, String numLicencia) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.dpi = dpi;
        this.nit = nit;
        this.telefono = telefono;
        this.correo = correo;
        this.numLicencia = numLicencia;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
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
     * @return the dpi
     */
    public String getDpi() {
        return dpi;
    }

    /**
     * @param dpi the dpi to set
     */
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    /**
     * @return the nit
     */
    public String getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(String nit) {
        this.nit = nit;
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

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the numLicencia
     */
    public String getNumLicencia() {
        return numLicencia;
    }

    /**
     * @param numLicencia the numLicencia to set
     */
    public void setNumLicencia(String numLicencia) {
        this.numLicencia = numLicencia;
    }
    
    public void registrarClientes(){
        System.out.println("Cliente registrado existosamente:" + nombre);
    }
    
    public boolean validarLicencia() {
        return numLicencia != null && !numLicencia.trim().isEmpty();
    }

    public void actualizarContacto(String telefono, String correo) {
        this.telefono = telefono;
        this.correo = correo;
        System.out.println("Contacto de " + nombre + " actualizado correctamente.");
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", nombre='" + nombre + '\'' +
                ", dpi='" + dpi + '\'' +
                ", nit='" + nit + '\'' +
                ", telefono='" + telefono + '\'' +
                ", correo='" + correo + '\'' +
                ", numLicencia='" + numLicencia + '\'' +
                '}';
    }
}
