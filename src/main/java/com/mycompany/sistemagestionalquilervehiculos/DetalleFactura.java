/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class DetalleFactura {
    private int idDetalleFactura;
    private String concepto;
    private double monto;
    private Factura factura;

    public DetalleFactura() {

    }

    public DetalleFactura(int idDetalleFactura, String concepto, double monto) {
        this.idDetalleFactura = idDetalleFactura;
        this.concepto = concepto;
        this.monto = monto;
    }

    /**
     * @return the idDetalleFactura
     */
    public int getIdDetalleFactura() {
        return idDetalleFactura;
    }

    /**
     * @param idDetalleFactura the idDetalleFactura to set
     */
    public void setIdDetalleFactura(int idDetalleFactura) {
        this.idDetalleFactura = idDetalleFactura;
    }

    /**
     * @return the concepto
     */
    public String getConcepto() {
        return concepto;
    }

    /**
     * @param concepto the concepto to set
     */
    public void setConcepto(String concepto) {
        this.concepto = concepto;
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

    /**
     * @return the factura
     */
    public Factura getFactura() {
        return factura;
    }

    /**
     * @param factura the factura to set
     */
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public double obtenerSubtotal() {
        return this.monto;
    }

    public String mostrarConcepto() {
        return "Detalle #" + idDetalleFactura + " | Concepto: " + concepto + " | Monto: Q" + monto;
    }

    @Override
    public String toString() {
        return "DetalleFactura{" +
                "idDetalleFactura=" + idDetalleFactura +
                ", concepto='" + concepto + '\'' +
                ", monto=" + monto +
                '}';
    }
}
