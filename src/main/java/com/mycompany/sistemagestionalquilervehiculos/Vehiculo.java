/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class Vehiculo {
    private int idVehiculo;
    private String marca;
    private String modelo;
    private String vin;
    private String placa;
    private int anio;
    private double kilometraje;
    private double tarifaDiaria;
    private String estado;

    public Vehiculo() {

    }

    public Vehiculo(int idVehiculo, String marca, String modelo, String vin, String placa, int anio, double kilometraje, double tarifaDiaria, String estado) {
        this.idVehiculo = idVehiculo;
        this.marca = marca;
        this.modelo = modelo;
        this.vin = vin;
        this.placa = placa;
        this.anio = anio;
        this.kilometraje = kilometraje;
        this.tarifaDiaria = tarifaDiaria;
        this.estado = estado;
    }

    /**
     * @return the idVehiculo
     */
    public int getIdVehiculo() {
        return idVehiculo;
    }

    /**
     * @param idVehiculo the idVehiculo to set
     */
    public void setIdVehiculo(int idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    /**
     * @return the marca
     */
    public String getMarca() {
        return marca;
    }

    /**
     * @param marca the marca to set
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * @return the modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * @param modelo the modelo to set
     */
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    /**
     * @return the vin
     */
    public String getVin() {
        return vin;
    }

    /**
     * @param vin the vin to set
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * @return the placa
     */
    public String getPlaca() {
        return placa;
    }

    /**
     * @param placa the placa to set
     */
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    /**
     * @return the anio
     */
    public int getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(int anio) {
        this.anio = anio;
    }

    /**
     * @return the kilometraje
     */
    public double getKilometraje() {
        return kilometraje;
    }

    /**
     * @param kilometraje the kilometraje to set
     */
    public void setKilometraje(double kilometraje) {
        this.kilometraje = kilometraje;
    }

    /**
     * @return the tarifaDiaria
     */
    public double getTarifaDiaria() {
        return tarifaDiaria;
    }

    /**
     * @param tarifaDiaria the tarifaDiaria to set
     */
    public void setTarifaDiaria(double tarifaDiaria) {
        this.tarifaDiaria = tarifaDiaria;
    }

    /**
     * @return the estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void cambiarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
        System.out.println("Estado del vehiculo actualizado a: " + nuevoEstado);
    }

    public void registrarKilometraje(double km) {
        if (km >= this.kilometraje) {
            this.kilometraje = km;
            System.out.println("Kilometraje actualizado exitosamente a: " + km + " km");
        } else {
            System.out.println("Error: el nuevo kilometraje no puede ser menor al actual (" + this.kilometraje + " km)");
        }
    }

    public boolean consultarDisponibilidad() {
        return "Disponible".equalsIgnoreCase(estado);
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "idVehiculo=" + idVehiculo +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", vin='" + vin + '\'' +
                ", placa='" + placa + '\'' +
                ", anio=" + anio +
                ", kilometraje=" + kilometraje +
                ", tarifaDiaria=" + tarifaDiaria +
                ", estado='" + estado + '\'' +
                '}';
    }
}
