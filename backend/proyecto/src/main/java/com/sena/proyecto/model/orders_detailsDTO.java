package com.sena.proyecto.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "orders_details")
public class orders_detailsDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_details;

    @ManyToOne
    @JoinColumn(name = "id_orders")
    private ordersDTO id_rders;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private productDTO id_prducto;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public int getId_details() {
        return id_details;
    }

    public void setId_details(int id_details) {
        this.id_details = id_details;
    }

    public ordersDTO getId_rders() {
        return id_rders;
    }

    public void setId_rders(ordersDTO id_rders) {
        this.id_rders = id_rders;
    }

    public productDTO getId_prducto() {
        return id_prducto;
    }

    public void setId_prducto(productDTO id_prducto) {
        this.id_prducto = id_prducto;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}
