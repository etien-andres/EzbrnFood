package DAL.Entidades;

public class Suministros {

    String nombre,u_comp,u_vent;
    float costo,uvxuc;
    Long id;

    public Suministros(String nombre, String u_comp, String u_vent, float costo, float uvxuc) {
        this.nombre = nombre;
        this.u_comp = u_comp;
        this.u_vent = u_vent;
        this.costo = costo;
        this.uvxuc = uvxuc;
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

    public String getU_comp() {
        return u_comp;
    }

    public void setU_comp(String u_comp) {
        this.u_comp = u_comp;
    }

    public String getU_vent() {
        return u_vent;
    }

    public void setU_vent(String u_vent) {
        this.u_vent = u_vent;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getUvxuc() {
        return uvxuc;
    }

    public void setUvxuc(float uvxuc) {
        this.uvxuc = uvxuc;
    }
}
