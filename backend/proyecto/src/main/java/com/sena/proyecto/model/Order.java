package com.sena.proyecto.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // 👇 Relación con cliente (muchas órdenes pueden tener un solo cliente)
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_costumers", nullable = false)
    private Customer customer;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    // 👇 Relación inversa (opcional, pero útil)
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
