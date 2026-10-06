/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sistemagestionalquilervehiculos;

/**
 *
 * @author jimmy
 */
public class CategoriaVehiculo {
    private int idCategoria;
    private String nombreCategoria;
    private String descripcion;
    private double tarifaBase;
    
    public CategoriaVehiculo(){
        
    }
    
    public CategoriaVehiculo(int idCategoria, String nombreCategoria, String descripcion, double tarifaBase) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descripcion = descripcion;
        this.tarifaBase = tarifaBase;
    }

    /**
     * @return the idCategoria
     */
    public int getIdCategoria() {
        return idCategoria;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    /**
     * @return the nombreCategoria
     */
    public String getNombreCategoria() {
        return nombreCategoria;
    }

    /**
     * @param nombreCategoria the nombreCategoria to set
     */
    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
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
     * @return the tarifaBase
     */
    public double getTarifaBase() {
        return tarifaBase;
    }

    /**
     * @param tarifaBase the tarifaBase to set
     */
    public void setTarifaBase(double tarifaBase) {
        this.tarifaBase = tarifaBase;
    }
    
    public String obtenerCategoria(){
        return "Categoria: " + nombreCategoria + " | Tarifa Base: Q" + tarifaBase;
    }
    public void actualizarTarifa(double nuevaTarifa) {
        this.tarifaBase = nuevaTarifa;
        System.out.println("Tarifa actualizada exitosamente a: Q" + nuevaTarifa);
    }
    @Override
    public String toString() {
        return "CategoriaVehiculo{" +
                "idCategoria=" + idCategoria +
                ", nombreCategoria='" + nombreCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", tarifaBase=" + tarifaBase +
                '}';
    }
}
