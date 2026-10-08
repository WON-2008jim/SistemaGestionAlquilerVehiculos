/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
import java.util.Date;

public class Mantenimiento {
    private int idMantenimiento;
    private Date fecha;
    private String tipoMantenimiento;
    private String descripcion;
    private double costo;
    private Vehiculo vehiculo;

    public Mantenimiento() {

    }

    public Mantenimiento(int idMantenimiento, Date fecha, String tipoMantenimiento, String descripcion, double costo) {
        this.idMantenimiento = idMantenimiento;
        this.fecha = fecha;
        this.tipoMantenimiento = tipoMantenimiento;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    /**
     * @return the idMantenimiento
     */
    public int getIdMantenimiento() {
        return idMantenimiento;
    }

    /**
     * @param idMantenimiento the idMantenimiento to set
     */
    public void setIdMantenimiento(int idMantenimiento) {
        this.idMantenimiento = idMantenimiento;
    }

    /**
     * @return the fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the tipoMantenimiento
     */
    public String getTipoMantenimiento() {
        return tipoMantenimiento;
    }

    /**
     * @param tipoMantenimiento the tipoMantenimiento to set
     */
    public void setTipoMantenimiento(String tipoMantenimiento) {
        this.tipoMantenimiento = tipoMantenimiento;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the costo
     */
    public double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(double costo) {
        this.costo = costo;
    }

    /**
     * @return the vehiculo
     */
    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    /**
     * @param vehiculo the vehiculo to set
     */
    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void registrarMantenimiento(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        vehiculo.cambiarEstado("En mantenimiento");
        System.out.println("Mantenimiento registrado para el vehiculo con placa: " + vehiculo.getPlaca()
                + " | Tipo: " + tipoMantenimiento + " | Costo: Q" + costo);
    }

    public void finalizarMantenimiento() {
        if (vehiculo != null) {
            vehiculo.cambiarEstado("Disponible");
            System.out.println("Mantenimiento finalizado. El vehiculo con placa " + vehiculo.getPlaca() + " ya esta disponible.");
        } else {
            System.out.println("Error: no hay un vehiculo asociado a este mantenimiento.");
        }
    }

    @Override
    public String toString() {
        return "Mantenimiento{" +
                "idMantenimiento=" + idMantenimiento +
                ", fecha=" + fecha +
                ", tipoMantenimiento='" + tipoMantenimiento + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costo=" + costo +
                '}';
    }
}
