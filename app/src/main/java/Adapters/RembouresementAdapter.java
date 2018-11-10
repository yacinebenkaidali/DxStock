package Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yacinebenkaidali.dxstock.R;

import java.util.ArrayList;
import java.util.List;

import Donnes.Articletmp;
import Donnes.Livraison_detail;
import Exceptions.QuantitéSuperieurException;

/**
 * Created by YacineBENKAIDALI on 8/15/2018.
 */
public class RembouresementAdapter extends ArrayAdapter<Articletmp>{

    public  static List<Livraison_detail> MAJdb=new ArrayList<Livraison_detail>();;
    ViewHolder viewHolder;
    List<Articletmp> articles=null;
    int id_client;
    String []qtes;

    public RembouresementAdapter(Context context, int resource, List<Articletmp> objects, int id_client)
    {
        super(context, resource, objects);
        articles=new ArrayList<>(objects);
        this.id_client=id_client;
        qtes=new String[articles.size()];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder= new ViewHolder();

        if(convertView==null){
            LayoutInflater infalter=LayoutInflater.from(getContext());
            convertView=infalter.inflate(R.layout.list_view_article,parent,false);


            viewHolder.designation = (TextView) convertView.findViewById(R.id.desi);
            viewHolder.quantité = (TextView) convertView.findViewById(R.id.qte_disp_2);
            viewHolder.prix = (TextView) convertView.findViewById(R.id.prix);
            viewHolder.qte=(EditText) convertView.findViewById(R.id.qte);
            viewHolder.cb=(CheckBox)convertView.findViewById(R.id.cbarticle);
            viewHolder.hide=(TextView)convertView.findViewById(R.id.hide);


            viewHolder.cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckBox check=(CheckBox)view;
                    check.requestFocus();
                    if (check.isChecked())
                    {
                        Articletmp a =articles.get((int)check.getTag());
                        Livraison_detail l=new Livraison_detail();
                        l.CODE_ARTICLE=a.CODE_ARTICLE;
                        l.PRIX_ACHAT=a.PRIX_VENTE;
                        l.CODE_BON=a.Code_Bon;
                        try
                        {
                            if(qtes[(int)check.getTag()].equals(""))
                            {
                                throw new Exception();
                            }else {
                                if (Double.parseDouble(qtes[(int)check.getTag()])>a.QTE_LIVRE) throw  new QuantitéSuperieurException();
                                else{
                                    l.QTE_TMP= Double.valueOf(a.QTE_LIVRE);//QTE_TMP pour sauvegarder l'ancienne quantité
                                    l.QTE_LIVRE=Double.parseDouble(qtes[(int)check.getTag()]);
                                    MAJdb.add(l);
                                }
                            }
                        }
                        catch (QuantitéSuperieurException e)
                        {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Quantité Superieur")
                                    .setMessage("la quantité saisie est supérieure que celle achetée "+a.QTE_LIVRE)
                                    .setCancelable(true)
                                    .setIcon(R.drawable.alert)
                                    .setNeutralButton("Compris",null )
                                    .show();
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getContext(), "Il faut toucher le fin bouton du clavier puis re-cochez", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Articletmp a2=articles.get((int)check.getTag());

                        for (Livraison_detail l:MAJdb) {
                            if (l.CODE_ARTICLE.equals(a2.CODE_ARTICLE))
                            {
                                MAJdb.remove(l);
                            }
                        }
                    }
                }
            });


            convertView.setTag(viewHolder);
            convertView.setTag(R.id.cbarticle, viewHolder.cb);
            convertView.setTag(R.id.qte, viewHolder.qte);
        }
        else {
            viewHolder =(ViewHolder)convertView.getTag();
        }
        viewHolder.qte.setTag(position);
        viewHolder.cb.setTag(position);



        viewHolder.qte.setId(position);
        viewHolder.qte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = view.getId();
                final EditText Caption = (EditText) view;
                qtes[position]=Caption.getText().toString();
                if(Caption.isFocused()) {Caption.clearFocus();}
            }
        });


        Articletmp article=articles.get(position);
        viewHolder.designation.setText(article.DESIGNATION);
        viewHolder.prix.setText(Double.toString(article.PRIX_VENTE));
        viewHolder.quantité.setText(Long.toString(article.Code_Bon));
        viewHolder.qte.setHint(Integer.toString(article.QTE_LIVRE));
        viewHolder.hide.setText("Code du Bon :");

        return convertView;
    }


    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public int getViewTypeCount(){
        return getCount();
    }



    private class ViewHolder
    {
        TextView designation;
        TextView quantité;
        TextView prix;
        EditText qte;
        CheckBox cb;
        TextView hide;
    }


}