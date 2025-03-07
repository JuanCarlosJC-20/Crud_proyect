package com.sena.proyecto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sena.proyecto.model.productDTO;
import com.sena.proyecto.service.ProductService;



@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    public String registerProduct(
        @RequestBody productDTO product
        ){
           productService.save(product);
        return "register ok";
    }

}
