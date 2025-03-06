package com.sena.proyecto.model;

import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;


@Entity(name="orders")
public class ordersDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_orders;
    
    
    @Column(name = "date", nullable = false)
    private Date date;


    @ManyToOne
    @JoinColumn(name = "id_costumers")
    private customersDTO  id_customers;

}
