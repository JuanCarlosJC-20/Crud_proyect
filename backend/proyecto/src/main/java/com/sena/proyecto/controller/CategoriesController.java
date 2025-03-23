package com.sena.proyecto.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.proyecto.model.categoriesDTO;
import com.sena.proyecto.service.CategoriesService;

import responseDTO.responseDTO;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/")
    public String registerCategories(
        @RequestBody  categoriesDTO  categories
        ) {
        categories.save(categoriesService);
        return "register ok";
    }
    //aplica lo ultimo
     @GetMapping("/")
    public ResponseEntity<Object> findAllcateogires() {
        List<categoriesDTO> listcategories = categoriesService.getAllCategories();
        return new ResponseEntity<>(listcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByIdcategories(@PathVariable int id) {
        categoriesDTO categories = categoriesService.getCategoriesById(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/search/{filter}")
    public ResponseEntity<Object> search(@PathVariable String filter) {
        List<categoriesDTO> listcategories = categoriesService.getFiltercategories(filter);
        return new ResponseEntity<>(listcategories, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletecategories(@PathVariable int id) {
        responseDTO response = categoriesService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

      



}
