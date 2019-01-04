package com.example.esferatech.barnfoodv11;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import DAL.Entidades.Suministros;
import DAL.Sqlitehelp;

public class registrar_sum extends AppCompatActivity {
    Sqlitehelp helper;
    EditText nombre,unic,univ,costo,relac;
    Button agregarsum;
    Toolbar toolbarpers;
    ImageButton botback;
    Button showsum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_sum);
        botback=findViewById(R.id.botonback);
        botback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        nombre=findViewById(R.id.nombr_prod);
        unic=findViewById(R.id.unidad_compra);
        univ=findViewById(R.id.unidad_venta);
        costo=findViewById(R.id.costo);
        relac=findViewById(R.id.relacioncv);
        showsum=findViewById(R.id.showsum);
        agregarsum=findViewById(R.id.addsuministro);
        agregarsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addsum();
            }
        });
        showsum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),showsuministros.class);
                startActivity(intent);
            }
        });
        Estaticas.settitle(this,"Suministro Nuevo");


    }

    public Long insert_sum(){
        Long id=-2L;
        if (validate()){
            Suministros sum=new Suministros(nombre.getText().toString(),unic.getText().toString(),univ.getText().toString(),Float.parseFloat(costo.getText().toString()),Float.parseFloat(relac.getText().toString()));
            id=helper.insertsumin(Estaticas.db,sum);
        }
        return id;
    }
    public boolean validate(){
        boolean valido=false;
        Toast tos;
        if (nombre.getText().toString().equals("")){
            tos=Toast.makeText(getApplicationContext(),"El nombre no puede estar vacío",Toast.LENGTH_SHORT);
            tos.show();
            return valido;

        }
        else {
            if (unic.getText().toString().equals("")){
                tos=Toast.makeText(getApplicationContext(),"La unidad de compra no puede estar vacía",Toast.LENGTH_SHORT);
                tos.show();
                return valido;
            }
            else {
                if (univ.getText().toString().equals("")){
                    tos=Toast.makeText(getApplicationContext(),"La unidad de venta no puede estar vacía",Toast.LENGTH_SHORT);
                    tos.show();
                    return valido;
                }
                else {
                    if (costo.getText().toString().equals("")){
                        tos=Toast.makeText(getApplicationContext(),"El costo no puede estar vacío",Toast.LENGTH_SHORT);
                        tos.show();
                        return valido;
                    }
                    else {
                        if (relac.getText().toString().equals("")){
                            tos=Toast.makeText(getApplicationContext(),"La relación no puede estar vacía",Toast.LENGTH_SHORT);
                            tos.show();
                            return valido;
                        }
                        else valido=true;
                    }
                }
            }

        }
        return valido;
    }
    public void addsum(){
        if (insert_sum()>0){
            clear();
            Toast tst=Toast.makeText(getApplicationContext(),"Suministro agregado",Toast.LENGTH_SHORT);
            tst.show();
        }
        else {
            Toast tst2=Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);
            tst2.show();
        }
        showsuministros.setsumin(getApplicationContext());
    }
    public void clear(){
        nombre.setText("");
        unic.setText("");
        univ.setText("");
        costo.setText("");
        relac.setText("");

    }
}
