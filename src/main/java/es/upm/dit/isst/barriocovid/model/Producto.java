package es.upm.dit.isst.barriocovid.model;
import javax.persistence.*;

@Entity
@Table(name="productos")

public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private double precio;
    private int cantidad;

    @ManyToOne
    private Usuario usuario;  

    public Producto() {
    }
    
    

    public Producto(Integer id, String nombre,  double precio, int cantidad
          ) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;   
    }

  

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

   

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Producto [cantidad=" + cantidad + ", id=" + id + ", nombre=" + nombre + ", precio=" + precio
                +  "]";
    }


    
}
