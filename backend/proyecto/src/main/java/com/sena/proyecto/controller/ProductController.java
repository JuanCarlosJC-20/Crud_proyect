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
import com.sena.proyecto.model.productDTO;
import com.sena.proyecto.service.ProductService;

import responseDTO.responseDTO;



@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    private ProductService productService;

         @PostMapping("/")
    public String registerProduct(
        @RequestBody productDTO product
        ){
            productService.save(product);
        return "register ok";
    }
    //Aqui se aplica el filtar eliminar etc.
     @GetMapping("/")
    public ResponseEntity<Object> findAllproduct() {
        List<productDTO> listcategories = productService.getAllProduct();
        return new ResponseEntity<>(listcategories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findByIdproduct(@PathVariable int id) {
        productDTO product = productService.getproductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/search/{filter}")
    public ResponseEntity<Object> search(@PathVariable String filter) {
        List<productDTO> listproduct = productService.getFilterproducto(filter);
        return new ResponseEntity<>(listproduct, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteproduct(@PathVariable int id) {
        responseDTO response = productService.delete(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
