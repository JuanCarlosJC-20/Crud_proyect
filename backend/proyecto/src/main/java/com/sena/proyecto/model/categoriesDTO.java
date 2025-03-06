package com.sena.proyecto.model;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity(name="categories")
public class categoriesDTO {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id_categories;

    @Column(name="name",nullable = false,length = 50)
    private String name;
    
    @ManyToMany(mappedBy="categories")
    private Set<productDTO> product;

}
