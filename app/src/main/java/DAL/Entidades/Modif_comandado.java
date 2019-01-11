package DAL.Entidades;

import java.util.ArrayList;

public class Modif_comandado {
    Modificadores mod;
    ArrayList<Modif_comandado> mod_de_mod;

    public Modif_comandado() {
        mod_de_mod=new ArrayList<>();
    }

    public Modificadores getMod() {
        return mod;
    }

    public void setMod(Modificadores mod) {
        this.mod = mod;
    }

    public ArrayList<Modif_comandado> getMod_de_mod() {
        return mod_de_mod;
    }

    public void setMod_de_mod(ArrayList<Modif_comandado> mod_de_mod) {
        this.mod_de_mod = mod_de_mod;
    }
    public void addmod_de_mod(Modif_comandado mod){
        mod_de_mod.add(mod);
    }

}
