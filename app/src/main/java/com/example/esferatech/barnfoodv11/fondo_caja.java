package com.example.esferatech.barnfoodv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import DAL.Sqlitehelp;

public class fondo_caja extends AppCompatActivity {
    ImageButton back;
    TextView fondo;
    Sqlitehelp helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fondo_caja);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        back=findViewById(R.id.botonback);
        fondo=findViewById(R.id.fondo_actual);
       back.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });

        fondo.setText(Double.toString(helper.get_fondo(Estaticas.db)));

    }
}
