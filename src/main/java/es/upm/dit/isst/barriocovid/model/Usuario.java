package es.upm.dit.isst.barriocovid.model;
import java.util.*;

import javax.persistence.*;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String contraseña;
    private String email;
    private String direccion;
    private String telefono;
    private String tipo;
    private boolean esvulnerable; 

    @OneToMany(mappedBy = "usuario")
    private List<Producto> productos;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> pedidos;
    
    public Usuario() {
    }
    
    public Usuario(Integer id, String nombre, String contraseña, String email, String direccion,
            String telefono, String tipo, boolean esvulnerable) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.contraseña = contraseña;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.tipo = tipo;
        this.esvulnerable = false; 
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
    
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    
    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public boolean getEsvulnerable() {
        return esvulnerable;
    }
    public void setEsvulnerable(boolean esvulnerable) {
        this.esvulnerable = esvulnerable;
    }

    @Override
    public String toString() {
        return "Usuario [contraseña=" + contraseña + ", direccion=" + direccion + ", email=" + email + ", id=" + id
                + ", nombre=" + nombre + ", pedidos=" + pedidos + ", productos=" + productos + ", telefono=" + telefono
                + ", tipo=" + tipo + ", esvulnerable=" + esvulnerable + "]";
    }
    
}
