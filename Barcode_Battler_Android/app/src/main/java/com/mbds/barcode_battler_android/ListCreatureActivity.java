package com.mbds.barcode_battler_android;

import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Joueur;

public class ListCreatureActivity extends AppCompatActivity implements ListAdapter {

    View returnView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_creature);

        ListView lv = (ListView) findViewById(R.id.listViewCreature);
        lv.setAdapter(this);
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return Joueur.getInstance().getListCreatures().size();
    }

    @Override
    public Object getItem(int position) {
        return Joueur.getInstance().getListCreatures().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        returnView = View.inflate(this, R.layout.layout_bouton_list_creature, null);
        Creature c = Joueur.getInstance().getListCreatures().get(position);

        TextView txNom = (TextView) returnView.findViewById(R.id.textCreatureNom);
        txNom.setText(c.getNomEtTitre());

        TextView txPV = (TextView) returnView.findViewById(R.id.textCreaturePV);
        txPV.setText(" PV : " + c.getPV());

        TextView txPA = (TextView) returnView.findViewById(R.id.textCreaturePA);
        txPA.setText(" PA : " + c.getPA());

        TextView txPB = (TextView) returnView.findViewById(R.id.textCreaturePB);
        txPB.setText(" PB : " + c.getPB());

        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), ((TextView) v.findViewById(R.id.textCreatureNom)).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return returnView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return Joueur.getInstance().getListCreatures().isEmpty();
    }
}
