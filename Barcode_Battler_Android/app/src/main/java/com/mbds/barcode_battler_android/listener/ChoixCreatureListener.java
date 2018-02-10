package com.mbds.barcode_battler_android.listener;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.fragment.CombatFragment;
import com.mbds.barcode_battler_android.fragment.CreatureScanFragment;
import com.mbds.barcode_battler_android.fragment.CreaturesFragment;

/**
 * Created by Karl on 27/10/2017.
 */

public class ChoixCreatureListener implements View.OnClickListener {

    private Creature creature;
    private boolean isChoixCreature;

    public ChoixCreatureListener(Creature creature, boolean isChoixCreature) {
        this.creature = creature;
        this.isChoixCreature = isChoixCreature;
    }

    @Override
    public void onClick(View v) {
        if (isChoixCreature == true) {
            Toast.makeText(v.getContext(), "Je choisis : " + ((TextView) v.findViewById(R.id.textCreatureNom)).getText(), Toast.LENGTH_SHORT).show();
            switch (CombatFragment.choix_creature) {
                case CREATURE_1:
                    CombatFragment.setCreature1(this.creature);

                    MainActivity.activity.onBackPressed();
                    break;

                case CREATURE_2:
                    CombatFragment.setCreature2(this.creature);

                    MainActivity.activity.onBackPressed();
                    break;
            }

        } else {
            CreatureScanFragment creaturesFragment = new CreatureScanFragment();
            creaturesFragment.setCreature(creature);
            creaturesFragment.setReadOnly(true);
            ((MainActivity) MainActivity.activity).lancerFragment(creaturesFragment, false);
        }
    }
}
