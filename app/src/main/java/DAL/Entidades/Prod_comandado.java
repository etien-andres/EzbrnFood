package DAL.Entidades;

import java.util.ArrayList;

public class Prod_comandado {
    Product product;
    int id_deventa;
    ArrayList<Modif_comandado> mod_oblig;
    ArrayList<Modif_comandado> mod_opcio;

    public Prod_comandado(int id_deventa) {
        this.id_deventa=id_deventa;
        mod_oblig=new ArrayList<>();
        mod_opcio=new ArrayList<>();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ArrayList<Modif_comandado> getMod_oblig() {
        return mod_oblig;
    }

    public void setMod_oblig(ArrayList<Modif_comandado> mod_oblig) {
        this.mod_oblig = mod_oblig;
    }

    public ArrayList<Modif_comandado> getMod_opcio() {
        return mod_opcio;
    }

    public void setMod_opcio(ArrayList<Modif_comandado> mod_opcio) {
        this.mod_opcio = mod_opcio;
    }
    public void addmod_oblig(Modif_comandado mod){
        mod_oblig.add(mod);
    }

    public void addmod_opc(Modif_comandado mod){
        mod_opcio.add(mod);
    }

    public void clearmodoblig(){
        mod_oblig.clear();
    }
}
