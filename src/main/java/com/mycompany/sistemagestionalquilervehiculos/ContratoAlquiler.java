/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

import java.util.Date;

/**
 *
 * @author jimmy
 */
public class ContratoAlquiler {
    private int idContrato;
    private Date fechaInicio;
    private Date fechaFin;
    private int diasAlquiler;
    private double montoBase;
    private double total;
    private String estado;

    // Relaciones / Atributos de Asociación
    private Cliente cliente;
    private Vehiculo vehiculo;

    // 1. CONSTRUCTOR VACÍO (Indispensable para Frameworks, ORM y JSON)
    public ContratoAlquiler() {
    }

    // 2. CONSTRUCTOR COMPLETO
    public ContratoAlquiler(int idContrato, Date fechaInicio, Date fechaFin, int diasAlquiler, double montoBase, String estado, Cliente cliente, Vehiculo vehiculo) {
        this.idContrato = idContrato;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasAlquiler = diasAlquiler;
        this.montoBase = montoBase;
        this.estado = estado;
        this.cliente = cliente;
        this.vehiculo = vehiculo;
        this.total = calcularTotal();
    }

    // --- GETTERS Y SETTERS ESTÁNDAR (JAVABEANS SPEC) ---
    public int getIdContrato() { return idContrato; }
    public void setIdContrato(int idContrato) { this.idContrato = idContrato; }

    public Date getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(Date fechaInicio) { this.fechaInicio = fechaInicio; }

    public Date getFechaFin() { return fechaFin; }
    public void setFechaFin(Date fechaFin) { this.fechaFin = fechaFin; }

    public int getDiasAlquiler() { return diasAlquiler; }
    public void setDiasAlquiler(int diasAlquiler) { this.diasAlquiler = diasAlquiler; }

    public double getMontoBase() { return montoBase; }
    public void setMontoBase(double montoBase) { this.montoBase = montoBase; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // Getters y Setters de las relaciones
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }


    // --- MÉTODOS DE LÓGICA DE NEGOCIO (UML) ---
    public void asociarCliente(Cliente cliente) {
        if (cliente != null && cliente.validarLicencia()) {
            this.cliente = cliente;
            System.out.println("Cliente " + cliente.getNombre() + " asociado exitosamente al contrato N° " + idContrato);
        } else {
            System.out.println("Error: El cliente no cuenta con una licencia válida.");
        }
    }

    public void asociarVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        System.out.println("Vehículo asociado al contrato N° " + idContrato);
    }

    public void agregarDetalle(String cargo, double monto) {
        this.total += monto;
        System.out.println("Cargo adicional agregado: " + cargo + " (Q" + monto + ")");
    }

    public int calcularDias() {
        return diasAlquiler;
    }

    public double calcularTotal() {
        return montoBase;
    }

    public void finalizarContrato() {
        this.estado = "Finalizado";
        System.out.println("El contrato N° " + idContrato + " ha sido finalizado.");
    }

    @Override
    public String toString() {
        return "ContratoAlquiler{" +
                "idContrato=" + idContrato +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", diasAlquiler=" + diasAlquiler +
                ", montoBase=" + montoBase +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                ", cliente=" + (cliente != null ? cliente.getNombre() : "Sin asignar") +
                ", vehiculo=" + (vehiculo != null ? vehiculo.getPlaca() : "Sin asignar") +
                '}';
    }
    
}
