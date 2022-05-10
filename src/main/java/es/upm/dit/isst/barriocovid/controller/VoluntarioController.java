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
        model.addAttribute("voluntario", voluntario.getId());
        return "voluntario/home";
    }

    @GetMapping("/voluntario/{idvoluntario}/pedidos") 
    public String verpedidos(Model model, @PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);
        model.addAttribute("voluntario", voluntario.getId());
        if(!denegado && pedido.getEstado() == 4) {
        model.addAttribute("vendedor", usuarioRepository.findById(2).get());
        model.addAttribute("comprador", usuarioRepository.findById(5).get());
        return "voluntario/pedidos";
        }
        
        return "voluntario/nopedido";
        
    }




    @GetMapping("/voluntario/{idvoluntario}/verPedido") 
    public String verPedidoVol(Model model, @PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);
        model.addAttribute("voluntario", voluntario.getId());
        if(!denegado && pedido.getEstado() == 4) {
        model.addAttribute("vendedor", usuarioRepository.findById(2).get());
        model.addAttribute("comprador", usuarioRepository.findById(5).get());
        model.addAttribute("infos", infoProductoRepository.findAll());
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
        return "redirect:/voluntario/" + idvoluntario;

    }

    @GetMapping("/voluntario/{idvoluntario}/pedidoDenegado")
    public String denegarPedidoVol(@PathVariable Integer idvoluntario){
        denegado=true;
        return "redirect:/voluntario/" + idvoluntario;

    }

    
    @GetMapping("/voluntario/{idvoluntario}/pedidosconfirmados") 
    public String verpedidosconfirmados(Model model, @PathVariable Integer idvoluntario){
        List <Pedido> pedidos = (List<Pedido>) pedidoRepository.findAll();
        Pedido pedido = pedidos.get(0);
        model.addAttribute("voluntario", voluntario.getId());
        if(!denegado && pedido.getEstado() >= 5) {
        model.addAttribute("vendedor", usuarioRepository.findById(2).get());
        model.addAttribute("comprador", usuarioRepository.findById(5).get());
        model.addAttribute("infos", infoProductoRepository.findAll());
        model.addAttribute("estado", pedido.getEstado());
        return "voluntario/veraceptados";
        }
        return "voluntario/nopedido";
        
    }

    @PostMapping("/correoEnviado/voluntario")
    public String correoEnviadovol(Model model){
    return "voluntario/correoEnviado";
    }



    @GetMapping("/contacto/voluntario")
    public String contactovol(Model model){
    return "voluntario/contacto";
    }



}