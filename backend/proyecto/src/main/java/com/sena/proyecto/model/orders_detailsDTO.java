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

}
