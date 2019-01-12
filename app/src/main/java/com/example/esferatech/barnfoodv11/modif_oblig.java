package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Mod_and_tipo;
import DAL.Entidades.Modif_comandado;
import DAL.Entidades.Modificadores;
import DAL.Entidades.Product;
import DAL.Sqlitehelp;


public class modif_oblig extends Fragment {
    GridView modif_obig_grid;
    Sqlitehelp helper;
    ArrayList<Mod_and_tipo> modificadores;
    Product product;
    boolean slected[];
    adapt_mods adapt_mods;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper=new Sqlitehelp(getContext(),"base",null,1);
        modificadores=helper.get_mod_de_prod(Estaticas.db,Estaticas.producto_comandado.getNombre());
        slected=new boolean[modificadores.size()];
        adapt_mods=new adapt_mods(modificadores);
        fillbool();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_modif_oblig,container,false);
        modif_obig_grid=view.findViewById(R.id.modif_oblig_grid);
        modif_obig_grid.setAdapter(adapt_mods);

        return view;

    }
    public void fillbool(){
        for (int i = 0; i <slected.length ; i++) {
            slected[i]=false;
        }
    }

    public void slect(int j){
        fillbool();
        slected[j]=true;
    }

    public class adapt_mods extends BaseAdapter{
        ArrayList<Mod_and_tipo> mod_and_tipos;


        public adapt_mods(ArrayList<Mod_and_tipo> mod_and_tipos) {
            this.mod_and_tipos = filtrarOblig(mod_and_tipos);
        }

        @Override
        public int getCount() {
            return mod_and_tipos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.elemento_modi_oblig,null);
            TextView nom,precio;
            RadioButton radioButton;

            nom=view.findViewById(R.id.nombre_mod);
            precio=view.findViewById(R.id.precio_mod);
            radioButton=view.findViewById(R.id.slected_mod);

            if (slected[position]){
                radioButton.setChecked(true);
            }
            else radioButton.setChecked(false);
            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        slect(position);
                        Modif_comandado modif_comandado=new Modif_comandado(Estaticas.id_deprod_comand);
                        modif_comandado.setMod(mod_and_tipos.get(position).getMod());
                        Estaticas.prod_comandado.clearmodoblig();
                         Estaticas.prod_comandado.addmod_oblig(modif_comandado);
                        adapt_mods.notifyDataSetChanged();
                }
            });
            nom.setText(mod_and_tipos.get(position).getMod().getNombre());
            precio.setText(mod_and_tipos.get(position).getMod().getPrecio().toString());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    slect(position);
                    Modif_comandado modif_comandado=new Modif_comandado(Estaticas.id_deprod_comand);
                    modif_comandado.setMod(mod_and_tipos.get(position).getMod());
                    Estaticas.prod_comandado.clearmodoblig();
                    Estaticas.prod_comandado.addmod_oblig(modif_comandado);
                    adapt_mods.notifyDataSetChanged();

                }
            });

            return view;
        }
    }

    public ArrayList<Mod_and_tipo> filtrarOblig(ArrayList<Mod_and_tipo> mod_and_tipos){
        ArrayList <Mod_and_tipo> modAndTipos=new ArrayList<>();
        for (Mod_and_tipo a:mod_and_tipos) {
            if (a.getTipo()==1){
                modAndTipos.add(a);
            }
        }
        return modAndTipos;
    }
}
