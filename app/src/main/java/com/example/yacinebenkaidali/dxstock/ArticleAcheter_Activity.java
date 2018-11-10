package com.example.yacinebenkaidali.dxstock;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.AchterAdapter;
import BDD.Datasrc;
import Donnes.Articletmp;

public class ArticleAcheter_Activity extends AppCompatActivity {
    Double total=0.0;
    Datasrc dbsrc;
    public List<Articletmp> articles = new ArrayList<>();
    String id_ven;int id_client;long code_bon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_acheter);

        id_ven=getIntent().getStringExtra("id_ven");
        id_client=getIntent().getIntExtra("id_client",0);
        code_bon=getIntent().getLongExtra("code_bon",0);

        ListView lv=(ListView)findViewById(R.id.list_acheter);
        if((getIntent().getStringExtra("act code")).equals("rembourser"))
            generatelistContent(-1);
        else  generatelistContent(code_bon);
        lv.setAdapter(new AchterAdapter(ArticleAcheter_Activity.this, R.layout.list_view_article_acheter, articles));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.imprimer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.imprimer) {
            new AlertDialog.Builder(this)
                    .setTitle("Impression du Bon")
                    .setMessage("Vous Ãªtes sur de votre liste des achats ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //ici on mettera le code de l'impression
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void generatelistContent(long CODE_BON)
    {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        articles = dbsrc.findallarticlesacheter(id_ven, id_client,CODE_BON);
        dbsrc.close();
    }

}
