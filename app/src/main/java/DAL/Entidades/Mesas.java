package DAL.Entidades;

public class Mesas {
    String nombre, ubicacion;
    Integer status;
    Long idmesa;

    public Mesas(String nombre) {
        this.nombre = nombre;

    }

    public Long getIdmesa() {
        return idmesa;
    }

    public void setIdmesa(Long idmesa) {
        this.idmesa = idmesa;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
