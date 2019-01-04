package DAL.Entidades;

import java.util.ArrayList;

public class Cuentas {
    public ArrayList<Product> listadeprod;
    String mesa;
    Double total;
    public Cuentas(String m) {
        listadeprod=new ArrayList<>();
        mesa=m;
        total=0.0;
    }

    public ArrayList<Product> getListadeprod() {
        return listadeprod;
    }

    public void setListadeprod(ArrayList<Product> listadeprod) {
        this.listadeprod = listadeprod;
    }

    public String getMesa() {
        return mesa;
    }

    public void setMesa(String mesa) {
        this.mesa = mesa;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
