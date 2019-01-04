package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.zip.Inflater;

import DAL.Entidades.Mesas;
import DAL.Entidades.Ubicacion;
import DAL.Sqlitehelp;

public class mesas extends AppCompatActivity {
    RecyclerView recyclerView;
    EditText nom_ubi;
    Button addubi;
    ImageView ubiimage;
    Sqlitehelp helper;
    RecViewadap recViewadap;
    TextView ubiid;
    EditText mesanom;
    Button addmesa;
    Spinner slec;
    ArrayList<String> listaubi;
    String[] ubis;
    TextView idmesa;
    Button showmes;
    Spinner spinerubi;
    Boolean[] ubiselc=new Boolean[3];
    adapubispin adapubispin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mesas);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView=findViewById(R.id.reciclerubica);
        showmes=findViewById(R.id.showmes);
        spinerubi=findViewById(R.id.spinnerimgubi);
        adapubispin=new adapubispin(getApplicationContext());

        spinerubi.setAdapter(adapubispin);
        fillselectubis();
        setselectubi(0);


        showmes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                            }
        });
        recyclerView.setLayoutManager(layoutManager);
       //filter=findViewById(R.id.filtrarubi);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
        setubis();
        nom_ubi=findViewById(R.id.editubinom);
        mesanom=findViewById(R.id.mesanomedit);
        slec=findViewById(R.id.selectubi);
        addmesa=findViewById(R.id.addmesa);
        ubiid=findViewById(R.id.idubtxt);
        addubi=findViewById(R.id.addubi);
        idmesa=findViewById(R.id.idmesatext);
        ubiimage=findViewById(R.id.imagubiedit);
        ubiimage.setImageResource(Estaticas.imgubi[spinerubi.getSelectedItemPosition()]);
        ImageButton back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Estaticas.editandomesa=false;
                Estaticas.editandoubis=false;
            }

        });

        fillubidropdown();





        addmesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarmesa();
            }
        });
//        filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                fillmesas();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });



        addubi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarubi();
            }
        });


        //fillmesas();



    }

    public void fillubidropdown(){
        listaubi=new ArrayList<>();

        listaubi=helper.returnubicationn(Estaticas.db);
       ubis=new String[listaubi.size()];
        for (int i = 0; i <listaubi.size() ; i++) {
            ubis[i]=listaubi.get(i);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,ubis);
        slec.setAdapter(adapter);
        slec.setSelection(0);
    }


    public void insertarmesa(){
        Long ins;
        if (!mesanom.getText().toString().equals("")&&slec.hasFocusable()){


            if (Estaticas.editandomesa){
                Mesas mesa=new Mesas(mesanom.getText().toString());
                mesa.setIdmesa(Long.parseLong(idmesa.getText().toString()));
                mesa.setUbicacion(ubis[slec.getSelectedItemPosition()]);
                mesa.setStatus(1);

                try {
                    helper.updatemesa(Estaticas.db,mesa);
                }catch (Exception e){
                    Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_LONG);
                    toast.show();
                }
                Toast toast = Toast.makeText(getApplicationContext(),"Mesa "+mesa.getIdmesa()+" actualizada." ,Toast.LENGTH_LONG);
                toast.show();

                clearmes();
                fillubidropdown();


            }
            else {
                Mesas mesa=new Mesas(mesanom.getText().toString());
                mesa.setUbicacion(ubis[slec.getSelectedItemPosition()]);
                mesa.setStatus(1);
                ins=helper.insertmesa(Estaticas.db,mesa,getApplicationContext());

                if (ins!=-1){
                    Toast toast = Toast.makeText(getApplicationContext(), "Mesa: "+mesanom.getText()+" agregada.",Toast.LENGTH_SHORT);
                    toast.show();
                    clearmes();
                    fillubidropdown();
                }
                else{

                    Toast toaast = Toast.makeText(getApplicationContext(), "Error, contacte a soporte",Toast.LENGTH_SHORT);
                    toaast.show();
                }

            }



        }
        else{

            Toast toastada = Toast.makeText(getApplicationContext(), "Debe haber una ubicacion y tener un nombre.",Toast.LENGTH_SHORT);
            toastada.show();
        }
        Estaticas.editandomesa=false;

    }
    public void clearubi(){
        nom_ubi.setText("");
    }
    public void clearmes(){
        mesanom.setText("");
        slec.setSelection(0);
    }

    public void agregarubi(){
        Long id=0L;

        if (!nom_ubi.getText().toString().equals("")){
            if (Estaticas.editandoubis){
                Ubicacion ubic=new Ubicacion(nom_ubi.getText().toString(),Long.parseLong(ubiid.getText().toString()));
                ubic.setPhoto(Integer.toString(Estaticas.imgubi[spinerubi.getSelectedItemPosition()]));

                helper.updateubi(Estaticas.db,ubic);
                Estaticas.editandoubis=false;
                ubiid.setText("");
                clearubi();
                setubis();
                fillubidropdown();
                setselectubi(0);
                ubiimage.setImageResource(Estaticas.imgubi[0]);

            }
            else {
                id=helper.insertubicacion(Estaticas.db,nom_ubi.getText().toString(),Integer.toString(Estaticas.imgubi[spinerubi.getSelectedItemPosition()]));
                clearubi();
                setubis();
                fillubidropdown();
                setselectubi(0);
                ubiimage.setImageResource(Estaticas.imgubi[0]);

            }

        }
        if (id==-1){
            Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT);

        }
        else Toast.makeText(getApplicationContext(),"Agregado",Toast.LENGTH_SHORT);


    }

    public void setubis(){
        RecViewadap recViewadap;
        recyclerView.invalidate();
        ArrayList<Ubicacion> ubicacions;
        ubicacions=helper.returnubicationfull(Estaticas.db);
        recViewadap=new RecViewadap(ubicacions);
        recyclerView.setAdapter(recViewadap);

    }


    public class RecViewadap extends RecyclerView.Adapter<mesas.RecViewadap.viewHolder>{

    ArrayList<Ubicacion> ubicacions=new ArrayList<>();




        public RecViewadap(ArrayList<Ubicacion> ubicacions) {
            this.ubicacions = ubicacions;
            //llenar(1);
        }








        @NonNull
        @Override
        public mesas.RecViewadap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.listaubi,parent, false);

            // Button but=view.findViewById(R.id.botoncate);

            return new mesas.RecViewadap.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final mesas.RecViewadap.viewHolder viewHolder, final int i) {

            viewHolder.nomubi.setText(ubicacions.get(i).getNombre());
            viewHolder.idubi.setText(ubicacions.get(i).getId().toString());
            viewHolder.imagenubi.setImageResource(Integer.parseInt(ubicacions.get(i).getPhoto()));
            viewHolder.borrar.setImageResource(R.drawable.trash);

            viewHolder.edit.setImageResource(R.drawable.writing);

            viewHolder.borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Sqlitehelp helper=new Sqlitehelp(getApplicationContext(),"base",null,1);

                    helper.deleteubi(Estaticas.db,ubicacions.get(i).getId());


                    setubis();
                    fillubidropdown();
                }
            });
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Estaticas.editandoubis=true;
                    Ubicacion ubi=new Ubicacion(ubicacions.get(i).getNombre(),ubicacions.get(i).getId());
                    nom_ubi.setText(ubi.getNombre());
                    ubiid.setText(ubi.getId().toString());
                    setubis();
                    fillubidropdown();
                }
            });



        }

        @Override
        public int getItemCount() {
            return ubicacions.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
            ImageButton edit;
            ImageButton borrar;
            ImageView imagenubi;
            TextView idubi;
            TextView nomubi;



            public viewHolder(@NonNull View itemView) {
                super(itemView);
               edit=itemView.findViewById(R.id.editarubibot);
               borrar=itemView.findViewById(R.id.eliminarubibot);
               imagenubi=itemView.findViewById(R.id.imagenubiedit);
               idubi=itemView.findViewById(R.id.idubi);
               nomubi=itemView.findViewById(R.id.nombreubic);


            }


        }

    }


    public class adapubispin  extends BaseAdapter{
        LayoutInflater inflater;
        Context ctx;
        public adapubispin(Context ctx){
            this.ctx=ctx;
            inflater=(LayoutInflater.from(ctx));
        }

        @Override
        public int getCount() {
            return Estaticas.imgubi.length;
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
            View view=inflater.inflate(R.layout.spinerubimg,null);
            CheckBox chb=view.findViewById(R.id.checkBoxubi);
            TextView txt=view.findViewById(R.id.texto_nom_ubi);
            ImageView imgviw=view.findViewById(R.id.imagenubi1);
            imgviw.setImageResource(R.drawable.esfera);
            switch (position){
                case 0:
                    txt.setText("Interior");
                    imgviw.setImageResource(Estaticas.imgubi[0]);
                    break;
                case 1:
                    txt.setText("Barra");
                    imgviw.setImageResource(Estaticas.imgubi[1]);
                    break;
                case 2:
                    txt.setText("Exterior");
                    imgviw.setImageResource(Estaticas.imgubi[2]);
                    break;
            }
            if (ubiselc[position]){
                chb.setChecked(true);
            }
            else chb.setChecked(false);
            chb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        setselectubi(position);

                        ubiimage.setImageResource(Estaticas.imgubi[position]);
                        notifyDataSetChanged();
                    }

                }
            });


            return view;
        }
    }

    public void fillselectubis(){
        for (int i = 0; i <ubiselc.length ; i++) {
            ubiselc[i]=false;

        }
    }
    public void setselectubi(int x){
        fillselectubis();
        spinerubi.setSelection(x);
        ubiselc[x]=true;
    }


}
