package com.example.yacinebenkaidali.dxstock;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.sql.Date;

import BDD.Datasrc;
import Donnes.Article;
import Donnes.Client;
import Donnes.Vendeur;

public class Admin extends AppCompatActivity {
    Datasrc dbsrc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        dbsrc =new Datasrc(this);
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //permission method for android +6.
    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        }
        return true;
    }


        public void importFile(View view){
        if(verifyStoragePermissions(this)){
            importFileArticle();
            importFileClient();
            importFileVendeur();
            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "prendre la permission et réessayer", Toast.LENGTH_SHORT).show();

        }
    }



    public void importFileVendeur(){

        try {
            FileInputStream fils = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/dxstock/VAN.csv");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fils));
            String line = "";

            if ((line = buffer.readLine()) != null) {
                // test if it is the correct file
                String[] str = line.split(";", 3);
                String id = str[0];
                String username = str[1];
                String pwd = str[2];
                // check if it is the right csv
                if (id.equalsIgnoreCase("CODE_VAN") && username.equalsIgnoreCase("NOM_Van")
                        && pwd.equalsIgnoreCase("PWD")) {

                    dbsrc.cleardatabaseVendeur();
                    while ((line = buffer.readLine()) != null) {
                        str = line.split(";", 3);  // defining 3 columns with null or blank field //values acceptance
                        id = str[0];
                        username = str[1];
                        pwd = str[2];
                        Vendeur v=new Vendeur(id,username,pwd);
                        dbsrc.createVendeur(v);
                    }
                    dbsrc.close();

                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getApplicationContext(), "Not correct File", Toast.LENGTH_LONG).show();

                }
            } else {
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            dbsrc.close();
        }
    }

    private void importFileClient() {
        try {
            FileInputStream fils = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/dxstock/CLIENTS_VAN.csv");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fils));
            String line = "";

            if ((line = buffer.readLine()) != null) {
                // test if it is the correct file
                String[] str = line.split(";", 6);
                String code_clt = str[0];
                String code_van = str[1];
                String nom_clt = str[2];
                String adr = str[3];
                String tel = str[4];
                String no_route = str[5];


                // check if it is the right csv
                if (adr.equalsIgnoreCase("ADRESSE")&nom_clt.equalsIgnoreCase("NOM_CLIENT")
                        && code_clt.equalsIgnoreCase("CODE_CLTV")&& code_van.equalsIgnoreCase("CODE_VAN")
                        && tel.equalsIgnoreCase("TEL")&& no_route.equalsIgnoreCase("NO_ROUTE")
                        ) {
                    dbsrc.cleardatabaseClient();
                    while ((line = buffer.readLine()) != null) {
                        str = line.split(";", 6);  // defining 6 columns with null or blank field //values acceptance
                        code_clt = str[0];
                        code_van = str[1];
                        nom_clt = str[2];
                        adr = str[3];
                        tel = str[4];
                        no_route = str[5];
                        Client c =new Client(adr,Integer.parseInt(code_clt),code_van,Date.valueOf("2001-11-1"),
                                nom_clt, tel,Integer.parseInt("01"));
                        dbsrc.createClient(c);

                    }
                    dbsrc.close();

                } else {
                    Toast.makeText(getApplicationContext(), "Not correct File", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(getApplicationContext(), "File is clear", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            dbsrc.close();
        }
    }

    private void importFileArticle() {
            try {
            FileInputStream fils = new FileInputStream(Environment.getExternalStorageDirectory().getAbsolutePath()+"/dxstock/PRODUITS.csv");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fils));
            String line = "";

            if ((line = buffer.readLine()) != null) {
                // test if it is the correct file
                String[] str = line.split(";", 5);
                String code_art = str[0];
                String desi = str[1];
                String code_type = str[2];
                String prix = str[3];
                String Qte_charge = str[4];


                if (code_art.equalsIgnoreCase("CODE_ARTICLE") && desi.equalsIgnoreCase("DESIGNATION")
                        && code_type.equalsIgnoreCase("CODE_TYPE") && prix.equalsIgnoreCase("PRIX_VENTE")
                        && Qte_charge.equalsIgnoreCase("QUANTITE")
                       ) {

                   dbsrc.cleardatabaseArticle();
                    while ((line = buffer.readLine()) != null) {
                        str = line.split(";",5);  // defining 3 columns with null or blank field //values acceptance
                        code_art = str[0];
                        desi = str[1];
                        code_type = str[2];
                        prix = str[3];
                        Qte_charge = str[4];
                        Article a =new Article(code_art,desi,code_type,Double.parseDouble(prix)
                                ,Integer.parseInt(Qte_charge),0);
                        dbsrc.createArticle(a);
                    }
                    dbsrc.close();

                } else {
                    Toast.makeText(getApplicationContext(), "Not correct File", Toast.LENGTH_LONG).show();

                }
            } else {
                Toast.makeText(getApplicationContext(), "File is clear", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            dbsrc.close();
        }
    }



        public boolean exportDatabase(View v) {
      // verifyStoragePermissions(this);
            //We use the Download directory for saving our .csv file.
            File root = Environment.getExternalStorageDirectory();
            File dir= new File(root.getAbsolutePath()+"/dxstock");
            if(!dir.exists()){
                dir.mkdir();
            }
            File file;
            try
            {
                file = new File(dir, "Livraisons_tete.csv");
                file.createNewFile();
                FileWriter fw = new FileWriter(file);
                rapport(fw,"LIVRAISON_TETE");
                file = new File(dir, "Livraisons_detaillée.csv");
                file.createNewFile();
                fw = new FileWriter(file);
                rapport(fw,"LIVRAISON_DETAIL");
                file = new File(dir, "Reclamations.csv");
                file.createNewFile();
                fw = new FileWriter(file);
                rapport(fw,"RECLAMATION");
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
            } catch(Exception exc) {
                Toast.makeText(getApplicationContext(), exc.getMessage(),Toast.LENGTH_SHORT).show();
                exc.printStackTrace();
                return false;
            }
            return true;
    }

    public void rapport(FileWriter fw,String nomBDD){
        SQLiteDatabase db = null;
        db = this.openOrCreateDatabase("DxBDD", MODE_PRIVATE, null);
        try {
            Cursor c = db.rawQuery("SELECT * FROM "+nomBDD  , null);
            BufferedWriter bw = new BufferedWriter(fw);
            int rowcount = 0;
            int colcount = 0;
            rowcount = c.getCount();
            colcount = c.getColumnCount();
            if (rowcount > 0) {
                c.moveToFirst();
                for (int i = 0; i < colcount; i++) {
                    if (i != colcount - 1) {
                        bw.write(c.getColumnName(i) + ";");
                    } else {
                        bw.write(c.getColumnName(i));
                    }
                }
                bw.newLine();

                for (int i = 0; i < rowcount; i++) {
                    c.moveToPosition(i);
                    for (int j = 0; j < colcount; j++) {
                        if (j != colcount - 1)
                            bw.write(c.getString(j) + ";");
                        else
                            bw.write(c.getString(j));
                    }
                    bw.newLine();
                }
                bw.newLine();
                bw.newLine();
                bw.newLine();
                bw.flush();
            }
            db.close();
        }catch (Exception e){

        }
    }

    public void changePwd(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Changer le mot de passe");
        builder.setMessage("Entrer votre nouveau mot de passe");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT );
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                SharedPreferences sharedPreferences = Admin.this.getSharedPreferences("com.example.yacinebenkaidali.dxstock", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("Password", m_Text).apply();
                Toast.makeText(getApplicationContext(),"Le nouveau mot de passe est enrengistré",Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Quiter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
