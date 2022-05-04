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

    @GetMapping("/misproductos/pedidos") 
    public String verpedidos(Model model){
        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        model.addAttribute("comprador", comprador);

        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        List <InfoProducto> infoProductos = (List<InfoProducto>) infoProductoRepository.findAll();
        
        if (infoProductos.isEmpty()==false){
            if (pedidos.get(0).getEstado() != 4){
                Pedido pedido = pedidos.get(0);
                model.addAttribute("pedido", pedido);

                logger.info("Este es el objeto infoproducto {}",infoProductoRepository.findAll());
                model.addAttribute("infos", infoProductoRepository.findAll());
                return "vendedor/pedidos";
            }
        } 
        return "redirect:/misproductos";
    }
   
    @GetMapping("/misproductos/pedidos/borrado") 
    public String borrarpedido(Model model){
        infoProductoRepository.deleteAll();
        return "redirect:/misproductos";
    }


    @GetMapping("/misproductos/pedidos/confirmado") 
    public String confirmarpedido(Model model){
        
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);

        logger.info("Estado problematico: {}", pedido.getEstado());
        pedido.setEstado(4);
        logger.info("Estado problematico2: {}", pedido.getEstado());
        pedidoRepository.save(pedido);

        return "redirect:/misproductos";
    }
    
    @GetMapping("/misproductos/pedidosConfirmados") 
    public String verpedidosconfirmados(Model model){
        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        model.addAttribute("comprador", comprador);

        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        List <InfoProducto> infoProductos = (List<InfoProducto>) infoProductoRepository.findAll();
        
        if (infoProductos.isEmpty()==false){
            if (pedidos.get(0).getEstado() == 6){

                Pedido pedido = pedidos.get(0);
                model.addAttribute("pedido", pedido);
    
                logger.info("Este es el objeto infoproducto {}",infoProductoRepository.findAll());
                model.addAttribute("infos", infoProductoRepository.findAll());
                return "vendedor/pedidosConfirmados";
            }
        } 
        return "redirect:/misproductos";
    }
    
    @GetMapping("/misproductos/pedidosConfirmados/listo") 
    public String verpedidosconfirmadoslisto(Model model){
        
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);

        logger.info("Estado problematico: {}", pedido.getEstado());
        pedido.setEstado(6);
        logger.info("Estado problematico2: {}", pedido.getEstado());
        pedidoRepository.save(pedido);

        return "redirect:/misproductos";
    }

    @GetMapping("/misproductos/pedidosRecogidos") 
    public String verpedidosrecogidos(Model model){
        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        model.addAttribute("comprador", comprador);

        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        List <InfoProducto> infoProductos = (List<InfoProducto>) infoProductoRepository.findAll();
        
        if (infoProductos.isEmpty()==false){
            if (pedidos.get(0).getEstado() == 7){

                Pedido pedido = pedidos.get(0);
                model.addAttribute("pedido", pedido);
    
                logger.info("Este es el objeto infoproducto {}",infoProductoRepository.findAll());
                model.addAttribute("infos", infoProductoRepository.findAll());
                return "vendedor/pedidosConfirmados";
            }
        } 
        return "redirect:/misproductos";
    }
    
    @GetMapping("/misproductos/pedidosRecogido/listo") 
    public String verpedidosrecogidoslisto(Model model){
        
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);

        logger.info("Estado problematico: {}", pedido.getEstado());
        pedido.setEstado(7);
        logger.info("Estado problematico2: {}", pedido.getEstado());
        pedidoRepository.save(pedido);

        return "redirect:/misproductos";
    }
}
