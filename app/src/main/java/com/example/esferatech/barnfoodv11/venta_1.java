package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DAL.Entidades.Categorias;
import DAL.Entidades.Product;
import DAL.Entidades.Venta_mesa;
import DAL.Entidades.Ventas;
import DAL.Sqlitehelp;

public class venta_1 extends AppCompatActivity {
    GridView gridViewcate;
    static GridView gridviewprod;
    TextView cateid;
    ArrayList<Product> cuenta=new ArrayList<>();
    ArrayList<Categorias> categorias;
    ImageView ubic;
    TextView titul;

    boolean[] cateselec;
    ArrayList<Product> lis;
    ArrayList<Product> listatotal=new ArrayList<>();
    RecyclerView recyclerView;
    TextView subtotal;
    Button procesar;
    RecViewadap recViewadap;
    RelativeLayout relativeLayout;
    ImageView verticket;
    ArrayList<Venta_mesa> listaventasactual;
    final Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta_1);
        boolean ventaocup=false;

        listaventasactual=new ArrayList<>();
        ubic=findViewById(R.id.ubicaimg);
        titul=findViewById(R.id.titulo_ubi2);
        ubic.setImageResource(Integer.parseInt(Estaticas.curubi.getPhoto()));
        titul.setText(Estaticas.curubi.getNombre()+": "+Estaticas.nombremesaact.getNombre());

        subtotal=findViewById(R.id.subtotal);

        relativeLayout=findViewById(R.id.barrafondo);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //listatotal.addAll(cuenta);
                Estaticas.prodsu=listatotal;
                Estaticas.prod_sin_proc=cuenta;
                Intent intent=new Intent(getApplicationContext(),cuenta.class);
                startActivity(intent);
                finish();
            }
        });
        //verticket=findViewById(R.id.verticket);

        gridviewprod=findViewById(R.id.gridviewprod);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        categorias=helper.getcate(Estaticas.db);
        if (!categorias.isEmpty()){
            lis=helper.getprod(Estaticas.db,categorias.get(0).getNombre());
            cateselec=new boolean[categorias.size()];

            llenarboleano();
            setselected(0);
        }
        else{
            lis=helper.getprod(Estaticas.db);
        }



        Adapprod1 adapprod=new Adapprod1(lis);
        gridviewprod.setAdapter(adapprod);

        recyclerView=findViewById(R.id.reciclercate);
        recyclerView.setLayoutManager(layoutManager);
        recViewadap=new RecViewadap(categorias);
        recyclerView.setAdapter(recViewadap);

        Button botoncancela=findViewById(R.id.botoncancela);
        botoncancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                cuenta.clear();

            }
        });


        procesar=findViewById(R.id.procesarbotn);

        procesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Estaticas.nombremesaact.getStatus()==1&&cuenta.size()>0){
                    Long idvent=-1L;
                    Estaticas.nombremesaact.setStatus(2);
                    Date date=new Date();
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String fech=format.format(date);
                    helper.updatemesa(Estaticas.db,Estaticas.nombremesaact);

                    idvent=helper.inserventa(Estaticas.db,gettotal(cuenta),fech,Estaticas.nombremesaact.getNombre(),"admin");
                    helper.insert_venta_temp(Estaticas.db,idvent,Estaticas.nombremesaact.getNombre());


                    for (int i = 0; i <cuenta.size() ; i++) {
                        helper.insert_prod_de_vent(Estaticas.db,i,idvent,cuenta.get(i).getNombre());

                    }
                    fragmentoventas.updategridmesa();
//                    for (int i = 0; i <cuenta.size() ; i++) {
//                        Print.PrtTicket(cuenta.get(i).getNombre()+" "+cuenta.get(i).getPrecio());
//                    }
//                    Print.PrtTicket("\n");
//                    Print.PrtTicket("\n");
//                    Print.PrtTicket("\n");
                    cuenta.clear();
                    finish();
                    return;
                }
                if (Estaticas.nombremesaact.getStatus()==2&&cuenta.size()>0){
                    Long idvent=helper.get_ventas_temp(Estaticas.db,Estaticas.nombremesaact.getNombre());
                    for (int i = listatotal.size(); i <(cuenta.size()+listatotal.size()) ; i++) {
                        helper.insert_prod_de_vent(Estaticas.db,i,idvent,cuenta.get(i-listatotal.size()).getNombre());
                    }
                    helper.updateventa(Estaticas.db,setrealtotal1(),Estaticas.nombremesaact.getNombre(),"admin",idvent);
                    finish();
                    cuenta.clear();
                    return;
                }
                if (cuenta.size()==0){
                    Toast tostada=Toast.makeText(getApplicationContext(),"No hay productos nuevos por procesar",Toast.LENGTH_SHORT);
                    tostada.show();
                    return;
                }
                if (Estaticas.nombremesaact.getStatus()==3){
                    Toast tostada=Toast.makeText(getApplicationContext(),"Imposible procesar, la mesa esta lista para ser cobrada",Toast.LENGTH_SHORT);
                    tostada.show();
                    return;
                }

            }
        });


        TextView cuentaact=(TextView) findViewById(R.id.cuentanum);
        cuentaact.setText("Cuenta: "+Estaticas.nombremesaact.getNombre());
        setcuenta();



    }



    float gettotal(ArrayList<Product> products){
        float total=0;
        for (Product a:products) {
            total+=a.getPrecio();
        }
        return total;
    }

    public void setcates(){


    }

    public void llenarboleano(){
        for (int i = 0; i <cateselec.length ; i++) {
            cateselec[i]=false;
        }
    }
    public void setselected(int x){
        cateselec[x]=true;
    }




    public void setcuenta(){
        if (Estaticas.nombremesaact.getStatus()==2||Estaticas.nombremesaact.getStatus()==3){
            Long ds=-1L;
            ds=helper.get_ventas_temp(Estaticas.db,Estaticas.nombremesaact.getNombre());
            listatotal=helper.getprods_de_vent(Estaticas.db,helper.get_ventas_temp(Estaticas.db,Estaticas.nombremesaact.getNombre()));
            setrealtotal();
        }
        if (Estaticas.nombremesaact.getStatus()==1){

        }


    }

    public class Adapprod1 extends BaseAdapter{

        ArrayList<Product> lista;


        public Adapprod1( ArrayList < Product> prods){
        lista=prods;

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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=inflater.inflate(R.layout.botonprod,null);
            ImageView imagenprod=(ImageView)view.findViewById(R.id.imagenprod);

            //imagenprod.setImageResource(images[position]);
            final TextView desc=(TextView)view.findViewById(R.id.nombr_prod);
            TextView precio=(TextView)view.findViewById(R.id.precio);
            desc.setText(lista.get(position).getNombre());
            precio.setText("$ "+lista.get(position).getPrecio().toString());
            ImageView botagre=view.findViewById(R.id.botonagrega);
            botagre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                cuenta.add(lista.get(position));
                    Toast toa=Toast.makeText(getApplicationContext(),Integer.toString(cuenta.size()),Toast.LENGTH_SHORT);
                    toa.show();
                    setrealtotal();


                }
            });

            return view;
        }
    }
    public void setrealtotal(){
        if (Estaticas.nombremesaact.getStatus()!=1){
            float tot=gettotal(listatotal)+gettotal(cuenta);
            subtotal.setText("$"+tot);
        }
        else {
            subtotal.setText("$"+gettotal(cuenta));
        }
    }
    public float setrealtotal1(){
        if (Estaticas.nombremesaact.getStatus()!=1){
            float tot=gettotal(listatotal)+gettotal(cuenta);
            return tot;
        }
        else {
            return gettotal(cuenta);
        }
    }




    public class RecViewadap extends RecyclerView.Adapter<RecViewadap.viewHolder>{

        ArrayList <Categorias> listacate;

        Boolean[] catesec;



        public RecViewadap(ArrayList<Categorias> listacate) {
            this.listacate = listacate;
            catesec=new Boolean[listacate.size()];
            //llenar(1);
        }








        @NonNull
        @Override
        public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.botoncategoria,parent, false);

           // Button but=view.findViewById(R.id.botoncate);

            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final viewHolder viewHolder, final int i) {

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
                    Adapprod1 adapprod2=new Adapprod1(lis);
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




}
