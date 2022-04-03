package es.upm.dit.isst.barriocovid.repository;

import java.util.*;

import org.springframework.data.repository.CrudRepository;


import es.upm.dit.isst.barriocovid.model.*;

public interface ProductoRepository extends CrudRepository<Producto,Integer> {
    List <Producto> findByUsuario(Usuario usuario);
    Optional <Producto> findById(Integer id);
    
}
