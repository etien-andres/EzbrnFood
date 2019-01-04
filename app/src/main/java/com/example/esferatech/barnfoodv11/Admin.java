package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Admin extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_admin, container, false);
        Button addprod=view.findViewById(R.id.definirprod);
        Button sumin=view.findViewById(R.id.sumins);
        sumin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),registrar_sum.class);
                startActivity(intent);
            }
        });

        addprod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),agregarprod.class);
                startActivity(intent);
            }
        });

        Button admap =view.findViewById(R.id.admap);
        admap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),mesas.class);
                startActivity(intent);
            }
        });




        return  view;
    }




}
