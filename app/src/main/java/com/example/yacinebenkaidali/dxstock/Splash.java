package com.example.yacinebenkaidali.dxstock;

import android.content.Intent;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        LinearLayout ly=findViewById(R.id.lysplash);
        Animation logo= AnimationUtils.loadAnimation(this,R.anim.transition);

        ly.startAnimation(logo);
        final Intent in=new Intent(Splash.this,MainActivity.class);
        Thread timer=new Thread(){
                public void run()
                {
                    try {
                        sleep(3500);
                    }
                catch (InterruptedException e)
                {
                    e.getStackTrace();
                }
                finally {
                    startActivity(in);
                    overridePendingTransition(android.R.anim.fade_out,android.R.anim.fade_in);
                    finish();
                }
            }
        };
        timer.start();
    }
}
