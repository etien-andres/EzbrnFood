package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class supervision extends Fragment {

    GridView grid_nav;
    ImageButton back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainActivity.changetitle("Supervisión");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_supervision, container, false);
        grid_nav=view.findViewById(R.id.superv_grid);

        AdaptNav adaptNav=new AdaptNav();
        grid_nav.setAdapter(adaptNav);
        grid_nav.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent=new Intent(getContext(),fondo_caja.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent2=new Intent(getContext(),Cortes.class);
                        startActivity(intent2);
                        break;
                }
            }
        });



        return view;
    }


    public class AdaptNav extends BaseAdapter{

        @Override
        public int getCount() {
            return 1;
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
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view=inflater.inflate(R.layout.botonnavegacion,null);
            TextView texto=view.findViewById(R.id.usr);
            ImageView img=view.findViewById(R.id.imagennav);
            CardView cardView=view.findViewById(R.id.cardviewnav);

            switch (position){
                case 0:
                    texto.setText("Fondo de caja");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.naranja_claro));
                    img.setImageResource(R.drawable.cashier);
                    break;
                case 1:
                    texto.setText("Movimientos");
                    cardView.setCardBackgroundColor(getResources().getColor(R.color.cafe_claro2));
                    img.setImageResource(R.drawable.lis);

            }

            return view;
        }
    }




}
