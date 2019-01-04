package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Suministros;
import DAL.Sqlitehelp;

public class showsuministros extends AppCompatActivity {
    static GridView gridsumins;
    AutoCompleteTextView busquedasumin;
    static Sqlitehelp helper;
    static ArrayList<Suministros> suministros =new ArrayList<>();
    Button new_sumin;
    ImageButton back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showsuministros);
        gridsumins=findViewById(R.id.grid_sumins);
        back=findViewById(R.id.botonback);
        busquedasumin=findViewById(R.id.busqueda_sumin);
        new_sumin=findViewById(R.id.new_sumin);
        helper=new Sqlitehelp(this,"base",null,1);
        setsumin(getApplicationContext());
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new_sumin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),registrar_sum.class);
                startActivity(intent);
            }
        });
        Estaticas.settitle(this,"Suministros");

    }

    public static void setsumin(Context ctx){
        suministros=helper.returnsumin(Estaticas.db);
        adapsumin adap=new adapsumin(suministros,ctx);
        gridsumins.setAdapter(adap);
    }


    public static class adapsumin extends BaseAdapter{
        ArrayList<Suministros> sumin;
        Context ctx;
        LayoutInflater layoutInflater;
        TextView numsum,nombresum1,unicom,univent,relacion,uven,ucom,precio2;
        ImageButton edit,delete;
        ImageView imgsum;
        public adapsumin(ArrayList<Suministros> sumin, Context ctx){
            this.sumin=sumin;
            this.ctx=ctx;
        }

        @Override
        public int getCount() {
            return sumin.size();
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
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.listadosumin,null);

            imgsum=view.findViewById(R.id.imagensumin);
            numsum=view.findViewById(R.id.idsum);
            nombresum1=view.findViewById(R.id.nombr_prod);
            unicom=view.findViewById(R.id.unicomp3);
            univent=view.findViewById(R.id.univenta);
            relacion=view.findViewById(R.id.relacion);
            uven=view.findViewById(R.id.univenta2);
            ucom=view.findViewById(R.id.unicomp);
            precio2=view.findViewById(R.id.precio1);
            edit=view.findViewById(R.id.editbuton);
            delete=view.findViewById(R.id.deletebuton);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.deletesumin(Estaticas.db,sumin.get(position));
                    setsumin(parent.getContext());
                }
            });
            numsum.setText(Long.toString(sumin.get(position).getId()));
            nombresum1.setText(sumin.get(position).getNombre());
            unicom.setText(sumin.get(position).getU_comp());
            univent.setText(sumin.get(position).getU_vent());
            relacion.setText(Float.toString(sumin.get(position).getUvxuc()));
            uven.setText(sumin.get(position).getU_vent());
            ucom.setText(sumin.get(position).getU_comp());
            precio2.setText("$"+Float.toString(sumin.get(position).getCosto()));



            return view;
        }
    }
}
