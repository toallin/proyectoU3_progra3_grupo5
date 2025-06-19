/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author HP
 */
public class Plato {
    private int id;
    private String nombre;
    private String descripcion;
    private String porcion;
    private int calorias;
    private String ingredientes;
    private int tipoComidaId;
    private String categoria; // Desayuno, Almuerzo, Cena
    private double precio;

    public Plato() {
    }

    public Plato(int id, String nombre, String descripcion, String porcion, int calorias, String ingredientes, int tipoComidaId, String categoria, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.porcion = porcion;
        this.calorias = calorias;
        this.ingredientes = ingredientes;
        this.tipoComidaId = tipoComidaId;
        this.categoria = categoria;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPorcion() {
        return porcion;
    }

    public void setPorcion(String porcion) {
        this.porcion = porcion;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public int getTipoComidaId() {
        return tipoComidaId;
    }

    public void setTipoComidaId(int tipoComidaId) {
        this.tipoComidaId = tipoComidaId;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    
}
