package es.upm.dit.isst.barriocovid.model;
import javax.persistence.*;

@Entity
@Table(name="infos")
public class InfoProducto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Integer id;
    private String nombre;
    private double cantidad;
    private double precio;
    private double total; //cantidad * precio

    @ManyToOne
    private Pedido pedido;
    
    @ManyToOne
    private Producto producto;

    public InfoProducto() {
    }


    public InfoProducto(Integer id, String nombre, double cantidad, double precio, double total) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.total = total;
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

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    @Override
    public String toString() {
        return "InfoProducto [cantidad=" + cantidad + ", id=" + id + ", nombre=" + nombre + ", pedido=" + pedido
                + ", precio=" + precio + ", producto=" + producto + ", total=" + total + "]";
    }

}