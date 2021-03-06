package com.mbds.barcode_battler_android.adapter;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.fragment.CreatureScanFragment;
import com.mbds.barcode_battler_android.fragment.EquipementScanFragment;

/**
 * Created by Karl on 26/10/2017.
 */

public class ListViewEquipementsAdapter implements ListAdapter {

    private LayoutInflater mInflater;
    private Equipement equipement;

    public ListViewEquipementsAdapter(Context equipementsFragment) {
        mInflater = LayoutInflater.from(equipementsFragment);
    }

    @Override
    public int getCount() {
        return Joueur.getInstance().getListEquipement().size();
    }

    @Override
    public Object getItem(int position) {
        return Joueur.getInstance().getListCreatures().get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView = mInflater.inflate(R.layout.item_list_equipements, null);
        equipement = Joueur.getInstance().getListEquipement().get(position);

        TextView txNom = (TextView) returnView.findViewById(R.id.nom_equipement);
        txNom.setText(equipement.getNom());

        LinearLayout layoutRarete = (LinearLayout) returnView.findViewById(R.id.rarity);
        for (int i = 0; i < equipement.getRarete(); i++) {

            View to_add = mInflater.inflate(R.layout.rarety_star,
                    layoutRarete, false);

            layoutRarete.addView(to_add);
        }

        TextView txPV = (TextView) returnView.findViewById(R.id.pv_equipement);
        txPV.setText("" + equipement.getBonusPV());

        TextView txPA = (TextView) returnView.findViewById(R.id.pa_equipement);
        txPA.setText("" + equipement.getBonusPA());

        TextView txPB = (TextView) returnView.findViewById(R.id.pb_equipement);
        txPB.setText("" + equipement.getBonusPB());

        returnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EquipementScanFragment equipementFragment = new EquipementScanFragment();
                equipementFragment.setEquipement(equipement);
                equipementFragment.setReadOnly(true);
                ((MainActivity) MainActivity.activity).lancerFragment(equipementFragment, false);
            }
        });

        return returnView;
    }

    @Override
    public boolean isEmpty() {
        return Joueur.getInstance().getListCreatures().isEmpty();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

}
