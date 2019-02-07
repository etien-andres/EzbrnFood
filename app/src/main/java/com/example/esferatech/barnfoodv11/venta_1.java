package com.example.esferatech.barnfoodv11;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import DAL.Entidades.Categorias;
import DAL.Entidades.Mod_and_tipo;
import DAL.Entidades.Modif_comandado;
import DAL.Entidades.Modificadores;
import DAL.Entidades.Prod_comandado;
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
    ArrayList<Prod_comandado> prodscomands=new ArrayList<>();
    RecyclerView recyclerView;
    TextView subtotal;
    Button procesar;
    RecViewadap recViewadap;
    RelativeLayout relativeLayout;
    ImageView verticket;
    ArrayList<Venta_mesa> listaventasactual;
    final Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);
    int ide_deprod;
    Activity x;


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
        x=this;
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



        Adapprod1 adapprod=new Adapprod1(lis,x);
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
                    int idmod=1;
                    for (Prod_comandado prod:prodscomands) {
                        for (Modif_comandado mod:prod.getMod_oblig()) {
                            helper.insert_mod_de_prod_enventa(Estaticas.db,mod.getParent_prod(),idmod,idvent,prod.getProduct().getNombre(),mod.getMod().getNombre());
                            idmod++;
                        }
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
            ide_deprod=listatotal.size()+1;

        }
        if (Estaticas.nombremesaact.getStatus()==1){
            ide_deprod=1;
        }


    }

    public class Adapprod1 extends BaseAdapter{

        ArrayList<Product> lista;
        Activity a;

        public Adapprod1( ArrayList < Product> prods ,Activity a){
        lista=prods;
        this.a=a;
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
                    Estaticas.producto_comandado=lista.get(position);
                    Estaticas.prod_comandado=new Prod_comandado(ide_deprod);
                    Estaticas.id_deprod_comand=ide_deprod;

                    cuenta.add(Estaticas.producto_comandado);
                    prodscomands.add(Estaticas.prod_comandado);
                    setrealtotal();
                    ide_deprod++;

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
    public void agregar_prod(){


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
                    Adapprod1 adapprod2=new Adapprod1(lis,getParent());
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


    public class CustomDialogClass extends Dialog {
        public Activity c;
        public Dialog d;
        RecyclerView cates_de_drial;
        FrameLayout cuadro_de_Fragmentos;
        Fragment fr;
        cates_de_venta adaptar=new cates_de_venta();
        adapt_mods adapt_mods;
        boolean slected[];
        ArrayList<Mod_and_tipo> modificadores;
        GridView modif_obig_grid;


        public CustomDialogClass(Activity a) {
            super(a);
            this.c = a;
//            modificadores=helper.get_mod_de_prod(Estaticas.db,Estaticas.product_Current.getNombre());
//           slected=new boolean[modificadores.size()];
//            fillbool();


        }

        public void slect(int j){
            fillbool();
            slected[j]=true;
        }
        public void fillbool(){
            for (int i = 0; i <slected.length ; i++) {
                slected[i]=false;
            }
        }
        public ArrayList<Mod_and_tipo> filtrarOblig(ArrayList<Mod_and_tipo> mod_and_tipos){
            ArrayList <Mod_and_tipo> modAndTipos=new ArrayList<>();
            for (Mod_and_tipo a:mod_and_tipos) {
                if (a.getTipo()==1){
                    modAndTipos.add(a);
                }
            }
            return modAndTipos;
        }
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.custom_dialog_ventaprod);
//            modif_obig_grid=findViewById(R.id.grid_dialogo_venta);
//            adapt_mods=new adapt_mods(modificadores);
//            LinearLayoutManager layoutManager =new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
//            cates_de_drial=findViewById(R.id.top_menu_venta);
//            cates_de_drial.setLayoutManager(layoutManager);
//            cates_de_drial.setAdapter(adaptar);
//            Button cancel=findViewById(R.id.cancelar_vnt);
//            cancel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    dismiss();
//                }
//            });
//            Button terminar=findViewById(R.id.terminar_vnt);
//            terminar.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    cuenta.add(Estaticas.producto_comandado);
//                    prodscomands.add(Estaticas.prod_comandado);
//                    setrealtotal();
//                    ide_deprod++;
//                    dismiss();
//                }
//            });


        }

        public class adapt_mods extends BaseAdapter{
            ArrayList<Mod_and_tipo> mod_and_tipos;


            public adapt_mods(ArrayList<Mod_and_tipo> mod_and_tipos) {
                this.mod_and_tipos = filtrarOblig(mod_and_tipos);
            }

            @Override
            public int getCount() {
                return mod_and_tipos.size();
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
                View view=inflater.inflate(R.layout.elemento_modi_oblig,null);
                TextView nom,precio;
                RadioButton radioButton;

                nom=view.findViewById(R.id.nombre_mod);
                precio=view.findViewById(R.id.precio_mod);
                radioButton=view.findViewById(R.id.slected_mod);

                if (slected[position]){
                    radioButton.setChecked(true);
                }
                else radioButton.setChecked(false);
                radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        slect(position);
                        Modif_comandado modif_comandado=new Modif_comandado(Estaticas.id_deprod_comand);
                        modif_comandado.setMod(mod_and_tipos.get(position).getMod());
                        Estaticas.prod_comandado.clearmodoblig();
                        Estaticas.prod_comandado.addmod_oblig(modif_comandado);
                        adapt_mods.notifyDataSetChanged();
                    }
                });
                nom.setText(mod_and_tipos.get(position).getMod().getNombre());
                precio.setText(mod_and_tipos.get(position).getMod().getPrecio().toString());

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        slect(position);
                        Modif_comandado modif_comandado=new Modif_comandado(Estaticas.id_deprod_comand);
                        modif_comandado.setMod(mod_and_tipos.get(position).getMod());
                        Estaticas.prod_comandado.clearmodoblig();
                        Estaticas.prod_comandado.addmod_oblig(modif_comandado);
                        adapt_mods.notifyDataSetChanged();

                    }
                });

                return view;
            }
        }







        public class cates_de_venta extends RecyclerView.Adapter<CustomDialogClass.cates_de_venta.viewHolder>{

            boolean slected[]=new boolean[3];
            public void fillbol(){
                for (int i = 0; i <3 ; i++) {
                    slected[i]=false;
                }

            }
            public void slect(int i){
                fillbol();
                slected[i]=true;
            }

            public cates_de_venta() {
                slect(0);
            }

            @NonNull
            @Override
            public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view=LayoutInflater.from(getApplicationContext()).inflate(R.layout.botoncategoria,parent, false);

                // Button but=view.findViewById(R.id.botoncate);

                return new viewHolder(view);            }

            @Override
            public void onBindViewHolder(@NonNull viewHolder viewHolder, final int i) {
                switch (i){
                    case 0:
                        viewHolder.botoncate.setText("Ingredientes");
                        break;
                    case 1:
                        viewHolder.botoncate.setText("Extras");
                        break;
                    case 2:
                        viewHolder.botoncate.setText("Comentarios");
                        break;
                }

                if (slected[i]) {
                    viewHolder.border.setBackgroundColor(getResources().getColor(R.color.transparent));
                }
                else viewHolder.border.setBackgroundColor(getResources().getColor(R.color.colorgris));
                viewHolder.botoncate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fillbol();
                        slected[i]=true;
                        adaptar.notifyDataSetChanged();

                    }
                });

            }

            @Override
            public int getItemCount() {
                return 3;
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

}
