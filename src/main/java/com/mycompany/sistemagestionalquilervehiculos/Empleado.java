/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class Empleado {
    private int idEmpleado;
    private String nombre;
    private String rol;
    private String usuario;
    private String clave;

    public Empleado() {

    }

    public Empleado(int idEmpleado, String nombre, String rol, String usuario, String clave) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.rol = rol;
        this.usuario = usuario;
        this.clave = clave;
    }

    /**
     * @return the idEmpleado
     */
    public int getIdEmpleado() {
        return idEmpleado;
    }

    /**
     * @param idEmpleado the idEmpleado to set
     */
    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
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
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * @param rol the rol to set
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the clave
     */
    public String getClave() {
        return clave;
    }

    /**
     * @param clave the clave to set
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    public boolean autenticar(String usuario, String clave) {
        if (this.usuario != null && this.clave != null) {
            return this.usuario.equals(usuario) && this.clave.equals(clave);
        }
        return false;
    }

    public String obtenerPerfil() {
        return "ID: " + idEmpleado + " | Nombre: " + nombre + " | Rol: " + rol + " | Usuario: " + usuario;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", nombre='" + nombre + '\'' +
                ", rol='" + rol + '\'' +
                ", usuario='" + usuario + '\'' +
                '}';
    }
}
