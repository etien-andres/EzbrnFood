package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DAL.Entidades.Usuarios;
import DAL.Sqlitehelp;

public class users_show extends AppCompatActivity {

    Button add_usser;
   static GridView show_user;
    static ArrayList<Usuarios> usuarios;
   static Sqlitehelp helper;
    ImageButton back;
    static Adaptadorusers adaptadorusers;
    TextView title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_show);
        Estaticas.settitle(this,"Usuarios");

        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        add_usser=findViewById(R.id.add_user);
        show_user=findViewById(R.id.gid_users);
        usuarios=helper.getusers(Estaticas.db);
        adaptadorusers=new Adaptadorusers(usuarios);
        show_user.setAdapter(adaptadorusers);
        add_usser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),users_edit.class);
                startActivity(intent);
            }
        });

    }
    public static void voidupdateusrs(){
        usuarios=helper.getusers(Estaticas.db);
        adaptadorusers=new Adaptadorusers(usuarios);
        show_user.setAdapter(adaptadorusers);
        adaptadorusers.notifyDataSetChanged();
    }

    public static class Adaptadorusers extends BaseAdapter {

        ArrayList<Usuarios> users;
        public Adaptadorusers(ArrayList<Usuarios> users) {
            this.users=users;
        }

        @Override
        public int getCount() {
            return users.size();
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
        public View getView(final int position, View convertView, final ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.user_list,null);
            final TextView usr=view.findViewById(R.id.usr);
            ImageView imgusr=view.findViewById(R.id.user_img);
            TextView nomcomp=view.findViewById(R.id.nom_completo);
            TextView level=view.findViewById(R.id.tipo);
            ImageButton erase=view.findViewById(R.id.borrar_user);
            ImageButton edit=view.findViewById(R.id.edit_User);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Estaticas.user_Current=usuarios.get(position);
                    Estaticas.editandoUsers=true;
                    Intent intent=new Intent(parent.getContext(),users_edit.class);
                    parent.getContext().startActivity(intent);


                }
            });
            erase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.deleteuser(Estaticas.db,usuarios.get(position).getUsr());
                    voidupdateusrs();
                }
            });
            switch (users.get(position).getTipo()){
                case 1:
                    usr.setText(users.get(position).getUsr());
                    imgusr.setImageResource(R.drawable.admin);
                    nomcomp.setText(users.get(position).getNombre());
                    level.setText("Admin");
                    break;
                case 2:
                    usr.setText(users.get(position).getUsr());
                    imgusr.setImageResource(R.drawable.supervisor);
                    nomcomp.setText(users.get(position).getNombre());
                    level.setText("Super");
                    break;
                case 3:
                    usr.setText(users.get(position).getUsr());
                    imgusr.setImageResource(R.drawable.waiter);
                    nomcomp.setText(users.get(position).getNombre());
                    level.setText("Mesero");
                    break;
            }

            return view;

        }
    }
}
