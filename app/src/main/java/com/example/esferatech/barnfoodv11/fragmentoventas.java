package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Mesas;
import DAL.Entidades.Ubicacion;
import DAL.Sqlitehelp;


public class fragmentoventas extends Fragment {
    static Adaptador adaptador;
    GridView gridView;
    ArrayList<Mesas> mesas;
    ArrayList<Ubicacion> ubicacions;
    RecyclerView recviewubi;
    Sqlitehelp helper;
    Boolean[] slectUbi;
    ImageView imagenpri;
    TextView nombreubi;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         helper=new Sqlitehelp(getContext(),"base",null,1);
        MainActivity.changetitle("Venta");
        //helper.returnubication(E)



        ubicacions=helper.returnubicationfull(Estaticas.db);
        if (ubicacions.size()>0){
            slectUbi=new Boolean[ubicacions.size()];
            //mesas=helper.returnmesas(Estaticas.db);
            fillubislc();
            setubislc(0);
            for (int i = 0; i <slectUbi.length ; i++) {
                if (slectUbi[i]) {
                    mesas=helper.returnmesas(Estaticas.db,ubicacions.get(i).getNombre(),getContext());
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_fragmentoventas, container, false);
        if (ubicacions.size()>0&&mesas.size()>0){
            gridView=(GridView) view.findViewById(R.id.gridviewer);
            imagenpri=view.findViewById(R.id.imagenubic);
            recviewubi=view.findViewById(R.id.cates_reci);
            LinearLayoutManager layoutManager =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
            recviewubi.setLayoutManager(layoutManager);
            nombreubi=view.findViewById(R.id.titulo_ubi);
            RecViewadap recViewadap=new RecViewadap(ubicacions);
            //recviewubi.setBackgroundColor(getResources().getColor(R.color.colorgris));
            recviewubi.setAdapter(recViewadap);
            for (int i = 0; i <slectUbi.length ; i++) {
                if (slectUbi[i]) {
                    imagenpri.setImageResource(Integer.parseInt(ubicacions.get(i).getPhoto()));
                    nombreubi.setText(ubicacions.get(i).getNombre().toUpperCase());
                }
            }
            setmesas();
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Estaticas.nombremesaact=mesas.get(position);
                    for (int i = 0; i <slectUbi.length ; i++) {
                        if (slectUbi[i]) Estaticas.curubi=ubicacions.get(i);
                    }
                    Intent intent=new Intent(getActivity(),venta_1.class);
                    startActivity(intent);
                }
            });
        }

        return view;
    }
    public void setmesas(){
        adaptador=new Adaptador(mesas);
        gridView.setAdapter(adaptador);
    }


    public static void updategridmesa(){
        adaptador.notifyDataSetChanged();
    }


    public void fillubislc(){
        for (int i = 0; i <slectUbi.length ; i++) {
            slectUbi[i]=false;
        }
    }
    public void setubislc(int x){
        fillubislc();
        slectUbi[x]=true;

    }



    public class RecViewadap extends RecyclerView.Adapter<fragmentoventas.RecViewadap.viewHolder>{

        ArrayList <Ubicacion> listaubi;

        Boolean[] ubiselec;



        public RecViewadap(ArrayList<Ubicacion> listaubi) {
            this.listaubi = listaubi;
            ubiselec=new Boolean[listaubi.size()];
            //llenar(1);
        }








        @NonNull
        @Override
        public fragmentoventas.RecViewadap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view=LayoutInflater.from(getContext()).inflate(R.layout.botonubi,parent, false);

            // Button but=view.findViewById(R.id.botoncate);

            return new fragmentoventas.RecViewadap.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final fragmentoventas.RecViewadap.viewHolder viewHolder, final int i) {


            viewHolder.botoncate.setText(listaubi.get(i).getNombre());
            if (slectUbi[i]) viewHolder.border.setBackgroundColor(getResources().getColor(R.color.blanco));
            else viewHolder.border.setBackgroundColor(getResources().getColor(R.color.colorgris));
            viewHolder.botoncate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setubislc(i);
                    notifyDataSetChanged();

                    mesas=helper.returnmesas(Estaticas.db,ubicacions.get(i).getNombre(),getContext());
                    imagenpri.setImageResource(Integer.parseInt(ubicacions.get(i).getPhoto()));
                    nombreubi.setText(ubicacions.get(i).getNombre());

                    setmesas();
                }
            });



        }

        @Override
        public int getItemCount() {
            return listaubi.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
            Button botoncate;
            LinearLayout border;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                botoncate=itemView.findViewById(R.id.botoncate);
                border=itemView.findViewById(R.id.botoncateborder);


            }


        }

    }




    public class Adaptador extends BaseAdapter{

        ArrayList<Mesas> listademesas;

        public Adaptador(ArrayList<Mesas> listademesas) {
            this.listademesas = listademesas;
        }

        @Override
        public int getCount() {
            return listademesas.size();
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
            View view=inflater.inflate(R.layout.mesaboton,null);
            TextView texto=(TextView)view.findViewById(R.id.textomesa);
            ImageView image=(ImageView) view.findViewById(R.id.imagenmesa);
            CardView tarjet=view.findViewById(R.id.tarjeta);
            String str="";
            if (listademesas.get(position).getNombre().length()>5){
                 str="";
                char [] nov=listademesas.get(position).getNombre().toCharArray();
                for (int i = 0; i <5 ; i++) {
                    str+=nov[i];
                }
                texto.setText(str);
            }
            else texto.setText(listademesas.get(position).getNombre());

            if (listademesas.get(position).getStatus()==1){
                tarjet.setCardBackgroundColor(Color.parseColor("#2c9e44"));
            }
            if (listademesas.get(position).getStatus()==2){
                image.setImageResource(R.drawable.breakfast);

                tarjet.setCardBackgroundColor(Color.parseColor("#ff5d00"));
            }
            if (listademesas.get(position).getStatus()==3){
                image.setImageResource(R.drawable.money);
                tarjet.setCardBackgroundColor(Color.parseColor("#a4e0fc"));
            }




            return view;

        }
    }



}
