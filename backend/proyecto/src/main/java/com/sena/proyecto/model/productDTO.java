package com.sena.proyecto.model;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity(name="product")

public class productDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_product;

    @Column(name="name",nullable = false,length=100)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "product_categories",
        joinColumns = @JoinColumn(name="id_product"),
        inverseJoinColumns = @JoinColumn(name="id_categories")

    )
    private Set<categoriesDTO> categories;

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<categoriesDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<categoriesDTO> categories) {
        this.categories = categories;
    }

    


}
