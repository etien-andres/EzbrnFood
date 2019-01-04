package com.example.esferatech.barnfoodv11;

import android.util.Log;

import com.vanstone.trans.api.PrinterApi;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import DAL.Entidades.Product;

public class Print {
    public static int PrtTicket( String str)
    {
        PrinterApi.PrnClrBuff_Api();
        PrinterApi.PrnFontSet_Api(50, 0, 0);
        PrinterApi.PrnSetGray_Api(30);
        PrinterApi.PrnLineSpaceSet_Api((short) 5,0);

        //PrinterApi.PrnFontSet_Api(24, 24, 0);
        PrinterApi.PrnStr_Api(str);

        //PrintData();
        PrinterApi.PrnStart_Api();
        PrinterApi.printFeedLine_Api(2);

        PrinterApi.PrnCut_Api();

        return 0;
    }
    //public static void put

    public static void prntCuenta(ArrayList<Product> products){
        DecimalFormat df;
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        df = new DecimalFormat("", otherSymbols);
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);


        PrinterApi.PrnClrBuff_Api();
        PrinterApi.PrnSetGray_Api(30);
        PrinterApi.PrnLineSpaceSet_Api((short) 5,0);
        PrinterApi.PrnFontSet_Api(50, 0, 0);
        PrinterApi.PrnStr_Api("    Cuenta\n");
        PrinterApi.PrnFontSet_Api(30, 0, 0);
        for (Product a:products) {
            String nom=a.getNombre();
            String finalnom="";
            if (nom.length()>14){
                for (int i = 0; i <13; i++) {
                    finalnom+=nom.charAt(i);
                }
                finalnom+="... ";

            }
            else {
                finalnom=nom;
                for (int i = nom.length(); i <17 ; i++) {
                    finalnom+=" ";
                }
            }



            PrinterApi.PrnStr_Api(finalnom+"$"+df.format(a.getPrecio())+"\n");
        }
        PrinterApi.PrnStr_Api("           Total:$"+df.format(gettotal(products)));

        PrinterApi.PrnStart_Api();
        PrinterApi.printFeedLine_Api(2);



    }


    static float gettotal(ArrayList<Product> products){
        float total=0;
        for (Product a:products) {
            total+=a.getPrecio();
        }
        return total;
    }
}
