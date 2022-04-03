package es.upm.dit.isst.barriocovid.repository;

import java.util.*;

import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.barriocovid.model.Usuario;


    public interface UsuarioRepository extends CrudRepository<Usuario,Integer> {
        List<Usuario> findByTipo(String tipo);
        Optional<Usuario> findByNombre(String nombre);
    
    }
    


    

