package es.upm.dit.isst.barriocovid.model;

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double importe;
    private Integer estado;

    @ManyToOne
    private Usuario usuario; //Comprador
    
    @OneToMany(mappedBy = "pedido")
    private List<InfoProducto> infosProducto;

    public Pedido() {
    }
    
    public Pedido(Integer id, double importe, Integer estado) {
        super();
        this.id = id;
        this.importe = importe;
        this.estado = estado;
    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<InfoProducto> getInfosProducto() {
        return infosProducto;
    }

    public void setInfosProducto(List<InfoProducto> infosProducto) {
        this.infosProducto = infosProducto;
    }

    public Integer getEstado() {
            return estado;
        }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
    

    

   

    
}
