package com.sena.proyecto.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "customers")
public class customersDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_costumers;

    @Column(name="name",nullable = false,length=100)
    private String name;
    //columna status para que? no se .
    @Column(name = "status", nullable = false)
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId_costumers() {
        return id_costumers;
    }

    public void setId_costumers(int id_costumers) {
        this.id_costumers = id_costumers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    

}
