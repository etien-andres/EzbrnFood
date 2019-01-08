package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Ventas;
import DAL.Sqlitehelp;

public class Cortes extends AppCompatActivity {
    GridView listaventas;
    Sqlitehelp helper;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cortes);
        listaventas=findViewById(R.id.grid_ventas);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Estaticas.settitle(this,"Movimientos");
        listaventas.setAdapter(new Adprventas(helper.getVentas(Estaticas.db)));


    }

    public class Adprventas extends BaseAdapter{
        ArrayList<Ventas> ventas;

        public Adprventas(ArrayList<Ventas> ventas) {
            this.ventas = ventas;
        }

        @Override
        public int getCount() {
            return ventas.size();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater=(LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.elemento_lista_ventas,null);
            TextView fecha,id,total,user,mesa;
            fecha=view.findViewById(R.id.date_venta);
            id=view.findViewById(R.id.id_venta);
            total=view.findViewById(R.id.total_vent);
            user=view.findViewById(R.id.user_venta);
            mesa=view.findViewById(R.id.mesa_venta);

            fecha.setText(ventas.get(position).getFecha());
            id.setText(Long.toString(ventas.get(position).getId()));
            total.setText(Float.toString(ventas.get(position).getTotal()));
            user.setText(ventas.get(position).getUsr());
            mesa.setText(ventas.get(position).getMesa());

            return view;
        }
    }
}
