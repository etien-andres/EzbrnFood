package com.example.esferatech.barnfoodv11;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import DAL.Entidades.Categorias;
import DAL.Entidades.Product;
import DAL.Sqlitehelp;

public class agregarprod extends Activity {
    RecViewadappro adapprod=null;
    TextView visordeid;
    TextView editnom;
    TextView editprec;
    TextView idcateg;
    Spinner s;
    Spinner catenprod;
    Button addcate;
    EditText catenom;
    RadioGroup radioGroup;
    RadioButton redrad;
    ImageButton red,orange,yellow,blue,purp;
    TextView title_bar;
    public RecyclerView recyclerViewcate2,recyclerViewprods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarprod);
        String[] tipos={"Alimento","Bebida","Otro"};
        title_bar=findViewById(R.id.titulo_toolbar);
        title_bar.setText("Productos");
        s=findViewById(R.id.spinnertipo);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,tipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        recyclerViewcate2=findViewById(R.id.reciclercate);
        LinearLayoutManager layoutManager =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        LinearLayoutManager layoutManager2 =new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewprods=findViewById(R.id.recvipro);
        recyclerViewprods.setLayoutManager(layoutManager2);

        recyclerViewcate2.setLayoutManager(layoutManager);
        ImageButton back=findViewById(R.id.botonback);
        redrad=findViewById(R.id.redradio);
        visordeid=findViewById(R.id.idprods);
        editnom=findViewById(R.id.nombreedit);
        editprec=findViewById(R.id.precioedit);
        purp=findViewById(R.id.purpimg);
        blue=findViewById(R.id.blueimg);
        yellow=findViewById(R.id.yellowimg);
        orange=findViewById(R.id.orangeigm);
        red=findViewById(R.id.redimg);
        purp.setImageResource(R.color.morado);
        blue.setImageResource(R.color.azul);
        yellow.setImageResource(R.color.amarillo);
        orange.setImageResource(R.color.naranja);
        red.setImageResource(R.color.rojo);
        catenprod=findViewById(R.id.spinnercategs);

        addcate=findViewById(R.id.addcateg);
        catenom=findViewById(R.id.nombrecateedit);
        radioGroup=findViewById(R.id.radiogroupcolor);
        redrad.setChecked(true);
        idcateg=findViewById(R.id.idcate);


        addcate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addcate();

            }
        });

        setprods();



        setcates();
        setspinercate();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Estaticas.editandoprod=false;
                Estaticas.editandocate=false;
            }

        });
        Button btnagrega=findViewById(R.id.addproduct);



        btnagrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            addprods(); }});


    }
    public void addprods(){
        Product product=null;
        if (!Estaticas.editandoprod){
            Spinner x=findViewById(R.id.spinnertipo);

            Sqlitehelp help=new Sqlitehelp(getApplicationContext(),"base",null,1);
            Long id=-2L;
            boolean valid=true;
            if (editnom.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "El nombre no puede estar vacío", Toast.LENGTH_SHORT);
                toast.show();
                valid=false;
            }
            if (editprec.getText().toString().equals("")){
                Toast toast = Toast.makeText(getApplicationContext(), "El precio no puede estar vacío", Toast.LENGTH_SHORT);
                toast.show();
                valid=false;

            }

            if (valid){
                 product=new Product(editnom.getText().toString(),Float.parseFloat(editprec.getText().toString()),x.getSelectedItem().toString());
                id=help.insertprod(Estaticas.db,product);


            }





            if (id==-1){
                Toast toast = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                if (id>0){
                    help.setcatedeprod(Estaticas.db,product,Estaticas.catesselect);
                    Toast toast = Toast.makeText(getApplicationContext(), "Agregado", Toast.LENGTH_SHORT);
                    toast.show();
                    editnom.setText("");
                    editprec.setText("");
                    x.setSelection(0);
                    Estaticas.catesselect.clear();
                    setspinercate();
                    setprods();
                }

            }




        }
        else {
            Sqlitehelp help=new Sqlitehelp(getApplicationContext(),"base",null,1);
            Product pr=new Product(editnom.getText().toString(),Float.parseFloat(editprec.getText().toString()),s.getSelectedItem().toString());
            pr.setId(Long.parseLong(visordeid.getText().toString()));
            help.updateprod(Estaticas.db,pr);
            try {
                help.setcatedeprod(Estaticas.db,pr,Estaticas.catesselect);

            }catch (Exception e){
                Toast tpas=Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                tpas.show();
            }
            Estaticas.catesselect.clear();
            visordeid.setText("");
            Toast toast = Toast.makeText(getApplicationContext(),"Producto "+ pr.getId()+" actualizado.", Toast.LENGTH_SHORT);
            toast.show();
            visordeid.setText("");
            visordeid.setText("");
            s.setSelection(0);
            setprods();
            setspinercate();

        }
        Estaticas.editandoprod=false;
    }

    public void addcate(){
        Sqlitehelp help=new Sqlitehelp(getApplicationContext(),"base",null,1);

        if (catenom.getText().toString().equals("")){
            Toast toast = Toast.makeText(getApplicationContext(), "Se necesita un nombre para la categoría.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            if (!Estaticas.editandocate){
                Categorias cate=new Categorias(catenom.getText().toString());
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.redradio:
                        cate.setColor("red");
                        break;

                    case R.id.yellowradio:
                        cate.setColor("yellow");
                        break;
                    case R.id.orangerad:
                        cate.setColor("orange");

                        break;
                    case R.id.blueradio:
                        cate.setColor("blue");
                        break;
                    case R.id.purpleradio:
                        cate.setColor("purple");
                        break;
                }
                Long id;
                id=help.insertcate(Estaticas.db,cate);


                if (id!=-1){

                    Toast toast = Toast.makeText(getApplicationContext(), "Categoría "+cate.getNombre()+" agregada.", Toast.LENGTH_SHORT);
                    toast.show();
                    setcates();
                    clearcate();

                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Error.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
            else {
                Estaticas.editandocate=false;
                Categorias cate=new Categorias(catenom.getText().toString());
                cate.setId(Long.parseLong(idcateg.getText().toString()));
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.redradio:
                        cate.setColor("red");
                        break;

                    case R.id.yellowradio:
                        cate.setColor("yellow");
                        break;
                    case R.id.orangerad:
                        cate.setColor("orange");

                        break;
                    case R.id.blueradio:
                        cate.setColor("blue");
                        break;
                    case R.id.purpleradio:
                        cate.setColor("purple");
                        break;
                }


                help.updatecate(Estaticas.db,cate);
                Toast toast = Toast.makeText(getApplicationContext(), "Categoría "+Long.toString(cate.getId())+" actualizada.", Toast.LENGTH_SHORT);
                setcates();
                clearcate();
                toast.show();
            }
        }
    }

    public class Adapprod extends BaseAdapter {
       ArrayList<Product> products;


        public Adapprod(ArrayList <Product> products){
            this.products=products;
        }
        @Override
        public int getCount() {
            return products.size();
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
            final View view=inflater.inflate(R.layout.listadoprods,null);
            TextView descr=view.findViewById(R.id.nombr_prod);
            TextView prec=view.findViewById(R.id.precio1);
            TextView tipotext=view.findViewById(R.id.tipotext1);
            TextView n=view.findViewById(R.id.idsum);
            n.setText(products.get(position).getId().toString());
            descr.setText(products.get(position).getNombre());
            prec.setText(products.get(position).getPrecio().toString());
            tipotext.setText(products.get(position).getTipo());
            ImageButton delete=view.findViewById(R.id.deletebuton);
            delete.setImageResource(R.drawable.trash);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        Sqlitehelp help=new Sqlitehelp(getApplicationContext(),"base",null,1);
                        help.deleteprod(Estaticas.db,products.get(position).getId());
                        setprods();


                }
            });

            ImageButton edit =view.findViewById(R.id.editbuton);
            edit.setImageResource(R.drawable.writing);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    visordeid.setText(products.get(position).getId().toString());
                    editnom.setText(products.get(position).getNombre());
                    editprec.setText(products.get(position).getPrecio().toString());
                    if (products.get(position).getTipo().equals("Alimento")){
                        s.setSelection(0);
                    }
                    if (products.get(position).getTipo().equals("Bebida")){
                        s.setSelection(1);
                    }
                    if (products.get(position).getTipo().equals("Otro")){
                        s.setSelection(2);
                    }
                    Sqlitehelp helper=new Sqlitehelp(getApplicationContext(),"base",null,1);

                    Estaticas.catesselect=helper.getcatedeprod(Estaticas.db,products.get(position).getNombre());
                    setspinercate();
                    Estaticas.editandoprod=true;

                }
            });

            return view;
        }
    }

    public void clearcate(){
        catenom.setText("");
        radioGroup.check(R.id.redradio);
        idcateg.setText("");

    }

    public void setprods(){
        Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);
        ArrayList<Product> lista;
        lista=helper.getprod(Estaticas.db);
        adapprod=new RecViewadappro(lista);
        recyclerViewprods.setAdapter(adapprod);

    }
    public void clearpro(){
        visordeid.setText("");
        editnom.setText("");
        editprec.setText("");
        s.setSelection(0);
        setspinercate();
        Estaticas.catesselect.clear();
        Estaticas.editandoprod=false;


    }

    public void setcates(){
        Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);
        ArrayList<Categorias> categorias;
        categorias=helper.getcate(Estaticas.db);
        RecViewadap viewad=new RecViewadap(categorias);
        recyclerViewcate2.setAdapter(viewad);
        setspinercate();



    }
    public void setspinercate(){
        Sqlitehelp helper=new Sqlitehelp(this,"base",null,1);

        ArrayList<Categorias> categorias;
        categorias=helper.getcate(Estaticas.db);
        AdaptadorSpinCate spinCate=new AdaptadorSpinCate(categorias,getApplicationContext());
        catenprod.setAdapter(spinCate);

    }
    public class AdaptadorSpinCate extends BaseAdapter{
        ArrayList<Categorias> listacate;
        Context context;
        LayoutInflater inflater;
        public AdaptadorSpinCate(ArrayList<Categorias> listacate,Context context) {
            this.listacate = listacate;
            this.context=context;
            inflater=(LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return listacate.size();
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

            View view=inflater.inflate(R.layout.spinerlistadecate,null);
            ConstraintLayout constraintLayout=view.findViewById(R.id.contenedorspincate);
            TextView nomcate=view.findViewById(R.id.texto_nom_ubi);
            nomcate.setText(listacate.get(position).getNombre());
            String color=listacate.get(position).getColor();
            if (color.equals("red")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ff0000"));
            }
            if (color.equals("yellow")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ffe500"));
            }
            if (color.equals("orange")){
                constraintLayout.setBackgroundColor(Color.parseColor("#50ff8300"));
            }
            if (color.equals("blue")){
                constraintLayout.setBackgroundColor(Color.parseColor("#5000a5ff"));
            }
            if (color.equals("purple")){
                constraintLayout.setBackgroundColor(Color.parseColor("#508300ff"));
            }
            CheckBox checkBox=view.findViewById(R.id.checkBoxubi);
            if (Estaticas.catesselect.contains(listacate.get(position).getNombre())){
                checkBox.setChecked(true);
            }


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        Estaticas.catesselect.add(listacate.get(position).getNombre());
                    }
                    if (!isChecked){
                        Estaticas.catesselect.remove(listacate.get(position).getNombre());
                    }
                   setspinercate();
                }
            });


            return view;
        }
    }



    public class RecViewadap extends RecyclerView.Adapter<agregarprod.RecViewadap.viewHolder>{

        ArrayList<Categorias> categorias=new ArrayList<>();

        public RecViewadap(ArrayList<Categorias> categorias) {
            this.categorias = categorias;
            //llenar(1);
        }








        @NonNull
        @Override
        public agregarprod.RecViewadap.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.botoncategoriaedita,parent, false);

            return new agregarprod.RecViewadap.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final agregarprod.RecViewadap.viewHolder viewHolder, final int i) {
            viewHolder.nombrcat.setText(categorias.get(i).getNombre());
            if (categorias.get(i).getColor().equals("red")){
                viewHolder.colorim.setImageResource(R.color.rojo);

            }
            if (categorias.get(i).getColor().equals("orange")){
                viewHolder.colorim.setImageResource(R.color.naranja);

            }
            if (categorias.get(i).getColor().equals("purple")){
                viewHolder.colorim.setImageResource(R.color.morado);

            }
            if (categorias.get(i).getColor().equals("blue")){
                viewHolder.colorim.setImageResource(R.color.azul);

            }
            if (categorias.get(i).getColor().equals("yellow")){
                viewHolder.colorim.setImageResource(R.color.amarillo);

            }
            viewHolder.idcat.setText(Long.toString(categorias.get(i).getId()));
            viewHolder.borrar.setImageResource(R.drawable.trash);
            viewHolder.edit.setImageResource(R.drawable.writing);
            viewHolder.idcat.setText(Long.toString(categorias.get(i).getId()));
//
//
            viewHolder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Estaticas.editandocate=true;
                    idcateg.setText(Long.toString(categorias.get(i).getId()));
                    catenom.setText(categorias.get(i).getNombre());
                    if (categorias.get(i).getColor().equals("red")){
                        radioGroup.check(R.id.redradio);

                    }
                    if (categorias.get(i).getColor().equals("orange")){
                        radioGroup.check(R.id.orangerad);

                    }
                    if (categorias.get(i).getColor().equals("purple")){
                        radioGroup.check(R.id.purpleradio);

                    }
                    if (categorias.get(i).getColor().equals("blue")){
                        radioGroup.check(R.id.blueradio);

                    }
                    if (categorias.get(i).getColor().equals("yellow")){
                        radioGroup.check(R.id.yellowradio);

                    }
                    setcates();
                }
           });
            viewHolder.borrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Sqlitehelp helper=new Sqlitehelp(getApplicationContext(),"base",null,1);
                    try {
                        helper.deletecate(Estaticas.db,categorias.get(i).getId());

                    }catch (Exception e){
                        Toast toast=Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG);
                        toast.show();
                    }
                    setcates();
                }
            });




        }

        @Override
        public int getItemCount() {
            return categorias.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
            TextView idcat;
            TextView nombrcat, vigencia;
            ImageButton edit,borrar,colorim;



            public viewHolder(@NonNull View itemView) {
                super(itemView);
                idcat=itemView.findViewById(R.id.idcat);
                nombrcat=itemView.findViewById(R.id.nombrecate);
                vigencia=itemView.findViewById(R.id.vigenciacate);
                edit=itemView.findViewById(R.id.editcate);
                borrar=itemView.findViewById(R.id.erasecate);
                colorim=itemView.findViewById(R.id.colorimg);



            }


        }

    }
    public class RecViewadappro extends RecyclerView.Adapter<agregarprod.RecViewadappro.viewHolder>{

        ArrayList<Product> prods=new ArrayList<>();

        public RecViewadappro(ArrayList<Product> products) {
            this.prods = products;
            //llenar(1);
        }








        @NonNull
        @Override
        public agregarprod.RecViewadappro.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.listadoprods,parent, false);




            return new agregarprod.RecViewadappro.viewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final agregarprod.RecViewadappro.viewHolder viewHolder, final int i) {
        viewHolder.idnpo.setText(Long.toString(prods.get(i).getId()));
            String newstr="";

            if (prods.get(i).getNombre().length()>25){
            char[] chararr=prods.get(i).getNombre().toCharArray();
                for (int j = 0; j <22 ; j++) {
                    newstr+=chararr[j];
                }
                newstr+="...";
                viewHolder.desc.setText(newstr);
            }
            else viewHolder.desc.setText(prods.get(i).getNombre());


            viewHolder.prec.setText(Float.toString(prods.get(i).getPrecio()));
        viewHolder.tipo.setText(prods.get(i).getTipo());
        viewHolder.edit.setImageResource(R.drawable.writing);
        viewHolder.borrar.setImageResource(R.drawable.trash);
        viewHolder.prod.setImageResource(R.drawable.esfera);

        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visordeid.setText(prods.get(i).getId().toString());
                editnom.setText(prods.get(i).getNombre());
                editprec.setText(prods.get(i).getPrecio().toString());
                if (prods.get(i).getTipo().equals("Alimento")){
                    s.setSelection(0);
                }
                if (prods.get(i).getTipo().equals("Bebida")){
                    s.setSelection(1);
                }
                if (prods.get(i).getTipo().equals("Otro")){
                    s.setSelection(2);
                }
                Sqlitehelp helper=new Sqlitehelp(getApplicationContext(),"base",null,1);

                Estaticas.catesselect=helper.getcatedeprod(Estaticas.db,prods.get(i).getNombre());
                setspinercate();
                Estaticas.editandoprod=true;

            }
        });

        viewHolder.borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sqlitehelp help=new Sqlitehelp(getApplicationContext(),"base",null,1);
                help.deleteprod(Estaticas.db,prods.get(i).getId());
                setprods();
                clearpro();

            }
        });
//


        }

        @Override
        public int getItemCount() {
            return prods.size();
        }





        public class viewHolder extends RecyclerView.ViewHolder{
            TextView idnpo;
            TextView desc, prec,tipo;
            ImageButton edit,borrar;
            ImageView prod;


            public viewHolder(@NonNull View itemView) {
                super(itemView);
                idnpo=itemView.findViewById(R.id.idsum);
                desc=itemView.findViewById(R.id.nombr_prod);
                prec=itemView.findViewById(R.id.precio1);
                tipo=itemView.findViewById(R.id.tipotext1);
                edit=itemView.findViewById(R.id.editbuton);
                borrar=itemView.findViewById(R.id.deletebuton);
                prod=itemView.findViewById(R.id.imagenprodmin);



            }


        }

    }

}
