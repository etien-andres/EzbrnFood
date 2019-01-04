package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Modificadores;
import DAL.Sqlitehelp;

public class usuarios extends Activity {
    ImageButton back;
    Button add_mod;
    static GridView grd_mod;
    static Sqlitehelp helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        Estaticas.settitle(this,"Modificadores");
        back=findViewById(R.id.botonback);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        grd_mod=findViewById(R.id.grd_mod);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_mod=findViewById(R.id.add_mod);
        add_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Modificador_nuevo.class);
                startActivity(intent);
            }
        });
        update_mods();

    }



    public static class  mod_gridAdapter extends BaseAdapter{
        ArrayList<Modificadores> mods;


        public mod_gridAdapter(ArrayList<Modificadores> mods) {
            this.mods = mods;
        }

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
            LayoutInflater inflater=(LayoutInflater) parent.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.elemento_modificador,null);
            TextView nom_mod=view.findViewById(R.id.nombre_mod);
            nom_mod.setText(mods.get(position).getNombre());
            TextView precio=view.findViewById(R.id.precio_mod);
            precio.setText("$ "+Float.toString(mods.get(position).getPrecio()));
            ImageView erase=view.findViewById(R.id.erase_mod);
            erase.setImageResource(R.drawable.trash);
            erase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.deletemod(Estaticas.db,mods.get(position));
                    update_mods();
                }
            });
            return view;
        }
    }
    public static void update_mods( ){
        ArrayList<Modificadores> mods=helper.get_modificadores(Estaticas.db);
        mod_gridAdapter adapter=new mod_gridAdapter(mods);
        grd_mod.setAdapter(adapter);

    }


}
