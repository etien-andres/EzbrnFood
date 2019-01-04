package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import DAL.Entidades.Categorias;
import DAL.Entidades.Product;
import DAL.Sqlitehelp;

public class viewallprods extends AppCompatActivity {
    GridView vistaprodss;
    RecyclerView recyclerViewubi;
    TextView texto;
    static boolean[] cateselec;
    static ArrayList<Product> lis;
    static ArrayList<Categorias> categorias;
    static GridView gridviewprod;
    RecyclerView recyclerView;
    RecViewadap recViewadap;
    RelativeLayout relativeLayout;
    Button new_prod;

    static  Sqlitehelp helper;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewallprods);
        new_prod=findViewById(R.id.new_prod);
        new_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(getApplicationContext(),edit_prods.class);
                startActivity(intent2);
            }
        });
        Estaticas.settitle(this,"Productos");

        helper=new Sqlitehelp(this.getApplicationContext(),"base",null,1);
        //vistaprodss=findViewById(R.id.vistaprods);
        ImageButton back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        gridviewprod=findViewById(R.id.vistaprods);
        fillcates();
        recyclerView =findViewById(R.id.cates_reci);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recViewadap=new RecViewadap(categorias);
        recyclerView.setAdapter(recViewadap);
        fillprods();



    }




    public class RecViewadap extends RecyclerView.Adapter<viewallprods.RecViewadap.viewHolder>{

        ArrayList <Categorias> listacate;

        Boolean[] catesec;



        public RecViewadap(ArrayList<Categorias> listacate) {
            this.listacate = listacate;
            catesec=new Boolean[listacate.size()];
            //llenar(1);
        }








        @NonNull
        @Override
        public viewallprods.RecViewadap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.botoncategoria,parent, false);

            // Button but=view.findViewById(R.id.botoncate);

            return new viewallprods.RecViewadap.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final viewallprods.RecViewadap.viewHolder viewHolder, final int i) {

            viewHolder.botoncate.setText(listacate.get(i).getNombre());
            switch (listacate.get(i).getColor()){
                case "red":
                    viewHolder.botoncate.setBackgroundColor(getResources().getColor(R.color.rojotr));
                    break;
                case "yellow":
                    viewHolder.botoncate.setBackgroundColor(getResources().getColor(R.color.amarillotr));
                    break;
                case "orange":
                    viewHolder.botoncate.setBackgroundColor(getResources().getColor(R.color.naranjatr));
                    break;
                case "blue":
                    viewHolder.botoncate.setBackgroundColor(getResources().getColor(R.color.azultr));
                    break;
                case "purple":
                    viewHolder.botoncate.setBackgroundColor(getResources().getColor(R.color.moradotr));
                    break;
            }
            if (cateselec[i]) {
                viewHolder.border.setBackgroundColor(getResources().getColor(R.color.transparent));
            }
            else viewHolder.border.setBackgroundColor(getResources().getColor(R.color.colorgris));


            viewHolder.botoncate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lis=helper.getprod(Estaticas.db,categorias.get(i).getNombre());
                    viewallprods.Adapprod1 adapprod2=new viewallprods.Adapprod1(lis,getApplicationContext());
                    gridviewprod.setAdapter(adapprod2);
                    llenarboleano();
                    setselected(i);
                    recViewadap.notifyDataSetChanged();

                }

            });



        }

        @Override
        public int getItemCount() {
            return listacate.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
            Button botoncate;
            LinearLayout border;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                botoncate=itemView.findViewById(R.id.botoncate);
                border=itemView.findViewById(R.id.botoncateborder);


            }


        }

    }
    public void fillcates(){
        categorias=helper.getcate(Estaticas.db);
        cateselec=new boolean[categorias.size()];
        llenarboleano();
        if (categorias.size()>0)
            setselected(0);
    }
    public  void fillprods(){

        lis=helper.getprod(Estaticas.db);


    Adapprod1 adapprod1=new Adapprod1(lis,getApplicationContext());
    gridviewprod.setAdapter(adapprod1);
    }

    public static String checkCateSelected(){
        for (int i = 0; i <cateselec.length ; i++) {
            if (cateselec[i]){
                return categorias.get(i).getNombre();
            }
        }
        return "";
    }
    public static class Adapprod1 extends BaseAdapter{

        ArrayList<Product> lista;
        Context ctx;

        public Adapprod1( ArrayList < Product> prods, Context ctx){
            lista=prods;
            this.ctx=ctx;

        }
        @Override
        public int getCount() {
            return lista.size();
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
            View view=inflater.inflate(R.layout.editarprod,null);
            TextView nombre=view.findViewById(R.id.nombr_prod);
            nombre.setText(lista.get(position).getNombre());
            TextView prec=view.findViewById(R.id.precio_prod);
            prec.setText(Float.toString(lista.get(position).getPrecio()));
            ImageView borrar=view.findViewById(R.id.erase_prod);
            borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    helper.deleteprod(Estaticas.db,lista.get(position).getId());
                    setVistaprods(parent.getContext());
                }
            });
            ImageView edit=view.findViewById(R.id.edit_prod);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Estaticas.editandoprod=true;
                    Estaticas.product_Current=lista.get(position);
                    Estaticas.catesselect=helper.getcatedeprod(Estaticas.db,lista.get(position).getNombre());
                    Intent intent2=new Intent(parent.getContext(),edit_prods.class);
                    parent.getContext().startActivity(intent2);
                }
            });



            return view;
        }
    }
    public void llenarboleano(){
        for (int i = 0; i <cateselec.length ; i++) {
            cateselec[i]=false;
        }
    }
    public void setselected(int x){
        cateselec[x]=true;
    }

    public static void setVistaprods( Context ctx){
        lis=helper.getprod(Estaticas.db);
        viewallprods.Adapprod1 adapprod2=new viewallprods.Adapprod1(lis,ctx);
        gridviewprod.setAdapter(adapprod2);
    }




}
