package com.example.yacinebenkaidali.dxstock;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Consultation_Activity extends AppCompatActivity {
    int tmp1;String tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_info);
         tmp = getIntent().getStringExtra("nom du client");
        TextView nom = (TextView) findViewById(R.id.name);
        nom.setText(tmp);
        tmp1 = getIntent().getIntExtra("sold ant", 0);
        TextView sold_ant_val = (TextView) findViewById(R.id.sold_ant_val);
        sold_ant_val.setText(Integer.toString(tmp1));
        tmp1 = getIntent().getIntExtra("sold ini", 0);
        TextView sold_ini_val = (TextView) findViewById(R.id.sold_ini_val);
        sold_ini_val.setText(Integer.toString(tmp1));
        tmp = getIntent().getStringExtra("adr");
        TextView adr = (TextView) findViewById(R.id.adr);
        adr.setText(tmp);
        tmp = getIntent().getStringExtra("tel");
        TextView tel = (TextView) findViewById(R.id.tel);
        tel.setText(tmp);
        tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + getIntent().getStringExtra("tel")));
                startActivity(intent);
        }});
        tmp=getIntent().getStringExtra("Date_cr");
        TextView date_cre=(TextView)findViewById(R.id.date_cration);date_cre.setText(tmp);
        TextView ver=(TextView)findViewById(R.id.ver);ver.setText(Integer.toString(getIntent().getIntExtra("ver",0)));
        TextView n_rout=(TextView)findViewById(R.id.n_route);n_rout.setText(getIntent().getStringExtra("nrout"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
}
