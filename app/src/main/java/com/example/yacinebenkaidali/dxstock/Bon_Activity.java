package com.example.yacinebenkaidali.dxstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.BonAdapter;
import BDD.Datasrc;
import Donnes.Article;
import Donnes.Articletmp;

public class Bon_Activity extends AppCompatActivity {
    public Datasrc dbsrc;ListView lv;String code_act;
    public List<Articletmp> bons = new ArrayList<>();
    public List<Articletmp> bonsFiltre = new ArrayList<>();

    String ven_id;int client_id;
    SearchView sv;
    BonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bon);
        client_id = getIntent().getIntExtra("id_client",0);
        ven_id = getIntent().getStringExtra("id_ven");
        code_act=getIntent().getStringExtra("code_act");
        generateListContent();
        lv=(ListView)findViewById(R.id.lv_bons);
        sv= (SearchView) findViewById(R.id.sv3);

        if (lv != null) {
            adapter=new BonAdapter(Bon_Activity.this, R.layout.list_view_bon, bons);
            lv.setAdapter(adapter);

        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Articletmp a=bons.get(i);
                Intent in = new Intent(Bon_Activity.this, ArticleAcheter_Activity.class);
                in.putExtra("id_client", client_id);
                in.putExtra("id_ven", ven_id);
                in.putExtra("code_bon",a.Code_Bon);
                in.putExtra("act code","bon");
                startActivity(in);
            }
        });

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    adapter=new BonAdapter(Bon_Activity.this, R.layout.list_view_bon, bons);
                    lv.setAdapter(adapter);
                }else
                getBon(newText);
                return false;
            }
        });

    }

    private void getBon(String searchTerm)
    {
        bonsFiltre.clear();
        dbsrc = new Datasrc(this);
        dbsrc.open();
        bonsFiltre=dbsrc.filtreBon(this,searchTerm);
        dbsrc.close();
        adapter=new BonAdapter(Bon_Activity.this, R.layout.list_view_bon, bonsFiltre);
        lv.setAdapter(adapter);

    }
    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void generateListContent()
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date tmp= Calendar.getInstance().getTime();
        String datenow=df.format(tmp);
        dbsrc = new Datasrc(this);
        dbsrc.open();
        if(code_act.equals("client"))
        {
            bons = dbsrc.findallbon(client_id,"");
        }
        else {
            bons = dbsrc.findallbon(client_id,datenow);
        }
        if (bons.size() == 0)
        {
            Toast.makeText(Bon_Activity.this, "Pas de Bons !", Toast.LENGTH_LONG).show();
        }
        dbsrc.close();
    }
}
