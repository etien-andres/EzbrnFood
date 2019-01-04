package DAL.Entidades;

public class Ventas {
    Long id;
    String usr;
    float total;
    String fecha,mesa;

    public Ventas(String  usr, String mesa, float total, String fecha) {
        this.usr = usr;
        this.mesa = mesa;
        this.total = total;
        this.fecha = fecha;

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
