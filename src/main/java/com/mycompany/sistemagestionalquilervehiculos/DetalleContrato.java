/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class DetalleContrato {
    private int idDetalleContrato;
    private String cargoAdicional;
    private double monto;
    
    public DetalleContrato() {
    }

    public DetalleContrato(int idDetalleContrato, String cargoAdicional, double monto) {
        this.idDetalleContrato = idDetalleContrato;
        this.cargoAdicional = cargoAdicional;
        this.monto = monto;
    }

    /**
     * @return the idDetalleContrato
     */
    public int getIdDetalleContrato() {
        return idDetalleContrato;
    }

    /**
     * @param idDetalleContrato the idDetalleContrato to set
     */
    public void setIdDetalleContrato(int idDetalleContrato) {
        this.idDetalleContrato = idDetalleContrato;
    }

    /**
     * @return the cargoAdicional
     */
    public String getCargoAdicional() {
        return cargoAdicional;
    }

    /**
     * @param cargoAdicional the cargoAdicional to set
     */
    public void setCargoAdicional(String cargoAdicional) {
        this.cargoAdicional = cargoAdicional;
    }

    /**
     * @return the monto
     */
    public double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(double monto) {
        this.monto = monto;
    }
    
    public double obtenerMonto() {
        return monto;
    }

    public String obtenerDetalle() {
        return "Detalle N° " + idDetalleContrato + " | Cargo: " + cargoAdicional + " | Monto: Q" + monto;
    }

    @Override
    public String toString() {
        return "DetalleContrato{" +
                "idDetalleContrato=" + idDetalleContrato +
                ", cargoAdicional='" + cargoAdicional + '\'' +
                ", monto=" + monto +
                '}';
    }
}
