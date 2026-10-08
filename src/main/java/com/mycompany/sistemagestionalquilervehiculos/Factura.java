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
public class Factura {
   
    private int idFactura;
    private String numeroSerie;
    private Date fechaEmision;
    private double totalFactura;
    private ContratoAlquiler contrato;

    public Factura() {

    }

    public Factura(int idFactura, String numeroSerie, Date fechaEmision, double totalFactura) {
        this.idFactura = idFactura;
        this.numeroSerie = numeroSerie;
        this.fechaEmision = fechaEmision;
        this.totalFactura = totalFactura;
    }

    /**
     * @return the idFactura
     */
    public int getIdFactura() {
        return idFactura;
    }

    /**
     * @param idFactura the idFactura to set
     */
    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    /**
     * @return the numeroSerie
     */
    public String getNumeroSerie() {
        return numeroSerie;
    }

    /**
     * @param numeroSerie the numeroSerie to set
     */
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    /**
     * @return the fechaEmision
     */
    public Date getFechaEmision() {
        return fechaEmision;
    }

    /**
     * @param fechaEmision the fechaEmision to set
     */
    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    /**
     * @return the totalFactura
     */
    public double getTotalFactura() {
        return totalFactura;
    }

    /**
     * @param totalFactura the totalFactura to set
     */
    public void setTotalFactura(double totalFactura) {
        this.totalFactura = totalFactura;
    }

    /**
     * @return the contrato
     */
    public ContratoAlquiler getContrato() {
        return contrato;
    }

    /**
     * @param contrato the contrato to set
     */
    public void setContrato(ContratoAlquiler contrato) {
        this.contrato = contrato;
    }

    public void generarFactura(ContratoAlquiler contrato) {
        this.contrato = contrato;
        this.totalFactura = calcularTotal();
        System.out.println("Factura " + numeroSerie + " generada exitosamente para el contrato ID: " + contrato.getIdContrato());
    }

    public double calcularTotal() {
    if (contrato != null) {
        return contrato.getTotal();
    }
    return this.totalFactura;
    }

    public String imprimirFactura() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== FACTURA ===");
        sb.append("\nID Factura: ").append(idFactura);
        sb.append("\nNo. Serie: ").append(numeroSerie);
        sb.append("\nFecha de Emisión: ").append(fechaEmision);
        sb.append("\nTotal: Q").append(totalFactura);
        if (contrato != null) {
            sb.append("\nContrato Asociado: ").append(contrato.getIdContrato());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Factura{" +
                "idFactura=" + idFactura +
                ", numeroSerie='" + numeroSerie + '\'' +
                ", fechaEmision=" + fechaEmision +
                ", totalFactura=" + totalFactura +
                '}';
    }
}

