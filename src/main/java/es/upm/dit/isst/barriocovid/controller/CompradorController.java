package es.upm.dit.isst.barriocovid.controller;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    private final Logger logger = LoggerFactory.getLogger(CompradorController.class);
        
    Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
    //Pedido pedido = new Pedido(1,0,0);
    Pedido pedido = new Pedido();

    public CompradorController(ProductoRepository p, UsuarioRepository u, InfoProductoRepository i, PedidoRepository pe) {

        this.productoRepository = p;
        this.usuarioRepository = u;
        this.infoProductoRepository =i;
        this.pedidoRepository= pe;

    }

    

    @GetMapping("/") 
    public String inicio(){
       
        return "redirect:/login"; 
    }

    @GetMapping("/perfil/prueba") 
    public String perfil(Model model){
        model.addAttribute("perfil", usuarioRepository.findById(5).get());
        return "perfil";
    }

    @PostMapping("/perfil") 
    public String editarPerfil(Usuario usuario){  
        usuarioRepository.save(usuario);
        return "redirect:/comprador/" + comprador.getId();  
    }

    

    @GetMapping("/comprador/{idcomprador}") 
    public String homeComprador(Model model, @PathVariable Integer idcomprador){

        model.addAttribute("vendedores", usuarioRepository.findByTipo("ROLE_VEND"));
        model.addAttribute("idcomprador", comprador.getId());
        

        


        return "comprador/home";
    }

    @PostMapping("/comprador/{idcomprador}/buscartienda")
    public String buscarTienda(@RequestParam String nombre, @PathVariable Integer idcomprador){
        Optional <Usuario> usuario = usuarioRepository.findByNombre(nombre);
        Integer idTienda=0;
        try{
           idTienda= usuario.get().getId();
           return "redirect:/comprador/" + comprador.getId() + "/tienda/" + idTienda;
        }
        catch(Exception e) {}
        return "redirect:/comprador/" + comprador.getId();
    }
    
    @GetMapping("/comprador/{idcomprador}/tienda/{id}") 
    public String tiendaSeleccionada(@PathVariable Integer id, Model model, @PathVariable Integer idcomprador){
        Usuario usuario = usuarioRepository.findById(id).get();
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        if (pedidos.isEmpty()){
            pedido.setId(1);
            pedido.setEstado(0);
            pedido.setUsuario(comprador);
            pedidoRepository.save(pedido);
        }
        logger.info("ID: {}", pedido.getId());
        logger.info("Estado 1: {}", pedido.getEstado());

        model.addAttribute("estado", pedido.getEstado());

        model.addAttribute("productos", productoRepository.findByUsuario(usuario));
        model.addAttribute("idcomprador", comprador.getId());
        
        return "comprador/productos_tiendas";
    }

    @PostMapping("/carrito") 
    public String añadirAlCarrito(@RequestParam Integer id, @RequestParam Integer cantidad, Model model){
        InfoProducto infoProducto = new InfoProducto();
        Producto producto = productoRepository.findById(id).get();
        double total = 0;
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
        } else {
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
        return "redirect:/comprador/" + comprador.getId() +"/tienda/"+ idVendedor;
    }

    @GetMapping("/verCarrito") 
    public String verCarrito(Model model){
        logger.info("Estos son los detalles de los productos {}",infoProductoRepository.findAll());
        model.addAttribute("infos", infoProductoRepository.findAll());
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        if (pedidos.isEmpty()){
            model.addAttribute("pedido", pedido);
        } else {
            model.addAttribute("pedido", pedidos.get(0));
        }

        model.addAttribute("idcomprador", comprador.getId());
        return "comprador/carrito";
    }

    @GetMapping ("/borrarCarrito/{id}")
    public String quitarDelCarrito(@PathVariable Integer id, Model model ){
        Producto producto = productoRepository.findById(id).get();
        boolean dentro= false;

        for(InfoProducto p: infoProductoRepository.findAll()){
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
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        
            pedido.setImporte(total);
            pedidoRepository.save(pedido);
        
        model.addAttribute("infos", infoProductoRepository.findAll());
        model.addAttribute("vendedor",producto.getUsuario().getId());
        if (pedidos.isEmpty()){
            model.addAttribute("pedido", pedido);
        } else {
            model.addAttribute("pedido", pedidos.get(0));
        }
        
        model.addAttribute("idcomprador", comprador.getId());
        
        return "/comprador/carrito";
    }

    @GetMapping("/comprador/{idcomprador}/pedido") 
    public String verPedido(Model model){

        Usuario vendedor = new Usuario(2, "Supermercado BM","demo","supermarket.bm@gmail.com","C. de la Cruz, 23, 28012 Madrid","655432518","vendedor",false);
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        
            pedido.setEstado(1);
            pedidoRepository.save(pedido);
        
        
        model.addAttribute("infos", infoProductoRepository.findAll());
        
        if (pedidos.isEmpty()){
            model.addAttribute("pedido", pedido);
        } else {
            model.addAttribute("pedido", pedidos.get(0));
        }
        
        model.addAttribute("comprador", comprador);
        model.addAttribute("vendedor", vendedor);

        return "comprador/pedido";
    }

    @GetMapping("/comprador/{idcomprador}/pedidoGuardado") 
    public String guardarPedido(Model model){

        Usuario comprador = new Usuario(5, "Lucia Garcia","demo","lucia.garcia@gmail.com","Calle Alcalá,15, 3A","655432518","comprador",false);
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
            pedido.setUsuario(comprador);
            for(InfoProducto info: infoProductoRepository.findAll()){
                info.setPedido(pedido);
                infoProductoRepository.save(info);
            }
            pedido.setEstado(2);
            pedidoRepository.save(pedido);
        
        model.addAttribute("idcomprador", comprador.getId());
        return "comprador/pago";
    }

    @GetMapping("/comprador/{idcomprador}/finalPedido/cancelar") 
    public String pagocancelado(){
        infoProductoRepository.deleteAll();
        return "redirect:/comprador/" + comprador.getId();
    }

    @GetMapping("/comprador/{idcomprador}/finalPedido/pagado") 
    public String pagopagado(Model model){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
            pedido.setEstado(3);
            pedidoRepository.save(pedido);
        
        Integer estado = pedido.getEstado();

        if (pedidos.isEmpty()){
            model.addAttribute("estado",estado);
        } else {
            model.addAttribute("estado", pedidos.get(0).getEstado());
        }
        model.addAttribute("idcomprador", comprador.getId());
        return "comprador/seguimiento";
    }

    @GetMapping("/comprador/{idcomprador}/verSeguimiento") 
    public String vermipedido(Model model){
        Integer estado = pedido.getEstado();
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);
        logger.info("Estado en el seguimiento: {}", pedido.getEstado());
       
        if (pedidos.isEmpty()){
            model.addAttribute("estado",estado);
        } else {
            model.addAttribute("estado", pedidos.get(0).getEstado());
        }
        model.addAttribute("idcomprador", comprador.getId());
        return "comprador/seguimiento";
    }

    @GetMapping("/comprador/{idcomprador}/estado8")
    public String estado8(Model model, @PathVariable Integer idcomprador){
    List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
    Pedido pedido = pedidos.get(0);
    pedido.setEstado(8);
    pedidoRepository.save(pedido);
    return "redirect:/comprador/" + idcomprador;

}

    @PostMapping("/correoEnviado/comprador")
    public String correoEnviadocom(Model model){
    return "comprador/correoEnviado";
    }



    @GetMapping("/contacto/comprador")
    public String contactocom(Model model){
    return "comprador/contacto";
    }

}
