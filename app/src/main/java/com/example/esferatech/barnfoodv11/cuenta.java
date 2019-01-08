package com.example.esferatech.barnfoodv11;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DAL.Entidades.Product;
import DAL.Sqlitehelp;

import com.vanstone.trans.api.SystemApi;

public class cuenta extends Activity {
    RecyclerView visorprods;
    ImageButton back;
    Button cobrar;
    TextView mesa_nombre;
    Button print_ticket;
    Sqlitehelp helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        print_ticket=findViewById(R.id.print_ticket);
        visorprods=findViewById(R.id.vistacuentas);
        cobrar=findViewById(R.id.button_cobrar);
        mesa_nombre=findViewById(R.id.mesa_nombre);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        cobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Estaticas.nombremesaact.getStatus()!=1){
                    finish();
                    Intent intent=new Intent(getApplicationContext(),Cobrar.class);
                    startActivity(intent);
                }
                else {
                    Toast toast=Toast.makeText(getApplicationContext(),"No hay ningún producto procesado en esta cuenta.",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        print_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Print.prntCuenta(Estaticas.prodsu);
                Estaticas.nombremesaact.setStatus(3);
                helper.updatemesa(Estaticas.db,Estaticas.nombremesaact);
                fragmentoventas.updategridmesa();
                finish();
               // SystemApi.Beef_Api(6,500);
               // SystemApi.PlaySound_Api(1,6);
            }
        });
        back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        visorprods.setLayoutManager(layoutManager);
        visorprods.setAdapter(new RecViewadap(Estaticas.prodsu));
        TextView prodsinproc=findViewById(R.id.cant_prod_sin_proc);
        TextView ctatotal=findViewById(R.id.totalreal);
        TextView prodtot=findViewById(R.id.total_prods);
        prodsinproc.setText(Integer.toString(Estaticas.prod_sin_proc.size()));
        prodtot.setText(Integer.toString(Estaticas.prodsu.size()));
        mesa_nombre.setText(Estaticas.nombremesaact.getNombre());
        Estaticas.settitle(this,"Cuenta");




//        int x=Estaticas.ctaact.listadeprod.size();
//        String y=Integer.toString(x);
//            prodtot.setText(y);



        ctatotal.setText(Float.toString(gettotal(Estaticas.prodsu)));



    }

    float gettotal(ArrayList<Product> products){
        float total=0;
        for (Product a:products) {
            total+=a.getPrecio();
        }
        return total;
    }

    public class RecViewadap extends RecyclerView.Adapter<cuenta.RecViewadap.viewHolder>{

        ArrayList <Product> cuentaxd;

        public RecViewadap(ArrayList<Product> cuental) {
            this.cuentaxd = cuental;
        }






        @NonNull
        @Override
        public cuenta.RecViewadap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.prodlist,parent, false);



            return new cuenta.RecViewadap.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final cuenta.RecViewadap.viewHolder viewHolder, final int i) {
            viewHolder.imageprodvi.setImageResource(R.drawable.esfera);
            viewHolder.descu.setImageResource(R.drawable.tag);
            viewHolder.elim.setImageResource(R.drawable.cancel);
            viewHolder.desc.setText(cuentaxd.get(i).getNombre());
            viewHolder.prec.setText(Float.toString(cuentaxd.get(i).getPrecio()));

        }

        @Override
        public int getItemCount() {
            return cuentaxd.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
           ImageView imageprodvi,descu,elim;
           TextView desc;
           TextView prec;

            public viewHolder(@NonNull View itemView) {
                super(itemView);
                imageprodvi=itemView.findViewById(R.id.imagenprodmin);
                desc=itemView.findViewById(R.id.nombr_prod);
                prec=itemView.findViewById(R.id.precio);
                descu=itemView.findViewById(R.id.botondescuenta);
                elim=itemView.findViewById(R.id.botoncancelarprod);

            }


        }

    }




}
