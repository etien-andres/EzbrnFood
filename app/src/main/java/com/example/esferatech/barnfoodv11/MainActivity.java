package com.example.esferatech.barnfoodv11;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import DAL.Entidades.Mesas;
import DAL.Entidades.Usuarios;
import DAL.Sqlitehelp;
import com.vanstone.trans.api.SystemApi;
import com.vanstone.trans.api.constants.GlobalConstants;
import com.vanstone.utils.CommonConvert;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;

    Sqlitehelp sqlitehelp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        setSupportActionBar(toolbar);
        Sqlitehelp sqlitehelp=new Sqlitehelp(this,"base",null,1);
        Estaticas.db= sqlitehelp.getReadableDatabase();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment fr1=null;
        sqlitehelp1=new Sqlitehelp(getApplicationContext(),"base",null,1);

        setadminuser();
        fr1=new fragmentoventas();
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fr1).commit();

        new Thread(){
            public void run() {
                while(true){
                    SystemApi.SystemInit_Api(0, CommonConvert.StringToBytes(GlobalConstants.CurAppDir+"/" + "\0"), MainActivity.this);
                    break;
                }

            };
        }.start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fr=null;
        boolean selected=false;
        if (id == R.id.nav_venta) {
            fr=new fragmentoventas();
            selected=true;
        } else if (id == R.id.nav_super) {
            fr=new supervision();
            selected=true;
        } else if (id == R.id.nav_adm) {
            fr=new Administracion();
            selected=true;
        } else if (id == R.id.nav_manage) {

        }
        if (selected){
            getSupportFragmentManager().beginTransaction().replace(R.id.contenedor,fr).commit();

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static void changetitle(String str){
        toolbar.setTitle(str);
    }

    public void setadminuser(){
        ArrayList<Usuarios> usuarios=sqlitehelp1.getusers(Estaticas.db);
        for (Usuarios a:usuarios) {
            if (a.getNombre().equals("admin")){
                Estaticas.usuario_actual=a;
            }
        }

    }


}
