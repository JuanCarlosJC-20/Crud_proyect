package com.sena.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sena.proyecto.model.categoriesDTO;
import com.sena.proyecto.service.CategoriesService;

@RestController
public class CategoriesController {
    @Autowired
    private CategoriesService categoriesService;

    @PostMapping("/categories")
    public String registerCategories(
        @RequestBody  categoriesDTO  categories
        ) {
        categoriesService.save(categories);
        return "register ok";
    }

      



}
