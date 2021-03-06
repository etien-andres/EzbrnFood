package com.example.esferatech.barnfoodv11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import DAL.Entidades.Ventas;
import DAL.Sqlitehelp;

public class Cobrar extends AppCompatActivity {
    RadioGroup grupo_tipo_pago;
    ImageButton back;
    Button button;
    TextView total,cambio;
    Sqlitehelp helper;
    Float prop,gran_total;
    EditText propina,pago_;
    Float tot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cobrar);
        helper=new Sqlitehelp(getApplicationContext(),"base",null,1);

        back=findViewById(R.id.botonback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button=findViewById(R.id.pagar_button);
        total=findViewById(R.id.total_text);
        cambio=findViewById(R.id.cambio_text);
        propina=findViewById(R.id.propina);
        pago_=findViewById(R.id.pago);

        Long idvent=helper.get_ventas_temp(Estaticas.db,Estaticas.nombremesaact.getNombre());
        Ventas vta=helper.getventas(Estaticas.db,idvent);

        tot=vta.getTotal();
        total.setText(Float.toString(tot));
        grupo_tipo_pago=findViewById(R.id.radioGroup);
        grupo_tipo_pago.check(R.id.efectivo_radio);
        Estaticas.settitle(this,"Cobrar");
        pago_.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkchange();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        propina.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkchange();            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagar();
            }
        });
        //check_propina();
        checkchange();


    }

    public void pagar(){

        if (checkchange()>=0){
            float prop=helper.getprops(Estaticas.db);
            float pago;
            float cambio;
            float propi;
            pago=checkpago();
            propi=check_propina();
            cambio=checkchange();
            helper.update_fondo_de_caja(Estaticas.db,helper.get_fondo(Estaticas.db)+pago);
            if (cambio>0){
                helper.update_fondo_de_caja(Estaticas.db,helper.get_fondo(Estaticas.db)-cambio);
            }
            if (propi>0){
                prop+=propi;
                helper.updateprops(Estaticas.db,prop);
            }
            Estaticas.nombremesaact.setStatus(1);
            helper.updatemesa(Estaticas.db,Estaticas.nombremesaact);
            helper.delete_venta_temp(Estaticas.db,Estaticas.nombremesaact.getNombre());
            fragmentoventas.updategridmesa();
            finish();
        }
        else {
            Toast tst=Toast.makeText(getApplicationContext(),"Pago incorrecto.",Toast.LENGTH_SHORT);
            tst.show();
        }
    }

    public float checkchange(){
        Float a=checkpago()-check_propina()-tot;
        cambio.setText(Float.toString(a));
        if (a<0){
            return -1;
        }
        else {
            return a;
        }
    }
    public float checkpago(){
        float a=0f;
        if (!pago_.getText().toString().equals("")){
            a=Float.parseFloat(pago_.getText().toString());
        }
        return a;
    }
    public Float check_propina(){
        if (propina.getText().toString().equals("")){
            prop=0F;

        }
        else {
            prop=Float.parseFloat(propina.getText().toString());
        }
        return prop;
    }


}
