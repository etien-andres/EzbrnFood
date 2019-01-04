package com.example.esferatech.barnfoodv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import DAL.Entidades.Usuarios;
import DAL.Sqlitehelp;

public class users_edit extends AppCompatActivity {
    List<String> lista_niveles;
    String[] strFrutas;
    Spinner selector_nivel;
    ArrayAdapter<String> leveladapter;
    EditText usr,pwd,pwdconf,nombre,contacto;
    Button add_user;
    Sqlitehelp helper;
    ImageButton back;
    Switch switch_editar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_edit);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        back=findViewById(R.id.botonback);
        usr=findViewById(R.id.usr_edit);
        pwd=findViewById(R.id.pwd_edit);
        pwdconf=findViewById(R.id.pwd_confi);
        nombre=findViewById(R.id.nom_complet);
        contacto=findViewById(R.id.contact_edit);
         lista_niveles=new ArrayList<>();
        strFrutas = new String[] {"Admin", "Super", "Mesero"};
        Collections.addAll(lista_niveles,strFrutas);
        selector_nivel=findViewById(R.id.spinnerlvl);
        //selector_nivel
        leveladapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item, lista_niveles);
        selector_nivel.setAdapter(leveladapter);
        add_user=findViewById(R.id.add_user_now);
        switch_editar=findViewById(R.id.switch_editar);
        if (Estaticas.editandoUsers){
            filluser();
            Estaticas.settitle(this,"Editando Usuario");

            add_user.setText("Editar");
            set_editable(false);
            switch_editar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    set_editable(isChecked);
                }
            });
        }else {
            Estaticas.settitle(this,"Agregando Usuario");
            switch_editar.setVisibility(View.INVISIBLE);
        }

        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check_user()){
                    if (Estaticas.editandoUsers){
                        helper.updateuser(Estaticas.db,createusr());
                        clear();
                        switch_editar.setEnabled(false);
                        Toast toast=Toast.makeText(getApplicationContext(),"Usuario editado!",Toast.LENGTH_SHORT);
                        toast.show();
                        Estaticas.editandoUsers=false;
                        users_show.voidupdateusrs();
                        finish();
                    }else {
                        helper.insertuser(Estaticas.db,createusr());
                        clear();
                        Toast toast=Toast.makeText(getApplicationContext(),"Usuario agregado!",Toast.LENGTH_SHORT);
                        toast.show();
                        users_show.voidupdateusrs();
                        finish();
                    }

                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Estaticas.editandoUsers=false;
                Estaticas.user_Current=null;
                finish();
            }
        });



    }


    public boolean check_user(){
        if (!usr.getText().toString().equals("")){
            if (!pwd.getText().toString().equals("")){
                if (!pwdconf.getText().toString().equals("")){
                    if (!contacto.getText().toString().equals("")){
                        if (!nombre.getText().toString().equals("")){
                            if (pwd.getText().toString().equals(pwdconf.getText().toString())){
                                if (selector_nivel.getSelectedItem()!=null){
                                    return true;
                                }
                                else {
                                    Toast toast=Toast.makeText(getApplicationContext(),"Debe seleccionar un nivel",Toast.LENGTH_SHORT);
                                    toast.show();
                                    return false;
                                }
                            }else {
                                Toast toast=Toast.makeText(getApplicationContext(),"Los passwords deben coincidir",Toast.LENGTH_SHORT);
                                toast.show();
                                return false;
                            }
                        }else {
                            Toast toast=Toast.makeText(getApplicationContext(),"El nombre completo no puede estar vacío",Toast.LENGTH_SHORT);
                            toast.show();
                            return false;
                        }
                    }else {
                        Toast toast=Toast.makeText(getApplicationContext(),"El contacto no puede estar vacío",Toast.LENGTH_SHORT);
                        toast.show();
                        return false;
                    }
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"La confirmacion del pwd no puede estar vacío",Toast.LENGTH_SHORT);
                    toast.show();
                    return false;
                }
            }else {
                Toast toast=Toast.makeText(getApplicationContext(),"El password no puede estar vacío",Toast.LENGTH_SHORT);
                toast.show();
                return false;
            }
        }
        else {
            Toast toast=Toast.makeText(getApplicationContext(),"El nombre de usuario no puede estar vacío",Toast.LENGTH_SHORT);
            toast.show();
            return false;
        }
    }

    public Usuarios createusr(){
        int tipo=0;
        String level="";
        Usuarios usuario;
        switch (selector_nivel.getSelectedItemPosition()){
            case 0:
                tipo=1;
                level="Admin";
                break;
            case 1:
                tipo=2;
                level="Super";
                break;
            case 2:
                level="Mesero";
                tipo=3;
                break;
        }
        if (!Estaticas.editandoUsers){
             usuario=new Usuarios(usr.getText().toString(),pwd.getText().toString(),nombre.getText().toString(),contacto.getText().toString(),level,tipo);
            return usuario;
        }
        else {
            Estaticas.user_Current.setUsr(usr.getText().toString());
            Estaticas.user_Current.setPwd(pwd.getText().toString());
            Estaticas.user_Current.setContacto(contacto.getText().toString());
            Estaticas.user_Current.setNombre(nombre.getText().toString());
            Estaticas.user_Current.setNivel(level);
            Estaticas.user_Current.setTipo(tipo);
            return Estaticas.user_Current;
        }



    }
    public void clear(){
        usr.setText("");
        pwd.setText("");
        pwdconf.setText("");
        nombre.setText("");
        contacto.setText("");
        selector_nivel.setSelection(2);

    }
    public void set_editable(boolean valor){
        usr.setEnabled(valor);
        usr.setTextIsSelectable(valor);
        pwd.setEnabled(valor);
        pwdconf.setEnabled(valor);
        nombre.setEnabled(valor);
        contacto.setEnabled(valor);
        selector_nivel.setEnabled(valor);
        add_user.setEnabled(valor);
    }
    public void filluser(){
        usr.setText(Estaticas.user_Current.getUsr());
        pwd.setText(Estaticas.user_Current.getPwd());
        pwdconf.setText(Estaticas.user_Current.getPwd());
        nombre.setText(Estaticas.user_Current.getNombre());
        contacto.setText(Estaticas.user_Current.getContacto());
        switch (Estaticas.user_Current.getTipo()){
            case 1:
                selector_nivel.setSelection(0);
                break;
            case 2:
                selector_nivel.setSelection(1);
                break;
            case 3:
                selector_nivel.setSelection(2);
                break;
        }


    }
}
