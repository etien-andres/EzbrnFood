package com.example.esferatech.barnfoodv11;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DAL.Entidades.Categorias;
import DAL.Entidades.Mod_and_tipo;
import DAL.Entidades.Modificadores;
import DAL.Entidades.Product;
import DAL.Sqlitehelp;

public class edit_prods extends AppCompatActivity {
    EditText editnom, editPrec;
    Spinner catenprod;
    Spinner s;
    ImageView addsumin,addmod;
    Switch switch_editarprod;
    ImageButton back;
    Button agregarProd;
    String[] tipos;
    final Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);
    Activity activ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activ=this;
        setContentView(R.layout.activity_edit_prods);
        catenprod=findViewById(R.id.spinnercategs);
        addsumin=findViewById(R.id.add_sumin);
        addmod=findViewById(R.id.add_mod);
        agregarProd=findViewById(R.id.button3);
        back=findViewById(R.id.botonback);
        editnom=findViewById(R.id.nombreedit);
        editPrec=findViewById(R.id.precioedit);
        s=findViewById(R.id.spinnertipo);
        catenprod=findViewById(R.id.spinnercategs);


        switch_editarprod=findViewById(R.id.switch_editprod);
        setspinercate();
        tipos=new String[]{"Alimento","Bebida","Otro"};
        s=findViewById(R.id.spinnertipo);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        switch_editarprod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setstate(isChecked);
                if (isChecked){
                    addmod.setVisibility(View.INVISIBLE);
                    addmod.setEnabled(false);
                    addsumin.setVisibility(View.INVISIBLE);
                    addsumin.setEnabled(false);
                }
                else {
                    addmod.setVisibility(View.VISIBLE);
                    addmod.setEnabled(true);
                    addsumin.setVisibility(View.VISIBLE);
                    addsumin.setEnabled(true);
                }
            }
        });
        if (Estaticas.editandoprod){
            Estaticas.settitle(this,"Editando Producto");

            setstate(false);
            agregarProd.setText("Editar");
            fillprod();
            agregarProd.setEnabled(false);
            catenprod.setEnabled(false);

        }
        else {
            Estaticas.settitle(this,"Agregando Producto");
            Estaticas.catesselect.clear();
            switch_editarprod.setVisibility(View.INVISIBLE);
            addmod.setVisibility(View.INVISIBLE);
            addmod.setEnabled(false);
            addsumin.setVisibility(View.INVISIBLE);
            addsumin.setEnabled(false);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Estaticas.editandoprod=false;
            }

        });

        addmod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialogo_agrega_mods dialogo_agrega_mods=new Dialogo_agrega_mods(activ);
                dialogo_agrega_mods.show();
            }
        });

        agregarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Estaticas.editandoprod){
                    if (validate()){
                        helper.updateprod(Estaticas.db,createprod());
                        helper.setcatedeprod(Estaticas.db,createprod(),Estaticas.catesselect);
                        Toast toast=Toast.makeText(getApplicationContext(),"Producto editado satisfactoriamente!",Toast.LENGTH_SHORT);
                        toast.show();
                        clear();
                        viewallprods.setVistaprods(getApplicationContext());
                        finish();
                    }

                }else {
                    if (validate()){
                        Product pr=new Product(editnom.getText().toString(),Float.parseFloat(editPrec.getText().toString()),s.getSelectedItem().toString());
                        long a=helper.insertprod(Estaticas.db,pr);
                        helper.setcatedeprod(Estaticas.db,createprod(),Estaticas.catesselect);

                        if (a>0){
                            clear();
                            Toast toast=Toast.makeText(getApplicationContext(),"Producto agregado!",Toast.LENGTH_SHORT);
                            toast.show();
                            viewallprods.setVistaprods(getApplicationContext());
                        }
                        else {
                            Toast toast=Toast.makeText(getApplicationContext(),"Error al agregar!",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                }
            }
        });

    }
    public boolean validate(){
        if (!editnom.getText().toString().equals("")){
            if (!editPrec.getText().toString().equals("")){
                if (s.getSelectedItem()!=null){
                    return true;
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"Falta un tipo para el producto",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast=Toast.makeText(getApplicationContext(),"Falta un precio para el producto",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }


        }else {

            Toast toast=Toast.makeText(getApplicationContext(),"Falta un nombre para el producto",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }
    public Product createprod(){
        if (!Estaticas.editandoprod){
            Product prod=new Product(editnom.getText().toString(),Float.parseFloat(editPrec.getText().toString()),s.getSelectedItem().toString());
//            Toast toast=Toast.makeText(getApplicationContext(),"nuevo",Toast.LENGTH_SHORT);
//            toast.show();
            return prod;

        }
        else {
            Estaticas.product_Current.setNombre(editnom.getText().toString());
            Estaticas.product_Current.setPrecio(Float.parseFloat(editPrec.getText().toString()));
            Estaticas.product_Current.setTipo(tipos[s.getSelectedItemPosition()]);
//            Toast toast=Toast.makeText(getApplicationContext(),"edit",Toast.LENGTH_SHORT);
//            toast.show();
            return Estaticas.product_Current;

        }
    }
    public void clear(){
        editnom.setText("");
        editPrec.setText("");
        Estaticas.catesselect.clear();
        s.setSelection(0);
    }


    public void setstate(Boolean state){
        editPrec.setEnabled(state);
        editnom.setEnabled(state);
        s.setEnabled(state);
        catenprod.setEnabled(state);
        agregarProd.setEnabled(state);

    }
    public void fillprod(){
        editnom.setText(Estaticas.product_Current.getNombre());
        editPrec.setText(Float.toString(Estaticas.product_Current.getPrecio()));
        switch (Estaticas.product_Current.getTipo()){
            case "Alimento":
                s.setSelection(0);
                break;
            case "Bebida":
                s.setSelection(1);
                break;
            case "Otro":
                s.setSelection(2);
                break;
        }
        setspinercate();

    }

    public class AdaptadorSpinCate extends BaseAdapter {
        ArrayList<Categorias> listacate;
        Context context;
        LayoutInflater inflater;
        public AdaptadorSpinCate(ArrayList<Categorias> listacate,Context context) {
            this.listacate = listacate;
            this.context=context;
            inflater=(LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return listacate.size();
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

            View view=inflater.inflate(R.layout.spinerlistadecate,null);
            ConstraintLayout constraintLayout=view.findViewById(R.id.contenedorspincate);
            TextView nomcate=view.findViewById(R.id.texto_nom_ubi);
            nomcate.setText(listacate.get(position).getNombre());
            String color=listacate.get(position).getColor();
            if (color.equals("red")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ff0000"));
            }
            if (color.equals("yellow")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ffe500"));
            }
            if (color.equals("orange")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ff8300"));
            }
            if (color.equals("blue")){
                constraintLayout.setBackgroundColor(Color.parseColor("#5000a5ff"));
            }
            if (color.equals("purple")){
                constraintLayout.setBackgroundColor(Color.parseColor("#508300ff"));
            }
            CheckBox checkBox=view.findViewById(R.id.checkBoxubi);
            if (Estaticas.catesselect.contains(listacate.get(position).getNombre())){
                checkBox.setChecked(true);
            }


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Estaticas.catesselect.add(listacate.get(position).getNombre());
                    }
                    if (!isChecked){
                        Estaticas.catesselect.remove(listacate.get(position).getNombre());
                    }
                    setspinercate();
                }
            });


            return view;
        }
    }
    public void setspinercate(){
        Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);
        ArrayList<Categorias> categorias;
        categorias=helper.getcate(Estaticas.db);
        edit_prods.AdaptadorSpinCate spinCate=new edit_prods.AdaptadorSpinCate(categorias,getApplicationContext());
        catenprod.setAdapter(spinCate);
    }

    public class Dialogo_agrega_mods extends Dialog{
        GridView modific_grid;
        ArrayList<Mod_and_tipo> modificadores;
        ArrayList<Modificadores> modifs;
        ArrayList<Modificadores> modifs_deprod=new ArrayList<>();
        adap_mods adapt_mods;
        Activity c;
        TextView contador_mods;
        public Dialogo_agrega_mods(Activity a) {
            super(a);
            c=a;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);

            setContentView(R.layout.custom_dialog_agregarmods);
            contador_mods=findViewById(R.id.no_de_mods);
            Button cancel=findViewById(R.id.cancelar_add);
              setmod_deprod();
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            Button terminar=findViewById(R.id.agregar_mods);
            modific_grid=findViewById(R.id.mods_grid);
            modifs=helper.get_modificadores(Estaticas.db);
            adapt_mods=new adap_mods(modifs);
           modific_grid.setAdapter(adapt_mods);
            terminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

        }


        public void setmod_deprod(){
            modificadores=helper.get_mod_de_prod(Estaticas.db,Estaticas.product_Current.getNombre());
            for (Mod_and_tipo a:modificadores) {
                modifs_deprod.add(a.getMod());
            }
            contador_mods.setText(Integer.toString(modificadores.size()));
        }



        public class adap_mods extends BaseAdapter{

            public adap_mods(ArrayList<Modificadores> mods) {
                this.mods = mods;
            }

            ArrayList<Modificadores> mods;
            @Override
            public int getCount() {
                return mods.size();
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
                TextView nombre,precio;
                final Switch oblig_switch;
                CheckBox check_mod;
                View view=inflater.inflate(R.layout.elemento_modificador_menu_ad,null);
                nombre=view.findViewById(R.id.nombre_mod);
                precio=view.findViewById(R.id.precio_mod);
                oblig_switch=view.findViewById(R.id.oblig_switch);
                check_mod=view.findViewById(R.id.checked_mod);

                nombre.setText(mods.get(position).getNombre());
                precio.setText(mods.get(position).getPrecio().toString());
                int x=0;
                for (Modificadores a:modifs_deprod){
                    if (a.getNombre().equals(mods.get(position).getNombre())){
                        check_mod.setChecked(true);
                        if (modificadores.get(x).getTipo()==1){
                            oblig_switch.setChecked(false);
                        }
                        else {
                            oblig_switch.setChecked(true);

                        }
                    }
                    else check_mod.setChecked(false);
                    x++;
                }
                check_mod.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        int tip;
                        if (oblig_switch.isChecked()){
                            tip=2;
                        }
                        else tip=1;
                        if (isChecked){
                            helper.insert_mod_deprod(Estaticas.db,mods.get(position).getNombre(),Estaticas.product_Current.getNombre(),tip);
                            setmod_deprod();
                            adapt_mods.notifyDataSetChanged();
                            Toast toast=Toast.makeText(getApplicationContext(),"agregado",Toast.LENGTH_SHORT);
                            toast.show();

                        }
                        else {
                            helper.delete_mod_deprod(Estaticas.db,mods.get(position).getNombre(),Estaticas.product_Current.getNombre());
                            setmod_deprod();
                            adapt_mods.notifyDataSetChanged();
                            Toast toast=Toast.makeText(getApplicationContext(),"quitado",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });


                return view;
            }
        }



    }



}
