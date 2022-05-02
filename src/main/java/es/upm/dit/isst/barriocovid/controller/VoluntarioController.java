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

public class VoluntarioController {

    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;
    private final InfoProductoRepository infoProductoRepository;
    private final PedidoRepository pedidoRepository;

    private final Logger logger = LoggerFactory.getLogger(VendedorController.class);
        
    Usuario voluntario = new Usuario(6,"Pepe Jiménez","voluntario1","pepe.jimenez@gmail.com","Calle Alcalá, 60, 1B","678455321","voluntario",false);

    private boolean denegado=false;
    
    public VoluntarioController(ProductoRepository p, UsuarioRepository u, InfoProductoRepository i, PedidoRepository pe) {

        this.productoRepository = p;
        this.usuarioRepository = u;
        this.infoProductoRepository =i;
        this.pedidoRepository= pe;

    }

    @GetMapping("/voluntario/{idvoluntario}") 
    public String homeVoluntario(Model model, @PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);

        if(!denegado && pedido.getEstado() == 4) {
        model.addAttribute("vendedor", usuarioRepository.findById(2).get());
        model.addAttribute("comprador", usuarioRepository.findById(5).get());
        model.addAttribute("voluntario", voluntario.getId());
        return "voluntario/home";}
        return "voluntario/nopedido";
    }

    @GetMapping("/voluntario/{idvoluntario}/verPedido") 
    public String verPedidoVol(Model model, @PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);

        if(!denegado && pedido.getEstado() == 4) {
        model.addAttribute("vendedor", usuarioRepository.findById(2).get());
        model.addAttribute("comprador", usuarioRepository.findById(5).get());
        model.addAttribute("voluntario", voluntario.getId());
        model.addAttribute("infos", infoProductoRepository.findAll());
        logger.info("Estado voluntario aceptado1: {}", pedido.getEstado());
        return "voluntario/verpedido";
        }
        return "redirect:/voluntario/" + idvoluntario;
        
    }

    @GetMapping("/voluntario/{idvoluntario}/pedidoAceptado")
    public String aceptarPedidoVol(@PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);
        pedido.setEstado(5);
        pedidoRepository.save(pedido);
        denegado=false;
        logger.info("Estado voluntario aceptado2: {}", pedido.getEstado());
        return "redirect:/voluntario/" + idvoluntario;

    }

    @GetMapping("/voluntario/{idvoluntario}/pedidoDenegado")
    public String denegarPedidoVol(@PathVariable Integer idvoluntario){
        denegado=true;
        return "redirect:/voluntario/" + idvoluntario;

    }

    

}