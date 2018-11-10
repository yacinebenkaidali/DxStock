package com.example.yacinebenkaidali.dxstock;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import BDD.Datasrc;

public class Reclamation extends AppCompatActivity {
    Datasrc datasrc;

    EditText sujet;
    EditText email_send;
    EditText reclamation;
    Button submit;
    TextView nom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);

        sujet=(EditText) findViewById(R.id.rec_sujet);
        reclamation=(EditText)findViewById(R.id.rec_reclamation);
        email_send=(EditText)findViewById(R.id.rec_email);
        submit=(Button)findViewById(R.id.submit);

        nom=(TextView)findViewById(R.id.rec_nom);

        nom.setText(getIntent().getStringExtra("nom du client"));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoixOpérationReclamation();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }

    public void ChoixOpérationReclamation() {
        final Dialog choix = new Dialog(this);
        choix.requestWindowFeature(Window.FEATURE_NO_TITLE);
        choix.setContentView(R.layout.email_db);

        final Button email = (Button) choix.findViewById(R.id.bt_email);
        Button database = (Button) choix.findViewById(R.id.bt_database);

        email.setEnabled(true);
        database.setEnabled(true);

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent .setType("vnd.android.cursor.dir/email");
                if(!email_send.getText().toString().equals(""))
                {
                    String email = email_send.getText().toString();
                    Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
                    Matcher mat = pattern.matcher(email);

                    if(mat.matches()){
                        String to[] = {email_send.getText().toString()};
                        emailIntent .putExtra(Intent.EXTRA_EMAIL, to);
                        emailIntent .putExtra(Intent.EXTRA_SUBJECT, sujet.getText().toString()+"de "+nom.getText().toString());
                        emailIntent .putExtra(Intent.EXTRA_TEXT, reclamation.getText().toString());
                        startActivity(Intent.createChooser(emailIntent , "Send email..."));
                        reclamation.setText("");sujet.setText("");
                    }else{

                        Toast.makeText(Reclamation.this, "STP Entrer une adresse valide", Toast.LENGTH_SHORT).show();
                    }



                }else {
                    Toast.makeText(Reclamation.this, "STP Entrer l'email du destinataire", Toast.LENGTH_SHORT).show();
                }
                choix.dismiss();
            }
        });

        database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reclamation.clearFocus();
                submit.requestFocus();
                String rec=reclamation.getText().toString();
                datasrc =new Datasrc(Reclamation.this);
                datasrc.open();
                datasrc.createreclamation(rec,sujet.getText().toString(),getIntent().getIntExtra("id client",0));
                datasrc.close();
                reclamation.setText("");sujet.setText("");
                choix.dismiss();
                sujet.setText("");reclamation.setText("");email_send.setText("");
                Toast.makeText(Reclamation.this, "Votre Réclamation est pris en charge", Toast.LENGTH_SHORT).show();
            }
        });
        choix.show();
    }
}
