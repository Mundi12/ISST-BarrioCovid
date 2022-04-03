package es.upm.dit.isst.barriocovid.repository;

import java.util.*;

import org.springframework.data.repository.CrudRepository;


import es.upm.dit.isst.barriocovid.model.*;

public interface InfoProductoRepository extends CrudRepository<InfoProducto,Integer> {
    Optional <InfoProducto> findByProducto(Producto producto);
}