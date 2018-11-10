package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yacinebenkaidali.dxstock.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Donnes.Article;
import Donnes.Articletmp;
import Donnes.Livraison_tete;

/**
 * Created by YacineBENKAIDALI on 9/2/2018.
 */
public class BonAdapter extends ArrayAdapter<Articletmp>
{
    List<Articletmp> bons=null;
    ViewHolder viewHolder;


    public BonAdapter(Context context, int resource, List<Articletmp> objects) {
        super(context, resource, objects);
        bons=new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder = new ViewHolder();
        if(convertView==null){
            LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_view_bon,parent,false);

            viewHolder.nom = (TextView) convertView.findViewById(R.id.nom_client_fin);
            viewHolder.code_bon = (TextView) convertView.findViewById(R.id.code_bon_fin);
            viewHolder.montant = (TextView) convertView.findViewById(R.id.motant_fin);
            viewHolder.date_liv = (TextView) convertView.findViewById(R.id.date_liv_fin);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Articletmp article=bons.get(position);
        viewHolder.nom.setText(article.nom_client);
        viewHolder.montant.setText(Double.toString(article.Montant));
        viewHolder.code_bon.setText(Double.toString(article.Code_Bon));
        viewHolder.date_liv.setText(df.format( article.DATE_LIVRAISON));

        return convertView;
    }

    private class ViewHolder
    {
        TextView nom;
        TextView code_bon;
        TextView montant;
        TextView date_liv;
    }
}