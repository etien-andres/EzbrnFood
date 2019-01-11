package com.example.esferatech.barnfoodv11;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.ArrayList;

import DAL.Entidades.Categorias;
import DAL.Entidades.Cuentas;
import DAL.Entidades.Mesas;
import DAL.Entidades.Modificadores;
import DAL.Entidades.Prod_comandado;
import DAL.Entidades.Product;
import DAL.Entidades.Ubicacion;
import DAL.Entidades.Usuarios;
import DAL.Sqlitehelp;

public class Estaticas {

    public static SQLiteDatabase db;
    public static boolean editandoprod;
    public static boolean editandoubis;
    public static boolean editandomesa;
    public static Mesas nombremesaact;

    public static boolean editandocate;

    static ArrayList<String> catesselect=new ArrayList<>();

    public static ArrayList<Product> prodsu=new ArrayList<>();

    public static int conteo=0;
    public static Integer[] imgubi={R.drawable.principal,R.drawable.bar,R.drawable.terraza};
    public static Ubicacion curubi;
    public static boolean editandoUsers=false;
    public static Usuarios user_Current;
    public static Usuarios usuario_actual;
    public static Product product_Current;
    public static Categorias categoria_current;
    public static ArrayList<Product> prod_sin_proc=new ArrayList<>();
    public static boolean coming_from_cobrar=false;
    public static void settitle(Activity act, String str){
        TextView title_bar;
        title_bar=act.findViewById(R.id.titulo_toolbar);
        title_bar.setText(str);
    }

    public static Product producto_comandado;
    public static int id_deprod_comand;
    public static Prod_comandado prod_comandado;
    public static Modificadores modprod_comanded;
}
