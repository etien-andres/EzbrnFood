package DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.esferatech.barnfoodv11.Estaticas;

import java.util.ArrayList;
import java.util.List;

import DAL.Entidades.Categorias;
import DAL.Entidades.Existencia;
import DAL.Entidades.Mesas;
import DAL.Entidades.Modificadores;
import DAL.Entidades.Product;
import DAL.Entidades.Sumin_Prod;
import DAL.Entidades.Suministros;
import DAL.Entidades.Ubicacion;
import DAL.Entidades.Usuarios;
import DAL.Entidades.Venta_mesa;
import DAL.Entidades.Ventas;

public class Sqlitehelp extends SQLiteOpenHelper {

    public Sqlitehelp(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {

        ContentValues nuevoUsuario=new ContentValues();
        nuevoUsuario.put("nombre","admin");
        nuevoUsuario.put("pwd","1234");
        nuevoUsuario.put("nombrecomp","Administrador Del Sistema");
        nuevoUsuario.put("contacto","23057000");
        nuevoUsuario.put("rol","Administrador");
        nuevoUsuario.put("tipo",1);
        ContentValues fondo_decaja=new ContentValues();
        fondo_decaja.put("fondo",0.00);
        fondo_decaja.put("descripcion","fondo");
        ContentValues minimo_decaja=new ContentValues();
        minimo_decaja.put("fondo",2000);
        minimo_decaja.put("descripcion","minimo");

        ContentValues maximo_de_caja=new ContentValues();
        maximo_de_caja.put("fondo",10000);
        maximo_de_caja.put("descripcion" ,"maximo");

        db.execSQL("create table if not exists restriccion(nombre text primary key unique)");


        db.execSQL("create table if not exists productos (nombre text primary key unique, precio real, tipo text , photo text )");
        db.execSQL("create table if not exists suministros (nombre primary key unique,unidadC text, unidadV text, costo real, relacionCV real, photo text)");
        db.execSQL("create table if not exists usuarios( nombre text primary key unique, pwd text, nombrecomp text, contacto text, rol text, tipo integer)");
        db.execSQL("create table if not exists modificadores( nombre text primary key unique, precio real)");
        db.execSQL("create table if not exists combo( nombre text primary key unique, precio real, photo text)");
        db.execSQL("create table if not exists vigen( nombre text primary key unique, fechaalta text,fechainit text, fechafin text)");
        db.execSQL("create table if not exists categorias( nombre text primary key unique, color text, vigen text, foreign key (vigen) references vigen(nombre))");
        db.execSQL("create table if not exists dias(dia text ,vigen text, foreign key (vigen) references vigen(nombre))");
        db.execSQL("create table if not exists horas(horainit text,horafin text, idvigen text ,foreign key (idvigen) references vigen(nombre))");
        db.execSQL("create table if not exists ubicacion (nombre text primary key unique, photo text)");
        db.execSQL("create table if not exists mesas( nombre text primary key unique, status integer, ubica text , foreign key (ubica) references ubicacion(nombre))");
        db.execSQL("create table if not exists ventas(ventaid integer primary key,total real, fecha text, mesa text, user text, foreign key (mesa) references mesas(nombre), foreign key (user) references usuarios(nombre))");
        db.execSQL("create table if not exists existencias (cantuc real, cantuv real, sumi text, foreign key (sumi) references suministros(nombre))");

        db.execSQL("create table if not exists modhasmod(nomb1 text, nomb2 text, foreign key (nomb1) references modificadores(nombre), foreign key (nomb2) references modificadores(nombre))");

        db.execSQL("create table if not exists prodhassum(cantuv real, produ text, sumin text, foreign key (produ) references productos(nombre), foreign key (sumin) references suministros(nombre))");

        db.execSQL("create table if not exists prodhasmod(prod text, mod text, foreign key (prod) references productos(nombre), foreign key (mod) references modificadores(nombre))");

        db.execSQL("create table if not exists prodhascat(prod text, cate text , foreign key (prod) references productos(nombre), foreign key (cate) references categorias(nombre))");
        db.execSQL("create table if not exists restrichasprod(rest text, prod text, foreign key (rest) references restriccion(nombre), foreign key (prod) references productos(nombre))");

        db.execSQL("create table if not exists modhassum(mod text, sum text, cantv real, foreign key(mod) references modificadores(nombre), foreign key (sum) references suministros(nombre))");

        db.execSQL("create table if not exists combohasprod(cant integer, comb text, prod text, foreign key (comb) references combo(nombre), foreign key (prod) references productos(nombre))");
        db.execSQL("create table if not exists ventashascombo(combid integer, venta integer, combo text, foreign key (venta) references ventas(ventaid), foreign key (combo) references combo(nombre))");
        db.execSQL("create table if not exists ventashasprod(prodid integer , venta integer, prod text, foreign key (venta) references ventas(ventaid), foreign key (prod) references productos(nombre))");
        db.execSQL("create table if not exists prodhasrest(prod text, restric text, foreign key (prod) references productos(nombre),foreign key (restric) references restriccion(nombre))");


        db.execSQL("create table if not exists prodchasmod(modid integer , prodid integer, venta integer, prod text, mod text,foreign key (venta) references ventas(ventaid), foreign key (prod) references productos(nombre), foreign key (mod) references modificadores(nombre))");
        db.execSQL("create table if not exists modchasmod(modid integer, prodid integer, venta integer, mod1 text,mod2 text ,foreign key (venta) references ventas(ventaid), foreign key (mod1) references modificadores(nombre), foreign key (mod2) references modificadores(nombre))");
        db.execSQL("create table if not exists ventas_en_mesas_TEMP(venta integer, mesa text, foreign key (mesa) references mesas(nombre),foreign key (venta) references ventas(ventaid) )");
        db.execSQL("create table if not exists caja(descripcion text,fondo real)");
        try {
            db.insertOrThrow("usuarios",null,nuevoUsuario);
            db.insertOrThrow("caja",null,fondo_decaja);
            db.insertOrThrow("caja",null,minimo_decaja);
            db.insertOrThrow("caja",null,maximo_de_caja);
        }catch (Exception e){

        }

    }
    public float get_minimo_de_caja(SQLiteDatabase db){
        float a;
        Cursor cur=db.query("caja",new String[]{"fondo"},"rowid=1",null,null,null,null);
        a=cur.getFloat(0);
        //asdasd
        return a;
    }
    public float get_maximo_de_caja(SQLiteDatabase db){
        float a;
        Cursor cur=db.query("caja",new String[]{"fondo"},"rowid=1",null,null,null,null);
        a=cur.getFloat(0);
        //cambio
        return a;
    }
    public void update_minimo_decaja(SQLiteDatabase db,Float cant){
        ContentValues minim=new ContentValues();
        minim.put("fondo",cant);
        db.update("caja",minim,"rowid=2",null);
    }
    public void update_maximo_de_caja(SQLiteDatabase db, Float max){
        ContentValues maxis=new ContentValues();
        maxis.put("fondo",max);
        db.update("caja",maxis,"descripcion=?",new String[]{"maximo"});
    }



    public void update_fondo_de_caja(SQLiteDatabase db,Double fndo){
        ContentValues contentValues=new ContentValues();
        contentValues.put("fondo",fndo);
        db.update("caja",contentValues,"decripcion=?",new String[]{"fondo"});

    }
    public double get_fondo(SQLiteDatabase db){
        Cursor cur=db.query("caja",new String[]{"fondo"},"descripcion=?",new String[]{"fondo"},null,null,null);
        double a=cur.getDouble(0);
        return a;
    }
    public void insert_venta_temp(SQLiteDatabase db,Long venta, String mesa){
        ContentValues ventatemp=new ContentValues();
        ventatemp.put("venta",venta);
        ventatemp.put("mesa",mesa);
        db.insert("ventas_en_mesas_TEMP",null,ventatemp);
    }

    public void delete_venta_temp(SQLiteDatabase db, String mesa){
        db.delete("ventas_en_mesas_TEMP","mesa=?",new String[]{mesa});
    }
    public Long get_ventas_temp(SQLiteDatabase db, String mesa){
        String[] cols={"venta"};
        Cursor cur=db.query("ventas_en_mesas_TEMP",cols,"mesa=?",new String[]{mesa},null,null,null);
        if (cur.moveToFirst()){
            return cur.getLong(0);
        }
        return 0L;
    }




    public long insertuser(SQLiteDatabase db, Usuarios user){
        long a= -1l;
        ContentValues content =new ContentValues();
        content.put("nombre",user.getUsr());
        content.put("pwd",user.getPwd());
        content.put("nombrecomp",user.getNombre());
        content.put("contacto",user.getContacto());
        content.put("rol",user.getNivel());
        content.put("tipo",user.getTipo());

        a=db.insert("usuarios",null,content);
        return a;
    }
    public void updateuser(SQLiteDatabase db, Usuarios user){
        ContentValues content=new ContentValues();
        content.put("nombre",user.getUsr());
        content.put("pwd",user.getPwd());
        content.put("nombrecomp",user.getNombre());
        content.put("contacto",user.getContacto());
        content.put("rol",user.getNivel());
        content.put("tipo",user.getTipo());
        db.update("usuarios",content,"rowid=?",new String[]{Long.toString(user.getId())});
    }
    public void deleteuser(SQLiteDatabase db,String usuarios){
        db.delete("usuarios","nombre=?",new String[]{usuarios});
    }
    public ArrayList<Usuarios> getusers(SQLiteDatabase db){
        ArrayList<Usuarios> listausers=new ArrayList<>();
        String[] select={"rowid","nombre","pwd","nombrecomp","contacto","rol","tipo"};
        Cursor cur=db.query("usuarios",select,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Usuarios user=new Usuarios(cur.getString(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getInt(6));
                user.setId(cur.getLong(0));
                listausers.add(user);
            }while (cur.moveToNext());
        }
        return listausers;
    }

    public void declararexist(SQLiteDatabase db,float cantuc, float cantuv,String sumin){
        ContentValues content=new ContentValues();
        content.put("cantuc",cantuc);
        content.put("cantuv",cantuv);
        content.put("sumi",sumin);
        db.insert("existencias",null,content);
    }
    public void updateexist(SQLiteDatabase db,float cantuc, float cantuv,String sumin){
        ContentValues content=new ContentValues();
        content.put("cantuc",cantuc);
        content.put("cantuv",cantuv);
        db.update("existencias",content,"sumi=?",new String[]{sumin});
    }
    public Existencia get_exist(SQLiteDatabase db, Suministros sumin){
        Existencia ex=new Existencia();
        Cursor cursor=db.query("existencias",new String[]{"cantuc","cantuv"},"sumi=?",new String[]{sumin.getNombre()},null,null,null);
        ex.setNombreSum(sumin.getNombre());
        ex.setCantuc(cursor.getFloat(0));
        ex.setCantuv(cursor.getFloat(1));
        return ex;
    }



    public Long inserventa(SQLiteDatabase db,float total,String fecha,String mesa ,String user){
        Long a=-1L;
        ContentValues contentValues=new ContentValues();
        contentValues.put("total",total);
        contentValues.put("fecha",fecha);
        contentValues.put("mesa",mesa);
        contentValues.put("user",user);
        a=db.insert("ventas","ventaid",contentValues);
        ContentValues valid=new ContentValues();
        valid.put("ventaid",a);
        db.update("ventas",valid,"rowid=?",new String[]{Long.toString(a)});
        return a;
    }
    public ArrayList<Ventas> getVentas(SQLiteDatabase db){
        ArrayList<Ventas> vents=new ArrayList<>();
        String[] cols={"ventaid", "total","fecha","mesa","user"};
        Cursor cur=db.query("ventas",cols,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Ventas vent=new Ventas(cur.getString(4),cur.getString(3),cur.getFloat(1),cur.getString(2));
                vent.setId(cur.getInt(0));
                vents.add(vent);
            }while (cur.moveToNext());
        }
        return vents;
    }
    public Ventas getventas(SQLiteDatabase db,Long id){
        String[] cols={"ventaid", "total","fecha","mesa","user"};
        Ventas ventas=null;
        Cursor cur=db.query("ventas",cols,"ventaid=?",new String[]{Long.toString(id)},null,null,null);
        if (cur.moveToFirst()){
             ventas=new Ventas(cur.getString(4),cur.getString(3),cur.getFloat(1),cur.getString(2));
            ventas.setId(cur.getInt(0));

        }
        return ventas;
    }
    public void updateventa(SQLiteDatabase db,float total, String mesa, String user,Long id){
        ContentValues contentValues=new ContentValues();
        contentValues.put("total",total);
        contentValues.put("mesa",mesa);
        contentValues.put("user",user);
        db.update("ventas",contentValues,"ventaid=?",new String[]{Long.toString(id)});
    }


    public void insert_prod_de_vent(SQLiteDatabase db, int id_de_prod_en_v, Long ventid,String prod){
        ContentValues values=new ContentValues();
        values.put("prodid", id_de_prod_en_v);
        values.put("venta",ventid);
        values.put("prod",prod);
        db.insert("ventashasprod",null,values);
    }
    public ArrayList<Product> getprods_de_vent(SQLiteDatabase db, Long ventid){
        ArrayList<Product> prods=new ArrayList<>();
        ArrayList<String> prodss=new ArrayList<>();
        Cursor cur=db.query("ventashasprod",new String[]{"prod"},"venta=?",new String[]{Long.toString(ventid)},null,null,null);
        if (cur.moveToFirst()){
            do {
                prodss.add(cur.getString(0));
            }while (cur.moveToNext());

        }

        for (String a:prodss) {
           Cursor cur2=db.query("productos", new String[]{"nombre","precio","tipo"},"nombre=?",new String[]{a},null,null,null);
           if (cur2.moveToFirst()){
               Product product=new Product(cur2.getString(0),cur2.getFloat(1),cur2.getString(2));
               prods.add(product);
           }

        }

        return prods;
    }
    public void delete_prod_devent(SQLiteDatabase db , int id_deprod_env, Long ventid){
        db.execSQL("delete from ventashasprod where venta="+ventid+" and prodid="+id_deprod_env+"");
    }

    public void insert_mod_de_prod_enventa(SQLiteDatabase db, int id_de_prod_env, int id_de_mod_en_v,Long venta,String prod,String mod ){
        ContentValues cont=new ContentValues();
        cont.put("modid", id_de_mod_en_v);
        cont.put("prodid",id_de_prod_env);
        cont.put("venta",venta);
        cont.put("prod",prod);
        cont.put("mod",mod);
        db.insert("prodchasmod",null,cont);
    }
    public ArrayList<Modificadores> getmod_deProd_enVenta(SQLiteDatabase db, Long venta, int idprod){
        ArrayList<Modificadores> modificadores=new ArrayList<>();
        ArrayList<String> nom_mods=new ArrayList<>();
        Cursor cur=db.rawQuery("select mod from prodchasmod where venta="+venta+" and prodid="+idprod+"",null);
        if (cur.moveToFirst()){
            do {
                nom_mods.add(cur.getString(0));
            }while (cur.moveToNext());
        }

        for (String a:nom_mods) {
            cur=db.query("modificadores",new String[]{"nombre","precio"},"nombre=?",new String[]{a},null,null,null);
            Modificadores mod=new Modificadores(cur.getString(0),cur.getFloat(1));
            modificadores.add(mod);
        }
        return modificadores;
    }
    public void delete_mod_de_prod_envent(SQLiteDatabase db, int id_de_prod_env, int id_de_mod_en_v,Long venta){
        db.execSQL("delete from prodchasmod where venta="+venta+" and modid="+id_de_mod_en_v+" and prodid="+id_de_prod_env+"");
    }

    public void insert_mod_de_mod_env(SQLiteDatabase db,int prodid, int modid, Long venta, String mod1, String mod2){
        ContentValues cont=new ContentValues();
        cont.put("modid",modid);
        cont.put("venta",venta);
        cont.put("mod1",mod1);
        cont.put("mod2",mod2);
        cont.put("prodid",prodid);
        db.insert("modchasmod",null,cont);
    }
    public  ArrayList<Modificadores> getmod_de_mod_en_venta(SQLiteDatabase db, Long venta, int idprod, int idmod){
        ArrayList<Modificadores> mods=new ArrayList<>();
        ArrayList<String> mods_nom=new ArrayList<>();
        Cursor cur=db.rawQuery("select mod2 from modchasmod where venta="+venta+" and prodid="+idprod+" and modid="+idmod+"",null);
        if (cur.moveToFirst()){
            do {
                mods_nom.add(cur.getString(0));
            }while (cur.moveToNext());
        }
        for (String a:mods_nom) {
            cur=db.query("modificadores",new String[]{"nombre","precio"},"nombre=?",new String[]{a},null,null,null);
            Modificadores mod= new Modificadores(cur.getString(0),cur.getFloat(1));
            mods.add(mod);
        }
        return mods;
    }
    public void delete_mod_de_mod_env(SQLiteDatabase db, int prodid, int modid, Long venta ){
        db.execSQL("delete from modchasmod where modid="+modid+" and prodid="+prodid+" and venta ="+venta+" ");
    }





    public void insert_mod_a_mod(SQLiteDatabase db,String mod1,String mod2){
        ContentValues cont=new ContentValues();
        cont.put("nomb1",mod1);
        cont.put("nom2",mod2);
        db.insert("modhasmod",null,cont);
    }
    public void delet_mod_de_mod(SQLiteDatabase db, String mod1, String mod2){
        db.execSQL("delete from modhasmod where nomb1='"+mod1+"' and nomb2= '"+mod2+"'");
    }
    public ArrayList<Modificadores> getmod_de_mod(SQLiteDatabase db,String mod){
        ArrayList<Modificadores> modificadores=new ArrayList<>();
        ArrayList<String> nom_mod=new ArrayList<>();
        Cursor cursor=db.query("modhasmod",new String[]{"nomb2"},"nomb1=?",new String[]{mod},null,null,null);
        if (cursor.moveToFirst()){
            do {
                nom_mod.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        for (String a:nom_mod) {
            cursor=db.query("modificadores",new String[]{"nombre","precio"},"nombre=?",new String[]{a},null,null,null);
            Modificadores modi=new Modificadores(cursor.getString(0),cursor.getFloat(1));
            modificadores.add(modi);
        }
        return modificadores;
    }

    public void insert_sumin_de_prod(SQLiteDatabase db,String sumin, String prod, float cantuv){

        ContentValues cont=new ContentValues();
        cont.put("cantuv",cantuv);
        cont.put("produ",prod);
        cont.put("sumin",sumin);
        db.insert("prodhassum",null,cont);
    }
    public void update_sumin_deprod(SQLiteDatabase db,String sumin, String prod, float cantuv){
        db.execSQL("update prodhassum set cantuv="+cantuv+" where produ='"+prod+"' and sumin='"+sumin+"' ");
    }
    public void delete_sumin_deprod(SQLiteDatabase db,String sumin, String prod){
        db.execSQL("delete from prodhassum where produ='"+prod+"' and sumin='"+sumin+"'");
    }
    public ArrayList<Sumin_Prod> get_suminde_prod(SQLiteDatabase db, String prod){
        ArrayList<Sumin_Prod> suministros=new ArrayList<>();
        ArrayList<String> nom_sum=new ArrayList<>();

        Cursor cursor=db.query("prodhassum",new String[]{"sumin","cantuv"},"produ=?",new String[]{prod},null,null,null);
        if (cursor.moveToFirst()){
            do {
                Cursor cur2=db.query("suministros",new String[]{"rowid","nombre","unidadC","unidadV","costo","relacionCV"},"nombre=?",new String[]{cursor.getString(0)},null,null,null);
                if (cur2.moveToFirst()){
                    Suministros sumin=new Suministros(cur2.getString(1),cur2.getString(2),cur2.getString(3),cur2.getFloat(4),cur2.getFloat(5));
                    sumin.setId(cur2.getLong(0));

                    Sumin_Prod sumin_prod=new Sumin_Prod();
                    sumin_prod.setSuministros(sumin);
                    sumin_prod.setCantuv(cursor.getFloat(1));
                    suministros.add(sumin_prod);
                }

            }while (cursor.moveToNext());
        }

        return suministros;
    }

    public void insert_mod_deprod(SQLiteDatabase db, String mod, String prod){
        ContentValues cont=new ContentValues();
        cont.put("prod",prod);
        cont.put("mod",mod);
        db.insert("prodhasmod",null,cont);
    }
    public void delete_mod_deprod(SQLiteDatabase db, String mod, String prod){
        db.execSQL("delete from prodhasmod where prod='"+prod+"' and mod='"+mod+"'");
    }
    public ArrayList<Modificadores> get_mod_de_prod(SQLiteDatabase db,String prod){
        ArrayList<Modificadores> modificadores=new ArrayList<>();
        ArrayList<String> nom_mod=new ArrayList<>();
        Cursor cur=db.query("prodhasmod",new String[]{"mod"},"prod=?",new String[]{prod},null,null,null);
        if (cur.moveToFirst()){
            do {
                nom_mod.add(cur.getString(0));
            }while (cur.moveToNext());
        }

        for (String a:nom_mod) {
            cur=db.query("modificadores",new String[]{"rowid","nombre","precio"},"nombre=?",new String[]{a},null,null,null,null);
            if (cur.moveToFirst()){
                do {
                    Modificadores modificadores1=new Modificadores(cur.getString(1),cur.getFloat(2));
                    modificadores1.setId(cur.getLong(0));
                    modificadores.add(modificadores1);
                }while (cur.moveToNext());
            }
        }

        return modificadores;
    }


    public void insert_sumin_demod(SQLiteDatabase db, String mod, String sum, float cantv){
        ContentValues cont=new ContentValues();
        cont.put("mod",mod);
        cont.put("sum",sum);
        cont.put("cantv",cantv);
        db.insert("modhassum",null, cont);
    }
    public void update_sum_de_mod(SQLiteDatabase db, String mod, String sum, float cantv){
        db.execSQL("update modhassum set cantv="+cantv+" where mod='"+mod+"' and sum='"+sum+"'");
    }
    public void delete_sum_de_mod(SQLiteDatabase db,String mod, String sum){
        db.execSQL("delete from modhassum where mod='"+mod+"' and sum='"+sum+"'");
    }
    public ArrayList<Sumin_Prod> get_sum_de_mod(SQLiteDatabase db,String mod){
        ArrayList<Sumin_Prod> suministros=new ArrayList<>();
        ArrayList<String> nom_sum=new ArrayList<>();
        Cursor cur=db.query("modhassum",new String[]{"sum","cantv"},"mod=?",new String[]{mod},null,null,null);
        if (cur.moveToFirst()){
            do {
                Cursor cur2=db.query("suministros",new String[]{"rowid","nombre","unidadC","unidadV","costo","relacionCV"},"nombre=?",new String[]{cur.getString(0)},null,null,null);
                if (cur2.moveToFirst()){
                    Suministros sumin=new Suministros(cur2.getString(1),cur2.getString(2),cur2.getString(3),cur2.getFloat(4),cur2.getFloat(5));
                    sumin.setId(cur2.getLong(0));

                    Sumin_Prod sumin_prod=new Sumin_Prod();
                    sumin_prod.setSuministros(sumin);
                    sumin_prod.setCantuv(cur.getFloat(1));
                    suministros.add(sumin_prod);
                }
            }while (cur.moveToNext());

        }


        return suministros;
    }




    public long insertubicacion(SQLiteDatabase db, String nombre,String phot){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nombre",nombre);
        contentValues.put("photo",phot);

        return db.insert("ubicacion",null,contentValues);
    }
    public ArrayList<Ubicacion> returnubicatio(SQLiteDatabase db){
        ArrayList<Ubicacion> listaubicaciones =new ArrayList<>();

        String[] slcstr={"rowid","nombre"};
        Cursor cur=db.query("ubicacion",slcstr,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Ubicacion ubi=new Ubicacion(cur.getString(1),cur.getLong(0));

                listaubicaciones.add(ubi);
            }while (cur.moveToNext());
        }
        return listaubicaciones;
    }
    public ArrayList<Ubicacion> returnubicationfull(SQLiteDatabase db){
        ArrayList<Ubicacion> listaubicaciones =new ArrayList<>();

        String[] slcstr={"rowid","nombre","photo"};
        Cursor cur=db.query("ubicacion",slcstr,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Ubicacion ubi=new Ubicacion(cur.getString(1),cur.getLong(0));
                ubi.setPhoto(cur.getString(2));
                listaubicaciones.add(ubi);
            }while (cur.moveToNext());
        }
        return listaubicaciones;
    }
    public ArrayList<String> returnubicationn(SQLiteDatabase db){
        ArrayList<String> listaubicaciones =new ArrayList<>();

        String[] slcstr={"nombre"};
        Cursor cur=db.query("ubicacion",slcstr,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {

                listaubicaciones.add(cur.getString(0));
            }while (cur.moveToNext());
        }
        return listaubicaciones;
    }
    public void updateubi(SQLiteDatabase db, Ubicacion ubi){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nombre",ubi.getNombre());
        contentValues.put("photo",ubi.getPhoto());
        String[] wherecla={ubi.getId().toString()};
        db.update("ubicacion",contentValues,"rowid=?",wherecla);


    }
    public void deleteubi(SQLiteDatabase db, Long id){
        db.delete("ubicacion","rowid=?",new String[]{id.toString()});
    }



    public long insertprod(SQLiteDatabase db,Product product ){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nombre",product.getNombre());
        contentValues.put("tipo",product.getTipo());
        contentValues.put("precio",product.getPrecio());
        return db.insert("productos",null,contentValues);
    }
    public ArrayList getprod(SQLiteDatabase db,String cate){
        ArrayList<Product> listaprod=new ArrayList<>();
        String[] stc={"prod"};
        String[] selcargs={cate};

        Cursor cur=db.query("prodhascat",stc,"cate=?",selcargs,null,null,null);
        if (cur.moveToFirst()){
            do {
                String[] stc2={"rowid","nombre","precio","tipo"};
                String[] selarg={cur.getString(0)};
                Cursor cur2=db.query("productos",stc2,"nombre=?",selarg,null,null,null);
                if (cur2.moveToFirst()){
                    do {
                        Product prod=new Product(cur2.getString(1),cur2.getFloat(2),cur2.getString(3));
                        prod.setId(cur2.getLong(0));
                        listaprod.add(prod);


                    }while (cur2.moveToNext());
                }
            }while (cur.moveToNext());
        }


        return listaprod;
    }
    public ArrayList getprod(SQLiteDatabase db){
        ArrayList<Product> listaprod=new ArrayList<>();
        String[] stc={"rowid","nombre","precio","tipo"};

        Cursor cur=db.query("productos",stc,"",null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Product prod=new Product(cur.getString(1),cur.getFloat(2),cur.getString(3));
                prod.setId(cur.getLong(0));
                listaprod.add(prod);
            }while (cur.moveToNext());
        }


        return listaprod;
    }
    public void deleteprod(SQLiteDatabase db, Long id){
        String where="rowid=?";
        String[] args={id.toString()};
        db.delete("productos ",where,args);
    }
    public void updateprod(SQLiteDatabase db, Product prod){
        ContentValues values=new ContentValues();
        values.put("nombre",prod.getNombre());
        values.put("precio",prod.getPrecio());
        values.put("tipo",prod.getTipo());
        String[] wherecla={prod.getId().toString()};
        db.update("productos",values,"rowid=?",wherecla);
    }


    public void setcatedeprod(SQLiteDatabase db, Product prod,ArrayList<String> cates){

        db.delete("prodhascat","prod=?",new String[]{prod.getNombre()});

        for (int i = 0; i <cates.size() ; i++) {
            ContentValues vals=new ContentValues();
            vals.put("prod",prod.getNombre());
            vals.put("cate",cates.get(i));
            db.insert("prodhascat",null,vals);

        }

    }
    public ArrayList<String> getcatedeprod(SQLiteDatabase db, String prod){
        ArrayList<String> cates=new ArrayList<>();
        String[] cols={"cate"};

        Cursor cur=db.query("prodhascat",cols,"prod=?",new String[]{prod},null,null,null);
        if (cur.moveToNext()){
            do {
                cates.add(cur.getString(0));
            }while (cur.moveToNext());
        }

        return cates;
    }




    public ArrayList getprodbyname(SQLiteDatabase db, String name){
        ArrayList<Product> listaprod=new ArrayList<>();
        String[] stc={"nombre","precio","tipo"};

        Cursor cur=db.query("productos",stc,"nombre LIKE ?",new String[]{"%"+name+"%"},null,null,null);
        if (cur.moveToFirst()){
            do {
                Product prod=new Product(cur.getString(0),cur.getFloat(1),cur.getString(2));
                listaprod.add(prod);
            }while (cur.moveToNext());
        }


        return listaprod;
    }



    public long insertsumin(SQLiteDatabase db, Suministros sumin){
        ContentValues content=new ContentValues();
        content.put("nombre",sumin.getNombre());
        content.put("unidadC", sumin.getU_comp());
        content.put("unidadV",sumin.getU_vent());
        content.put("costo",sumin.getCosto());
        content.put("relacionCV",sumin.getUvxuc());

        long a= db.insert("suministros",null,content);
        if (a!=-1){
            declararexist(db,0,0,sumin.getNombre());
        }
        return a;
    }
    public void updatesumin(SQLiteDatabase db,Suministros sumin){
        ContentValues content=new ContentValues();
        content.put("nombre",sumin.getNombre());
        content.put("unidadC", sumin.getU_comp());
        content.put("unidadV",sumin.getU_vent());
        content.put("costo",sumin.getCosto());
        content.put("relacionCV",sumin.getUvxuc());
        db.update("suministros",content,"rowid=?",new String[]{Long.toString(sumin.getId())});
    }
    public void deletesumin(SQLiteDatabase db,Suministros sumin){
        db.delete("suministros","nombre=?",new String[]{sumin.getNombre()});
    }
    public ArrayList<Suministros> returnsumin(SQLiteDatabase db){
        ArrayList<Suministros> suministros=new ArrayList<>();

        Cursor cur=db.query("suministros",new String[]{"rowid","nombre","unidadC","unidadV","costo","relacionCV"},null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Suministros sumin=new Suministros(cur.getString(1),cur.getString(2),cur.getString(3),cur.getFloat(4),cur.getFloat(5));
                sumin.setId(cur.getLong(0));
                suministros.add(sumin);
            }while (cur.moveToNext());
        }
        return suministros;
    }


    public long insertmod(SQLiteDatabase db, Modificadores mod){
        ContentValues content=new ContentValues();
        content.put("nombre",mod.getNombre());
        content.put("precio", mod.getPrecio());

        Long a=-1l;
        try {
            a=db.insert("modificadores",null,content);
        }catch (Exception e){
        }
        return a;
    }
    public void updatemod(SQLiteDatabase db, Modificadores mod){
        ContentValues content=new ContentValues();
        content.put("nombre",mod.getNombre());
        content.put("precio", mod.getPrecio());
        db.update("modificadores",content,"rowid=?",new String[]{Long.toString(mod.getId())});

    }
    public void deletemod (SQLiteDatabase db, Modificadores mod){
        db.delete("modificadores","nombre=?",new String[]{mod.getNombre()});
    }
    public ArrayList<Modificadores> get_modificadores(SQLiteDatabase db){
        ArrayList<Modificadores> modificadores=new ArrayList<>();
        Cursor cursor=db.query("modificadores",new String[]{"rowid","nombre","precio"},null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                Modificadores modificadores1=new Modificadores(cursor.getString(1),cursor.getFloat(2));
                modificadores1.setId(cursor.getLong(0));
                modificadores.add(modificadores1);
            }while (cursor.moveToNext());
        }

        return modificadores;
    }


    public long insertmesa(SQLiteDatabase db, Mesas mesa,Context context){
        ContentValues content=new ContentValues();
        content.put("nombre",mesa.getNombre());
        Long a=-1L;
        content.put("status",mesa.getStatus());
        content.put("ubica",mesa.getUbicacion());
        try {
            a=db.insertOrThrow("mesas",null,content);

        }catch (Exception e){
            Toast  toast=Toast.makeText(context,"Error "+e.getMessage(),Toast.LENGTH_LONG);
            toast.show();
        }

        return a;

    }
    public ArrayList<Mesas>  returnmesas(SQLiteDatabase db, String ubica,Context cont){
        ArrayList<Mesas> mesaslist=new ArrayList<>();
        String[] asd= {ubica};

                Cursor cur=db.query("mesas",new String[]{"rowid","nombre","status","ubica"},"ubica=?",asd,null,null,null);


            if (cur.moveToFirst()){
                do {

                        Mesas mesa=new Mesas(cur.getString(1));
                        mesa.setIdmesa(cur.getLong(0));
                        mesa.setStatus(cur.getInt(2));
                        mesa.setUbicacion(cur.getString(3));
                        mesaslist.add(mesa);




                }while (cur.moveToNext());
            }
            return mesaslist;

    }
    public ArrayList<Mesas>  returnmesas(SQLiteDatabase db){
        ArrayList<Mesas> mesaslist=new ArrayList<>();
        //String[] asd= {ubica};
        Cursor cur=db.query("mesas",new String[]{"rowid","nombre","status","ubica"},null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                //Toast  toast=Toast.makeText(context,Long.toString(cur.getLong(0))+" "+cur.getString(1)+" "+cur.getString(2),Toast.LENGTH_LONG);
                //toast.show();
                Mesas mesa=new Mesas(cur.getString(1));
                mesa.setIdmesa(cur.getLong(0));
                mesa.setStatus(cur.getInt(2));
                mesa.setUbicacion(cur.getString(3));
                mesaslist.add(mesa);

            }while (cur.moveToNext());
        }
        return mesaslist;
    }
    public void deletemesa(SQLiteDatabase db,Long id){
        db.delete("mesas","rowid=?",new String[]{id.toString()});
    }
    public void updatemesa(SQLiteDatabase db, Mesas mesa ){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nombre",mesa.getNombre());
        contentValues.put("status",mesa.getStatus());
        contentValues.put("ubica",mesa.getUbicacion());
        db.update("mesas",contentValues,"rowid=?",new String[]{Long.toString(mesa.getIdmesa())});

    }


    public Long insertcate(SQLiteDatabase db, Categorias cate){
        ContentValues content=new ContentValues();
        content.put("nombre",cate.getNombre());
        content.put("color",cate.getColor());
        return db.insert("categorias",null,content);

    }
    public ArrayList<Categorias> getcate(SQLiteDatabase db){
        ArrayList<Categorias> categorias=new ArrayList<>();

        String[] col={"rowid","nombre","color"};
        Cursor cur=db.query("categorias",col,null,null,null,null,null);
        if (cur.moveToFirst()){
            do {
                Categorias cat=new Categorias(cur.getString(1));
                cat.setId(cur.getLong(0));
                cat.setColor(cur.getString(2));
                categorias.add(cat);

            }while (cur.moveToNext());
        }
        return categorias;

    }
    public void deletecate(SQLiteDatabase db,Long id){
        db.delete("categorias","rowid=?",new String[]{Long.toString(id)});
    }
    public void updatecate(SQLiteDatabase db, Categorias cate){
        ContentValues content=new ContentValues();
        content.put("nombre",cate.getNombre());
        content.put("color",cate.getColor());
        //content.put("",);
        db.update("categorias",content,"rowid=?",new String[]{Long.toString(cate.getId())});
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
