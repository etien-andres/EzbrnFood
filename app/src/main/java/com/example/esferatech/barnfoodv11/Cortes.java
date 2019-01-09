package com.example.esferatech.barnfoodv11;

import android.content.Context;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DAL.Entidades.Ventas;
import DAL.Sqlitehelp;

public class Cortes extends AppCompatActivity {
    GridView listaventas;
    Sqlitehelp helper;
    ImageButton back;
    TextView date_today,grantotal;
    Date date;
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String fechaformateada;
    String solo_fecha="";
    Button changefecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cortes);
        changefecha=findViewById(R.id.change_fecha);
        grantotal=findViewById(R.id.gran_total);
        listaventas=findViewById(R.id.grid_ventas);
        date=new Date();
        fechaformateada=format.format(date);
        solo_fecha=get_only_fecha(fechaformateada);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Estaticas.settitle(this,"Movimientos");
        listaventas.setAdapter(new Adprventas(filtrarfecha()));
        date_today=findViewById(R.id.date_actual);


        grantotal.setText(calcuar_gran_tota().toString());


        date_today.setText(solo_fecha);


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
            total.setText("$"+Float.toString(ventas.get(position).getTotal()));
            user.setText(ventas.get(position).getUsr());
            mesa.setText(ventas.get(position).getMesa());
            return view;
        }
    }

    public Double calcuar_gran_tota(){
        Double a=0D;
        ArrayList<Ventas> ventas=filtrarfecha();
        for (Ventas v:ventas) {
            a+=v.getTotal();
        }
        return a;

    }

    public ArrayList<Ventas> filtrarfecha(){
        ArrayList<Ventas> ventas=helper.getVentas(Estaticas.db);
        ArrayList<Ventas> filtrado=new ArrayList<>();

        for (Ventas v:ventas) {
           // System.out.println(get_only_fecha(v.getFecha())+" "+solo_fecha);

            if (get_only_fecha(v.getFecha()).equals(solo_fecha)){

                filtrado.add(v);
            }
        }
        return filtrado;

    }

    public String get_only_fecha(String fechacompleta){
        String str="";
        for (int i = 0; i <10 ; i++) {
            str+=fechacompleta.charAt(i);
        }
        return str;
    }
}
