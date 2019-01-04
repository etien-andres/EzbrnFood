package DAL.Entidades;

public class Modificadores {
    Long id;
    String nombre;
    Float precio;

    public Modificadores( String nombre, Float precio) {

        this.nombre = nombre;
        this.precio = precio;
    }

    public Float getPrecio() {
        return precio;
    }

    public void setPrecio(Float precio) {
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
