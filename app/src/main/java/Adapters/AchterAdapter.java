package Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.yacinebenkaidali.dxstock.R;

import java.util.ArrayList;
import java.util.List;

import Donnes.Articletmp;

/**
 * Created by YacineBENKAIDALI on 8/5/2018.
 */
public class AchterAdapter extends ArrayAdapter<Articletmp>
{
    ViewHolder viewHolder;
    public ArrayList<Articletmp> articles=null;

    public AchterAdapter(Context context, int resource, List<Articletmp> objects) {
        super(context, resource, objects);
        articles=new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        viewHolder = new ViewHolder();
        if(convertView==null){
            LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_view_article_acheter,parent,false);

            viewHolder.designation = (TextView) convertView.findViewById(R.id.desifin);
            viewHolder.quantité = (TextView) convertView.findViewById(R.id.qte_disp_fin);
            viewHolder.prix = (TextView) convertView.findViewById(R.id.prixfin);

            convertView.setTag(viewHolder);

        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }
        Articletmp tmp= getItem(position);
        if (tmp!=null)
        {
            viewHolder.designation.setText(tmp.DESIGNATION);
            viewHolder.prix.setText(Double.toString(tmp.PRIX_VENTE));
            viewHolder.quantité.setText(Double.toString(tmp.QTE_LIVRE));
        }


        return convertView;
    }

    private class ViewHolder
    {
        TextView designation;
        TextView prix;
        TextView quantité;
    }
}