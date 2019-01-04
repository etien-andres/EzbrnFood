package DAL.Entidades;

public class Usuarios {
    Long id;
    String usr,pwd,nombre,contacto,nivel;
    int tipo;

    public Usuarios( String usr, String pwd, String nombre, String contacto, String nivel, int tipo) {
        this.tipo=tipo;
        this.usr = usr;
        this.pwd = pwd;
        this.nombre = nombre;
        this.contacto = contacto;
        this.nivel = nivel;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
}
