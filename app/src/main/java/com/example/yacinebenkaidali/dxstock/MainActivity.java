package com.example.yacinebenkaidali.dxstock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    EditText username;
    EditText pwd;
    TextView indice;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = this.getSharedPreferences("com.example.yacinebenkaidali.dxstock", Context.MODE_PRIVATE);
        boolean Login = sharedPreferences.getBoolean("Login",false);
        // if login is true we load the profile activity else the login activity
        if (Login) {
            Intent i = new Intent(getApplicationContext(), Consult_Client.class);
            startActivity(i);
            finish();
        }
        else {
            setContentView(R.layout.activity_login);
            username= (EditText)findViewById(R.id.usernameedit);
            pwd=(EditText)findViewById(R.id.pwdedit);
            indice=(TextView)findViewById(R.id.indicateText);
        }
    }

    public void administration(final View v){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mode Administration");
        builder.setMessage("Entrer le Mot de Passe");
       // Set up the input
        final EditText input = new EditText(this);
       // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

       // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               String m_Text = input.getText().toString();
               String pwd= sharedPreferences.getString("Password","root");
               if (m_Text.equals(pwd)){
                   Intent i = new Intent(getApplicationContext(), Admin.class);
                   startActivity(i);
               }
                else{
                   String message = "Le Mot de passe est incorrecte." + "\n" + "Réessayer s'il vous plait !";
                   AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                   builder.setTitle("Error");
                   builder.setMessage(message);
                   builder.setPositiveButton("Réessayer", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int id) {
                           administration(v);
                       }
                   });
                   builder.setNegativeButton("Quiter", null);
                   builder.create().show();
               }
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

    public void Login(View view){
            String user = username.getText().toString();
            String passowrd= pwd.getText().toString();
            // if there is nothing in one of the inputs
            if(user.equals("") || passowrd.equals("")){
                indice.setText("Entrer le nom d'utilisateur et le mot de passe ");
                indice.setVisibility(View.VISIBLE);
             }
            else { // check the account
            try {
            SQLiteDatabase db = null;
            db = this.openOrCreateDatabase("DxBDD", MODE_PRIVATE, null);
                Cursor c = db.rawQuery("SELECT CODE_VAN FROM VENDEUR WHERE NOM=? and PASSWORD=?", new String[]{user,passowrd});
                if (c.getCount() > 0) {  // right login, move to te profile activity
                    int code_van = c.getColumnIndex("CODE_VAN");
                    c.moveToFirst();
                    String codeVan = c.getString(code_van);
                    sharedPreferences.edit().putString("UserName", user).apply();
                    sharedPreferences.edit().putString("UserCode", codeVan).apply();
                    sharedPreferences.edit().putBoolean("Login", true).apply();
                    username.setText(""); pwd.setText(""); // for the next login
                    Intent i = new Intent(getApplicationContext(), Consult_Client.class);
                    startActivity(i);
                    finish();
                }else {
                    indice.setText(" Le nom d'utilisateur ou le mot de passe est incorrecte");
                    indice.setVisibility(View.VISIBLE);
                    username.setText("");
                    pwd.setText("");
                }
            }catch (Exception e){
                Toast.makeText(getApplicationContext(),"Le Compte n'existe pas",Toast.LENGTH_LONG).show();
            }
            }
    }

}
