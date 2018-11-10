package com.example.yacinebenkaidali.dxstock;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Adapters.ArticleAdapter;
import BDD.Datasrc;
import Donnes.Article;
import Donnes.Livraison_detail;
import Donnes.Livraison_tete;
import Exceptions.MontantNullException;
import Exceptions.VersementNullException;



public class Produits_Activity extends AppCompatActivity {

    public Datasrc dbsrc;
    public List<Article> articles = new ArrayList<>();
    public List<Article> articlesFiltre = new ArrayList<>();
    public int client_id;
    public String ven_id;
    TextView montant;
    SearchView searchView;
    public ListView lv;
    public ArticleAdapter adapter;
    String mode_paiement;
    double versement=0.0 ;
    double total=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produits);
        ArticleAdapter.MAJdb.clear();

        client_id = getIntent().getIntExtra("id client", 1);
        ven_id = getIntent().getStringExtra("id ven");
        lv = (ListView) findViewById(R.id.listView_prod);
        montant=(TextView)findViewById(R.id.prixtotal_tmp);
        searchView= (SearchView) findViewById(R.id.sv2);

        generatelistContent();

        if (lv != null)
        {
            lv.setItemsCanFocus(true);
            adapter=new ArticleAdapter(Produits_Activity.this, R.layout.list_view_article, articles);
            lv.setAdapter(adapter);
        }

        Spinner spinner= (Spinner) findViewById(R.id.spinner);
        String spinnerItem[]={"Espece","Chèque","Versement"};
        ArrayAdapter<String> spinneradapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinnerItem);
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        if (spinner != null) {
            spinner.setAdapter(spinneradapter);
        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mode_paiement= parent.getItemAtPosition(position).toString();
                final EditText et=(EditText)findViewById(R.id.versement_montant);
                if(mode_paiement.equals("Versement"))
                {
                    et.setVisibility(View.VISIBLE);
                    et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean hasFocus) {
                            if(!hasFocus) versement=Double.parseDouble(et.getText().toString());
                        }
                    });


                }else {
                    et.setVisibility(View.INVISIBLE);
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
                mode_paiement="Espece";
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.equals("")){
                    adapter=new ArticleAdapter(Produits_Activity.this, R.layout.list_view_article, articles);
                    lv.setAdapter(adapter);
                }else
                getProduit(newText);
                return false;
            }
        });

        Button totale_tmp= findViewById(R.id.totale_tmp);
        totale_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                total=0.0;
                for (Livraison_detail l1 : ArticleAdapter.MAJdb)
                {
                    for (Article a:articles)
                    {
                        if(a.CODE_ARTICLE.equals(l1.CODE_ARTICLE))
                        {
                            total+=a.PRIX_VENTE*l1.QTE_LIVRE;
                        }
                    }
                }
                montant.setText(new DecimalFormat(".###").format(total));
            }
        });
    }

    private void getProduit(String searchTerm)
    {
        articlesFiltre.clear();
        dbsrc = new Datasrc(this);
        dbsrc.open();
        articlesFiltre=dbsrc.filtreArtcile(this,searchTerm);
        dbsrc.close();
        adapter=new ArticleAdapter(Produits_Activity.this, R.layout.list_view_article, articlesFiltre);
        lv.setAdapter(adapter);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.finish, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.finish) {
            dbsrc.open();
            Livraison_tete l=new Livraison_tete();
            SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            Date date = Calendar.getInstance().getTime();
            String datenow=df.format(date);
            l.DATE_LIVRAISON= java.sql.Date.valueOf(datenow);

            l.MODE_REGLEMENT=mode_paiement;
            l.CODE_CLTV=this.client_id;
            try {

                if (total==0.0 )
                    throw new MontantNullException();
                else l.MONTANT=total;
                if (mode_paiement.equals("Versement") && versement==0.0)
                    throw new VersementNullException();
                else l.VERSEMENT_TRANCHE=versement;

                long code_bon =dbsrc.createLivraisontete(l);
                for (Livraison_detail l1 : ArticleAdapter.MAJdb)
                {
                    l1.CODE_BON=code_bon;
                    dbsrc.createLivraisondetail(l1);
                    dbsrc.updateArticle(l1.CODE_ARTICLE, l1.QTE_LIVRE,true);
                }
                ArticleAdapter.MAJdb.clear();
                dbsrc.close();
                Intent in = new Intent(Produits_Activity.this, Bon_Activity.class);
                in.putExtra("id_client", client_id);
                in.putExtra("id_ven", ven_id);
                in.putExtra("code_act","pro");
                //in.putExtra("act code","produit");
                startActivity(in);
                finish();
            } catch (MontantNullException e) {
                Toast.makeText(Produits_Activity.this, "vous n'avez pas Calculer le montant", Toast.LENGTH_LONG).show();
            }
            catch (VersementNullException e)
            {
                Toast.makeText(Produits_Activity.this, "vous n'avez pas saisir le Montant du versement", Toast.LENGTH_LONG).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }


    public void generatelistContent()
    {
        dbsrc = new Datasrc(this);
        dbsrc.open();
        articles = dbsrc.findallarticles(this, ven_id, client_id);

        if (articles.size() == 0)
        {
            Toast.makeText(Produits_Activity.this, "Tout Livré !", Toast.LENGTH_LONG).show();
        }
        dbsrc.close();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}