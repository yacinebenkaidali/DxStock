package Adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.SparseArray;
import android.view.KeyEvent;
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

import Donnes.Article;
import Donnes.Livraison_detail;
import Exceptions.QuantitéInsuffisanteException;

/**
 * Created by YacineBENKAIDALI on 8/1/2018.
 */
public class ArticleAdapter extends ArrayAdapter<Article>
{
    public static List<Livraison_detail> MAJdb = new ArrayList<Livraison_detail>();
    List<Article> articles = null;
    String[] qtes;

    public ArticleAdapter(Context context, int resource, List<Article> objects)
    {
        super(context, resource, objects);
        articles=new ArrayList<>(objects);
        qtes=new String[articles.size()];
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater infalter = LayoutInflater.from(getContext());
            convertView = infalter.inflate(R.layout.list_view_article, parent, false);


            viewHolder.designation = (TextView) convertView.findViewById(R.id.desi);
            viewHolder.quantité = (TextView) convertView.findViewById(R.id.qte_disp_2);
            viewHolder.prix = (TextView) convertView.findViewById(R.id.prix);
            viewHolder.qte = (EditText) convertView.findViewById(R.id.qte);
            viewHolder.cb = (CheckBox) convertView.findViewById(R.id.cbarticle);


            viewHolder.cb.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            CheckBox check = (CheckBox) view;
                            check.requestFocus();
                            if (check.isChecked()) {
                                Article a = articles.get(position);
                                Livraison_detail l = new Livraison_detail();
                                l.CODE_ARTICLE = a.CODE_ARTICLE;
                                l.PRIX_ACHAT = a.PRIX_VENTE;
                                try {
                                    if (qtes[position].equals("")) {
                                        throw new Exception();
                                    } else {
                                        if (Double.parseDouble(qtes[position]) > a.QTE_CHARGE) {
                                            throw new QuantitéInsuffisanteException();
                                        } else {
                                            l.QTE_LIVRE = Double.parseDouble(qtes[position]);
                                            MAJdb.add(l);
                                        }
                                    }
                                } catch (QuantitéInsuffisanteException e) {
                                    new AlertDialog.Builder(getContext())
                                            .setTitle("Quantité Insuffisante")
                                            .setMessage("la quantité saisie est superieur que celle du Stock il y a " + a.QTE_CHARGE + " artcile Disp")
                                            .setCancelable(true)
                                            .setIcon(R.drawable.alert)
                                            .setNeutralButton("Compris", null)
                                            .show();
                                } catch (Exception e) {
                                    Toast.makeText(getContext(), "Il faut toucher le fin bouton du clavier puis re-cochez", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Article a2 = articles.get(position);

                                for (Livraison_detail l : MAJdb) {
                                    if (l.CODE_ARTICLE.equals(a2.CODE_ARTICLE)) {
                                        MAJdb.remove(l);
                                    }
                                }
                            }
                        }
                    });

            viewHolder.qte.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final int position = view.getId();
                            final EditText Caption = (EditText) view;
                            qtes[position] = Caption.getText().toString();
                            if (Caption.isFocused()) {
                                Caption.clearFocus();
                            }
                        }
                    });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.cbarticle, viewHolder.cb);
            convertView.setTag(R.id.qte, viewHolder.qte);
        } else {
            viewHolder =(ViewHolder)convertView.getTag();
        }

        viewHolder.qte.setTag(position);
        viewHolder.cb.setTag(position);
        viewHolder.qte.setId(position);


        Article article = articles.get(position);
        if (article != null) {
            viewHolder.designation.setText(article.DESIGNATION);
            viewHolder.prix.setText(Double.toString(article.PRIX_VENTE));
            viewHolder.quantité.setText(Integer.toString(article.QTE_CHARGE));
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    private class ViewHolder {
        TextView designation;
        TextView quantité;
        TextView prix;
        EditText qte;
        CheckBox cb;
    }


}