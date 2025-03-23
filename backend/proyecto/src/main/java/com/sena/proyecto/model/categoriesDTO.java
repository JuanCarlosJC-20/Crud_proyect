package com.sena.proyecto.model;

import java.util.Set;

import com.sena.proyecto.service.CategoriesService;

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

    @Column(name="name",nullable = false ,length = 50)
    private String name;
    
    //se crea la columna status con set y get. para funcinar
    @Column(name = "status", nullable = false)
    private int status;
    
 

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @ManyToMany(mappedBy="categories")
    private Set<productDTO> product;

    public int getId_categories() {
        return id_categories;
    }

    public void setId_categories(int id_categories) {
        this.id_categories = id_categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<productDTO> getProduct() {
        return product;
    }

    public void setProduct(Set<productDTO> product) {
        this.product = product;
    }

    public boolean save(CategoriesService categoriesService){
       
        categoriesService.CategoriesRepository.save(this);
        return true;
    }

    


}
