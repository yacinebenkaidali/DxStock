package Adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yacinebenkaidali.dxstock.Bon_Activity;
import com.example.yacinebenkaidali.dxstock.Produits_Activity;
import com.example.yacinebenkaidali.dxstock.R;
import com.example.yacinebenkaidali.dxstock.Remboursement_Activity;

import java.util.ArrayList;
import java.util.List;

import BDD.Datasrc;
import Donnes.Client;
import com.example.yacinebenkaidali.dxstock.Reclamation;

/**
 * Created by YacineBENKAIDALI on 7/31/2018.
 */
public class ClientAdapter extends ArrayAdapter<Client>
{
    public ArrayList<Client> clients;
    String CODE_VAN="";
    Context context;


    public ClientAdapter(Context context, int resource, List<Client> objects,String CODE_VAN) {
        super(context, resource, objects);
        this.context=context;
        clients=new ArrayList<>(objects);
        this.CODE_VAN=CODE_VAN;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView==null){
            LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_view_client,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.Nomclient = (TextView) convertView.findViewById(R.id.Nom);
            viewHolder.Adr = (TextView) convertView.findViewById(R.id.adr);
            viewHolder.Tel = (TextView) convertView.findViewById(R.id.tel);
            viewHolder.options=(ImageButton)convertView.findViewById(R.id.option);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final Client client=clients.get(position);
        viewHolder.Nomclient.setText(client.NOM_CLIENT);
        viewHolder.Adr.setText(client.Adresse);
        viewHolder.Tel.setText(client.Tel);




        viewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChoixOperation(client);
            }
        });

        return convertView;

    }

    private class ViewHolder
    {
        TextView Nomclient;
        TextView Tel;
        TextView Adr;
        ImageButton options;
    }

    public void ChoixOperation(final Client c) {
    final Dialog choix = new Dialog(getContext());
    choix.requestWindowFeature(Window.FEATURE_NO_TITLE);
    choix.setContentView(R.layout.customdialog);

    Button reclamation = (Button) choix.findViewById(R.id.bt_reclamation);
    Button livre = (Button) choix.findViewById(R.id.bt_livraison);
    Button rembouresement=(Button)choix.findViewById(R.id.bt_retour);
    Button consultation=(Button)choix.findViewById(R.id.bt_consulte_bon);
    reclamation.setEnabled(true);
    livre.setEnabled(true);

    reclamation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent in = new Intent(getContext(), Reclamation.class);
            in.putExtra("tel client",c.Tel);
            in.putExtra("nom du client", c.NOM_CLIENT);
            in.putExtra("id client", c.Code_CLTV);
            getContext().startActivity(in);

            ((Activity) context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            choix.dismiss();

        }
    });
    livre.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent in = new Intent(getContext(), Produits_Activity.class);
            in.putExtra("id ven",CODE_VAN);
            in.putExtra("nom du client", c.NOM_CLIENT);
            in.putExtra("id client", c.Code_CLTV);
            choix.dismiss();
            getContext().startActivity(in);

            ((Activity) context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            choix.dismiss();
        }
    });


    rembouresement.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent in=new Intent(getContext(),Remboursement_Activity.class);
            in.putExtra("id ven",CODE_VAN);
            in.putExtra("nom du client", c.NOM_CLIENT);
            in.putExtra("id client", c.Code_CLTV);
            choix.dismiss();
            getContext().startActivity(in);

            ((Activity) context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            choix.dismiss();
        }
    });
    consultation.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent in = new Intent(getContext(), Bon_Activity.class);
            in.putExtra("id_client", c.Code_CLTV);
            in.putExtra("id_ven", CODE_VAN);
            in.putExtra("code_act","client");
            getContext().startActivity(in);
            ((Activity) context).overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            choix.dismiss();
        }
    });
    choix.show();
}
}

