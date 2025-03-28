package com.sena.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.sena.proyecto.model.productDTO;

public interface ProductRepository extends JpaRepository<productDTO,Integer> {
  @Query("SELECT b FROM product b WHERE b.status=1")
    List<productDTO> findAllcategoriesActive();

    // @Query("SELECT b FROM book b WHERE b.title LIKE %?1%")
    // List<bookDTO> search(String filter);
    @Query("SELECT b FROM product b WHERE b.name LIKE %?1%")
    List<productDTO> search(String filter);
    /*
     * example
     * 
     * @Query("SELECT b FROM book b WHERE b.title LIKE %?1% b.author LIKE %?2%")
     * List<bookDTO> search(String variable1, String variable2, String Variablen);
     */
}
