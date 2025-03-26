package com.sena.proyecto.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.sena.proyecto.model.customersDTO;

public interface CustomersRepository extends JpaRepository<customersDTO,Integer> {
  @Query("SELECT b FROM customers b WHERE b.status=1")
    List<customersDTO> findAllcustomersActive();

    // @Query("SELECT b FROM book b WHERE b.title LIKE %?1%")
    // List<bookDTO> search(String filter);
    @Query("SELECT b FROM customers b WHERE b.name LIKE %?1%")
    List<customersDTO> search(String filter);
}
