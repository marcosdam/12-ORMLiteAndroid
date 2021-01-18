package com.example.a12_ormliteandroid.modelos;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

// Después de añadir la librería orm-android, comenzamos a mapear
@DatabaseTable(tableName = "Ordenadores")
public class Ordenador {

    // Primary Key
    @DatabaseField(generatedId = true, columnName = "id_ordenador")  // Autoincremental
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Resto de campos
    @DatabaseField(canBeNull = false)   // Campo obligatorio
    private String marca;
    @DatabaseField(canBeNull = false)   // Campo obligatorio
    private String modelo;
    @DatabaseField(columnName = "ram_ordenador")   // Puede ser nulo
    private int ram;
    @DatabaseField()
    private float hd;

    // Necesario const vacío para ORM Lite
    public Ordenador() {
    }

    public Ordenador(String marca, String modelo, int ram, float hd) {
        this.marca = marca;
        this.modelo = modelo;
        this.ram = ram;
        this.hd = hd;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public float getHd() {
        return hd;
    }

    public void setHd(float hd) {
        this.hd = hd;
    }
}
