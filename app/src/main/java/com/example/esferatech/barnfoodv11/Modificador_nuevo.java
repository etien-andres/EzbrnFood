package com.example.esferatech.barnfoodv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import DAL.Entidades.Modificadores;
import DAL.Sqlitehelp;

public class Modificador_nuevo extends AppCompatActivity {
    ImageButton back;
    EditText nombre,precio;
    Button add_mod;
    Sqlitehelp helper;
    float precio_;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificador_nuevo);
        back=findViewById(R.id.botonback);
        add_mod=findViewById(R.id.add_mod);
        nombre=findViewById(R.id.nombre_mod);
        precio=findViewById(R.id.precio_mod);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        Estaticas.settitle(this,"Agregar Modificador");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check()){
                    insert_mod();
                    clear();
                }
                else {

                }

            }
        });




    }

    public boolean check(){
        if (nombre.getText().toString().equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"Debe tener un nombre",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }else {
            if (precio.getText().toString().equals("")){
                precio_=0;
            }
            else {
                precio_=Float.parseFloat(precio.getText().toString());
            }
            return true;
        }
    }
    public void insert_mod(){
        Long id=helper.insertmod(Estaticas.db,new Modificadores(nombre.getText().toString(),precio_));
        if (id>0) {
            Toast tos=Toast.makeText(getApplicationContext(),nombre.getText().toString()+", $"+ precio_+" agregado.",Toast.LENGTH_SHORT);
            tos.show();
            usuarios.update_mods();
            finish();

        }
        else {
            Toast tos=Toast.makeText(getApplicationContext(),"Error al agregar, ya existe el nombre.",Toast.LENGTH_SHORT);
            tos.show();
        }
    }

    public void clear(){
        nombre.setText("");
        precio.setText("");
    }


}
