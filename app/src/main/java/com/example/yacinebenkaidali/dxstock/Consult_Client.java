package com.example.yacinebenkaidali.dxstock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import Adapters.ClientAdapter;
import BDD.Datasrc;
import Donnes.Article;
import Donnes.Client;
import Donnes.Vendeur;




public class Consult_Client extends AppCompatActivity {
    Datasrc dbsrc;
    public String id_ven;
    public ClientAdapter adapter;
    ListView lv;
    SearchView sv;

    public List<Client> clients = new ArrayList<>();
    public List<Client> clientsFiltre = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult__client);
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.yacinebenkaidali.dxstock", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("UserName", "");
        getSupportActionBar().setTitle("Salut "+user);
        lv = (ListView) findViewById(R.id.lvclient);
        sv= (SearchView) findViewById(R.id.sv);
        id_ven = sharedPreferences.getString("UserCode", "");
        generatelistContent();
        adapter=new ClientAdapter(Consult_Client.this, R.layout.list_view_client, clients,id_ven);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client c = clients.get(i);
                Intent in = new Intent(Consult_Client.this, Consultation_Activity.class);
                in.putExtra("nom du client", c.NOM_CLIENT);
                in.putExtra("tel", c.Tel);
                in.putExtra("adr", c.Adresse);
                in.putExtra("Date_cr", c.DATE_CREATION.toString());
                in.putExtra("nrout", Integer.toString(c.NO_ROUTE));
                startActivity(in);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    adapter=new ClientAdapter(Consult_Client.this, R.layout.list_view_client, clients,id_ven);
                    lv.setAdapter(adapter);
                }else {
                    getClient(newText);
                }
                return false;
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();
        dbsrc.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbsrc.close();
    }



    private void getClient(String searchTerm)
    {
        clientsFiltre.clear();
        dbsrc = new Datasrc(this);
        dbsrc.open();
        clientsFiltre=dbsrc.filtreClient(this,searchTerm);
        dbsrc.close();
        adapter=new ClientAdapter(Consult_Client.this, R.layout.list_view_client, clientsFiltre,id_ven);
        lv.setAdapter(adapter);

    }


    public void generatelistContent()
    {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        clients = dbsrc.findallClient(this, id_ven);
        dbsrc.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.yacinebenkaidali.dxstock", Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("Login", false).apply();
            startActivity(i);
            finish();
        }else if (id == R.id.sendmmail){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirmation");
                builder.setMessage("Voulez vous envoyer les ficiers");

                // Set up the buttons
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       try{
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("vnd.android.cursor.dir/email");
                        String to[] = {"fz_ouldimam@esi.dz"};
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);

                        File filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dxstock", "Reclamations.csv");
                        Uri path = Uri.fromFile(filelocation);
                        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                        filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dxstock", "Livraisons_tete.csv");
                        path = Uri.fromFile(filelocation);
                        emailIntent.putExtra(Intent.EXTRA_STREAM, path);
                        filelocation = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/dxstock", "Livraisons_detaillée.csv");
                        path = Uri.fromFile(filelocation);
                        emailIntent.putExtra(Intent.EXTRA_STREAM, path);

// the mail subject
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Rapport");
                        emailIntent.putExtra(Intent.EXTRA_TEXT, "Vous trouver en piece jointe les fichiers d'aujourd'hui");
                        startActivity(Intent.createChooser(emailIntent, "Send email..."));
                    }catch (Exception e){
                        Toast.makeText(Consult_Client.this, "Envoi echec", Toast.LENGTH_SHORT).show();
                    }

                }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
        }

        return super.onOptionsItemSelected(item);
    }

}

