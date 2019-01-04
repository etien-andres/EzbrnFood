package com.example.esferatech.barnfoodv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import DAL.Entidades.Categorias;
import DAL.Sqlitehelp;

public class show_categorias extends AppCompatActivity {
    ImageButton back;
    EditText nombre;
    RadioGroup grupo;
    Button add_cate;
    Switch switch_editar;
    Sqlitehelp help;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         help=new Sqlitehelp(getApplicationContext(),"base",null,1);

        setContentView(R.layout.activity_show_categorias);
        back=findViewById(R.id.botonback);
        grupo=findViewById(R.id.radiogroupcolor);
        nombre=findViewById(R.id.nombre_cate_edit);
        add_cate=findViewById(R.id.add_cate);
        switch_editar=findViewById(R.id.switch_editCate);
        check();
        switch_editar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                set_state(isChecked);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estaticas.editandocate=false;
                Estaticas.categoria_current=null;
                finish();
            }
        });

        add_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (create()!=null){
                    if (Estaticas.editandocate){
                        help.updatecate(Estaticas.db,create());
                        clear();
                        show_cates.setcates();
                    }else{
                        help.insertcate(Estaticas.db,create());
                        clear();
                        show_cates.setcates();
                    }
                }
            }
        });

    }

    public void check(){
        if (Estaticas.editandocate){
            Estaticas.settitle(this,"Editando Categoría");

            add_cate.setText("Editar");
            setcate();
            grupo.setEnabled(false);
            nombre.setEnabled(false);
            add_cate.setEnabled(false);
        }
        else {
            Estaticas.settitle(this,"Agregando Categoría");

            switch_editar.setVisibility(View.INVISIBLE);
            grupo.check(R.id.redradio);
        }
    }
    public void set_state(boolean state){
        nombre.setEnabled(state);
        grupo.setEnabled(state);
        add_cate.setEnabled(state);
    }

    public void setcate(){
        nombre.setText(Estaticas.categoria_current.getNombre());
        switch (Estaticas.categoria_current.getColor()){
            case "red":
                grupo.check(R.id.redradio);
                break;
            case "yellow":
                grupo.check(R.id.yellowradio);
                break;
            case "orange":
                grupo.check(R.id.orangerad);
                break;
            case "blue":
                grupo.check(R.id.blueradio);
                break;
            case "purple":
                grupo.check(R.id.purpleradio);
                break;
        }
    }

    public Categorias create(){
        Categorias cate=null;
        if (Estaticas.editandocate){
            if (nombre.getText().toString().equals("")){
                Toast toast=Toast.makeText(getApplicationContext(),"Debe tener un nombre",Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }
            else {
                Estaticas.categoria_current.setNombre(nombre.getText().toString());
                switch (grupo.getCheckedRadioButtonId()){
                    case R.id.redradio:
                        Estaticas.categoria_current.setColor("red");
                        break;
                    case R.id.yellowradio:
                        Estaticas.categoria_current.setColor("yellow");
                        break;
                    case R.id.purpleradio:
                        Estaticas.categoria_current.setColor("purple");
                        break;
                    case R.id.blueradio:
                        Estaticas.categoria_current.setColor("blue");
                        break;
                    case R.id.orangerad:
                        Estaticas.categoria_current.setColor("orange");
                        break;
                }
                return Estaticas.categoria_current;
            }
        }else {
            if (nombre.getText().toString().equals("")){
                Toast toast=Toast.makeText(getApplicationContext(),"Debe tener un nombre",Toast.LENGTH_SHORT);
                toast.show();
                return null;
            }
            else{
                cate=new Categorias(nombre.getText().toString());
                switch (grupo.getCheckedRadioButtonId()){
                    case R.id.redradio:
                        cate.setColor("red");
                        break;
                    case R.id.yellowradio:
                        cate.setColor("yellow");
                        break;
                    case R.id.purpleradio:
                        cate.setColor("purple");
                        break;
                    case R.id.blueradio:
                        cate.setColor("blue");
                        break;
                    case R.id.orangerad:
                        cate.setColor("orange");
                        break;
                }
                Toast toast=Toast.makeText(getApplicationContext(),"Categoria creada",Toast.LENGTH_SHORT);
                toast.show();
                return cate;
            }
        }


    }

    public void clear(){
        nombre.setText("");
    }

}
