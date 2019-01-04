package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Categorias;
import DAL.Sqlitehelp;

public class show_cates extends AppCompatActivity {
    static Sqlitehelp sqlitehelp;
    static GridView cates_view;
    Button new_cate;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_cates);
        cates_view=findViewById(R.id.gid_cate);
        back=findViewById(R.id.botonback);
        sqlitehelp=new Sqlitehelp(getApplicationContext(),"base",null,1);
        new_cate=findViewById(R.id.add_cate);
        new_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),show_categorias.class);
                startActivity(intent);
            }
        });
        setcates();
        Estaticas.settitle(this,"Categorias");

        Estaticas.editandocate=false;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static class adaptadorCAtes extends BaseAdapter{
        ArrayList<Categorias> categorias;

        public adaptadorCAtes(ArrayList<Categorias> categorias) {
            this.categorias = categorias;
        }

        @Override
        public int getCount() {
            return categorias.size();
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
        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.botoncategoriaedita,null);
            TextView icat=view.findViewById(R.id.idcat);
            TextView nombrecate=view.findViewById(R.id.nombrecate);
            ImageButton colorimg=view.findViewById(R.id.colorimg);
            ImageButton editcate=view.findViewById(R.id.editcate);
            ImageButton erasecate=view.findViewById(R.id.erasecate);
            editcate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Estaticas.editandocate=true;
                    Categorias cate=new Categorias(categorias.get(position).getNombre());
                    cate.setColor(categorias.get(position).getColor());
                    cate.setId(categorias.get(position).getId());
                    Estaticas.categoria_current=cate;
                    Intent intent=new Intent(parent.getContext(),show_categorias.class);
                    parent.getContext().startActivity(intent);


                }
            });
            erasecate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            icat.setText(Long.toString(categorias.get(position).getId()));
            nombrecate.setText(categorias.get(position).getNombre());
            switch (categorias.get(position).getColor()){
                case "red":
                    colorimg.setBackgroundColor(parent.getResources().getColor(R.color.rojo));
                    break;
                case "yellow":
                    colorimg.setBackgroundColor(parent.getResources().getColor(R.color.amarillo));
                    break;
                case "orange":
                    colorimg.setBackgroundColor(parent.getResources().getColor(R.color.naranja));
                    break;
                case "blue":
                    colorimg.setBackgroundColor(parent.getResources().getColor(R.color.azul));
                    break;
                case "purple":
                    colorimg.setBackgroundColor(parent.getResources().getColor(R.color.morado));
                    break;
            }

            return view;
        }
    }


    public static void setcates(){
        ArrayList<Categorias> cates=sqlitehelp.getcate(Estaticas.db);
        adaptadorCAtes adaptadorCAtes=new adaptadorCAtes(cates);
        cates_view.setAdapter(adaptadorCAtes);

    }
}
