package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.vanstone.trans.api.SystemApi;
import com.vanstone.trans.api.constants.GlobalConstants;
import com.vanstone.utils.CommonConvert;

import DAL.Entidades.Product;


public class Administracion extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    GridView grid_nav;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_administracion, container, false);
        grid_nav=view.findViewById(R.id.navegacion_admin);
        AdaptadorNAv adaptadorNAv=new AdaptadorNAv();

        grid_nav.setAdapter(adaptadorNAv);
        grid_nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(getContext(),users_show.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2=new Intent(getContext(),viewallprods.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        Intent intent3=new Intent(getContext(),show_cates.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4 =new Intent(getContext(),showsuministros.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5=new Intent(getContext(),usuarios.class);
                        startActivity(intent5);
                        break;
                    case 5:
                        Intent intent6=new Intent(getContext(),mesas.class);
                        startActivity(intent6);
                        break;

                }
            }
        });

        return  view;
    }
    public class AdaptadorNAv extends BaseAdapter {


        public AdaptadorNAv() {
        }

        @Override
        public int getCount() {
            return 6;
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
            View view=inflater.inflate(R.layout.botonnavegacion,null);
            TextView texto=view.findViewById(R.id.usr);
            ImageView img=view.findViewById(R.id.imagennav);
            CardView cardView=view.findViewById(R.id.cardviewnav);
            switch (position){
                case 0:
                    texto.setText("Usuarios");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.moradotr));
                    img.setImageResource(R.drawable.users_icon);
                    break;
                case 1:
                    texto.setText("Productos");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.azul23));
                    img.setImageResource(R.drawable.products);

                    break;
                case 2:
                    texto.setText("Categorias");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.naranja_claro));
                    img.setImageResource(R.drawable.categorias);

                    break;
                case 3:
                    texto.setText("Suministros");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.amarillotr));
                    img.setImageResource(R.drawable.suministros);

                    break;
                case 4:
                    texto.setText("Modificadores");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.amarillo_olivo));
                    img.setImageResource(R.drawable.modificadores);

                    break;
                case 5:
                    texto.setText("Mapa de Mesas");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.azultr));
                    img.setImageResource(R.drawable.table);

                    break;
            }




            return view;

        }
    }

}
