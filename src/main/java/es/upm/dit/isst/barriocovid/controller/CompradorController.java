package es.upm.dit.isst.barriocovid.controller;

import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.isst.barriocovid.model.*;
import org.springframework.ui.Model;
import es.upm.dit.isst.barriocovid.repository.*;

import org.slf4j.*;
import org.springframework.stereotype.Controller;

@Controller

public class CompradorController {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InfoProductoRepository infoProductoRepository;
    private final PedidoRepository pedidoRepository;

    private final Logger logger = LoggerFactory.getLogger(VendedorController.class);
    Pedido pedido = new Pedido();


    
    public CompradorController(ProductoRepository p, UsuarioRepository u, InfoProductoRepository i, PedidoRepository pe) {

        this.productoRepository = p;
        this.usuarioRepository = u;
        this.infoProductoRepository =i;
        this.pedidoRepository= pe;

    }

    @GetMapping("/") 
    public String inicio(){
       
        return "login";
    }

    @GetMapping("/perfil/prueba") 
    public String perfil( Model model){
        model.addAttribute("perfil", usuarioRepository.findById(5).get());
        return "perfil";
    }

    @PostMapping("/perfil") 
    public String editarPerfil(Usuario usuario){  
        
        usuarioRepository.save(usuario);
        return "redirect:/comprador";  
    }




    

    @GetMapping("/comprador") 
    public String homeComprador(Model model){
        model.addAttribute("vendedores", usuarioRepository.findByTipo("vendedor"));
        return "comprador/home";
    }

    @PostMapping("/comprador/buscartienda")
    public String buscarTienda(@RequestParam String nombre){
        Optional <Usuario> usuario = usuarioRepository.findByNombre(nombre);
        Integer idTienda=0;
        try{
           idTienda= usuario.get().getId();
           return "redirect:/comprador/tienda/" + idTienda;
        }
        catch(Exception e) {}
        return "redirect:/comprador";
    }
    
    @GetMapping("/comprador/tienda/{id}") 
    public String tiendaSeleccionada(@PathVariable Integer id, Model model){
        Usuario usuario = usuarioRepository.findById(id).get();
        model.addAttribute("productos", productoRepository.findByUsuario(usuario));
        return "comprador/productos_tiendas";
    }


    @PostMapping("/carrito") 
    public String añadirAlCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        InfoProducto infoProducto = new InfoProducto();
        Producto producto = productoRepository.findById(id).get();
        double total = 0;
        logger.info("Producto buscado: {}", productoRepository.findById(id).get());
        logger.info("Cantidad: {}", cantidad);

        infoProducto.setCantidad(cantidad);
        infoProducto.setPrecio(producto.getPrecio());
        infoProducto.setNombre(producto.getNombre());
        infoProducto.setTotal(producto.getPrecio()* cantidad);
        infoProducto.setProducto(producto);

        boolean dentro= false;

        for(InfoProducto p: infoProductoRepository.findAll() ){
            if(p.getProducto().getId()== id){
                dentro = true;
            }
        }

        if(!dentro){
            infoProductoRepository.save(infoProducto);
        }
        else {
            Integer i = infoProductoRepository.findByProducto(producto).get().getId();
            infoProductoRepository.deleteById(i);
            infoProductoRepository.save(infoProducto);
        }
        for(InfoProducto p: infoProductoRepository.findAll() ){
            total = p.getTotal() + total;    
        }
        
        pedido.setImporte(total);
        pedidoRepository.save(pedido);
        Integer idVendedor = infoProducto.getProducto().getUsuario().getId();
        return "redirect:/comprador/tienda/"+ idVendedor;
    }

    @GetMapping("/verCarrito") 
    public String verCarrito(Model model){
        model.addAttribute("infos", infoProductoRepository.findAll());
        model.addAttribute("pedido", pedido);
        return "comprador/carrito";
    }



    @GetMapping ("/borrarCarrito/{id}")
    public String quitarDelCarrito(@PathVariable Integer id, Model model ){
        Producto producto = productoRepository.findById(id).get();
        
        

        boolean dentro= false;

        for(InfoProducto p: infoProductoRepository.findAll() ){
            if(p.getProducto().getId() == id){
                dentro=true;
            }
        }

        if(dentro){
           Integer i = infoProductoRepository.findByProducto(producto).get().getId();
           infoProductoRepository.deleteById(i);
        }
              
        
       
        double total = 0;

        for(InfoProducto p: infoProductoRepository.findAll() ){
            
                total = p.getTotal() + total;    
        }
       
        pedido.setImporte(total);
        pedidoRepository.save(pedido);

        model.addAttribute("infos", infoProductoRepository.findAll());
        model.addAttribute("vendedor",producto.getUsuario().getId());
        model.addAttribute("pedido", pedido);


        
        
        return "/comprador/carrito";
    }



    @GetMapping("/comprador/pedido") 
    public String verPedido(Model model){

        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        Usuario vendedor = new Usuario(2, "Supermercado BM","demo","supermarket.bm@gmail.com","C. de la Cruz, 23, 28012 Madrid","655432518","vendedor",false);

        model.addAttribute("infos", infoProductoRepository.findAll());
        model.addAttribute("pedido", pedido);
        model.addAttribute("comprador", comprador);
        model.addAttribute("vendedor", vendedor);

        return "comprador/pedido";
    }

    @GetMapping("/comprador/pedidoGuardado") 
    public String guardarPedido(){

        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        pedido.setUsuario(comprador);
        pedidoRepository.save(pedido);

        for(InfoProducto info: infoProductoRepository.findAll()){
            info.setPedido(pedido);
            infoProductoRepository.save(info);
        }

        infoProductoRepository.deleteAll();
        pedido= new Pedido();
        


        return "comprador/pago";
    }

    

    @PostMapping("/comprador/finalPedido") 
    public String pago(){
        return "redirect:/comprador";
    }


    
}
