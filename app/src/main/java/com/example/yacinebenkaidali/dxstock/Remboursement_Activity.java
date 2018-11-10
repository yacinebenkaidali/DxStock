package com.example.yacinebenkaidali.dxstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Adapters.RembouresementAdapter;
import BDD.Datasrc;
import Donnes.Articletmp;
import Donnes.Livraison_detail;

public class Remboursement_Activity extends AppCompatActivity
{

    Datasrc dbsrc;

    public List<Articletmp> articles = new ArrayList<>();
    int client_id;
    String ven_id;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remboursement);
        RembouresementAdapter.MAJdb.clear();

        client_id = getIntent().getIntExtra("id client", 1);
        ven_id = getIntent().getStringExtra("id ven");
        lv = (ListView) findViewById(R.id.list_rem);
        generatelistContent();

        if (articles.size() >0 )
        {
            lv.setItemsCanFocus(true);
            lv.setAdapter(new RembouresementAdapter(Remboursement_Activity.this, R.layout.list_view_article, articles, client_id));
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.finish, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.finish) {
            dbsrc.open();
            for (Livraison_detail l : RembouresementAdapter.MAJdb)
            {
                dbsrc.updateArticle(l.CODE_ARTICLE,l.QTE_TMP-l.QTE_LIVRE,false);
                if(l.QTE_LIVRE==0.0)
                {
                    dbsrc.supprimerLivraisondetail(l);
                    dbsrc.updateMontant(l);
                }
                else {
                    dbsrc.updateLivraisondetail(l);
                    dbsrc.updateMontant(l);
                }
            }
            RembouresementAdapter.MAJdb.clear();
        }
        dbsrc.close();
        Intent in = new Intent(Remboursement_Activity.this, ArticleAcheter_Activity.class);
        in.putExtra("id_client", client_id);
        in.putExtra("id_ven", ven_id);
        in.putExtra("act code","rembourser");
        startActivity(in);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    private void generatelistContent() {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        articles = dbsrc.findallarticlesacheter(ven_id, client_id,-1);
        if (articles.size()==0)
        {
            Toast.makeText(Remboursement_Activity.this, "ce client n'a rien acheté", Toast.LENGTH_SHORT).show();
        }
        dbsrc.close();
    }


}
