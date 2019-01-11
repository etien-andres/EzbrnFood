package DAL.Entidades;

public class Mod_and_tipo {
    Modificadores mod;
    Integer tipo;

    public Mod_and_tipo(Modificadores mod, Integer tipo) {
        this.mod = mod;
        this.tipo = tipo;
    }

    public Modificadores getMod() {
        return mod;
    }

    public void setMod(Modificadores mod) {
        this.mod = mod;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}
