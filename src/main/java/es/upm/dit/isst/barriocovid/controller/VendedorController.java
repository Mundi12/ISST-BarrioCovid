package es.upm.dit.isst.barriocovid.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.isst.barriocovid.model.*;
import es.upm.dit.isst.barriocovid.repository.*;

import java.util.*;

import org.slf4j.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
public class VendedorController {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InfoProductoRepository infoProductoRepository;
    private final PedidoRepository pedidoRepository;

    List<InfoProducto> infosProducto = new ArrayList<InfoProducto>();
    Pedido pedido = new Pedido();


    public VendedorController(ProductoRepository p, UsuarioRepository u, InfoProductoRepository i, PedidoRepository pe) {

        this.productoRepository = p;
        this.usuarioRepository = u;
        this.infoProductoRepository =i;
        this.pedidoRepository = pe;

    }

    private final Logger logger = LoggerFactory.getLogger(VendedorController.class);

    @GetMapping("/misproductos")
    public String verProductos(Model model){
        model.addAttribute("misproductos", productoRepository.findAll());
        return "vendedor/verProducto";

    }

    @GetMapping("/misproductos/nuevoProducto")
    public String crearProducto(){
        return "vendedor/crearProducto";

    }

    @PostMapping("/misproductos/productoGuardado")
    public String guardarProducto(Producto producto){
       
        Usuario usuario = new Usuario(2, "Supermercado BM","demo","supermarket.bm@gmail.com","C. de la Cruz, 23, 28012 Madrid","655432518","vendedor",false);
        usuarioRepository.save(usuario);

        producto.setUsuario(usuario);
        productoRepository.save(producto);
        logger.info("Este es el objeto producto {}",producto);
        return "redirect:/misproductos";

    }

    @GetMapping("/misproductos/editarProducto/{id}") 
    public String editarProducto(@PathVariable Integer id, Model model){
        Producto producto = new Producto();
        producto = productoRepository.findById(id).get();
        model.addAttribute("producto", producto);

        logger.info("Producto buscado: {}", producto);
        return "/vendedor/editarProducto";
    }

    @PostMapping("/misproductos/productoEditado") 
    public String productoEditado(Producto producto){
        Usuario usuario = new Usuario(2, "Supermercado BM","demo","supermarket.bm@gmail.com","C. de la Cruz, 23, 28012 Madrid","655432518","vendedor",false);
        usuarioRepository.save(usuario);
        producto.setUsuario(usuario);
        productoRepository.save(producto);
        return "redirect:/misproductos";
    }

    @GetMapping("/misproductos/borrarProducto/{id}") 
    public String borrarProducto(@PathVariable Integer id){
        productoRepository.deleteById(id);
        return "redirect:/misproductos";
    }

    

   
    
    

    
    
}
