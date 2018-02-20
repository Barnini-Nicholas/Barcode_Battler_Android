package com.mbds.barcode_battler_android.Service;

import android.widget.EditText;
import android.widget.TextView;

import com.mbds.barcode_battler_android.MainActivity;

import org.w3c.dom.Text;

/**
 * Created by PGNem on 10/02/2018.
 */

public class CombatChange {

    public EditText textLogs;
    public TextView pvCreature1;
    public TextView pvCreature2;

    public CombatChange(EditText et, TextView textCreature1, TextView textCreature2) {
        textLogs = et;
        pvCreature1 = textCreature1;
        pvCreature2 = textCreature2;

        MainActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textLogs.setText("");
            }
        });
    }
    public void addCombatMsg(final String msg) {
        MainActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textLogs.setText(textLogs.getText() + msg);
                textLogs.setSelection(textLogs.getText().length());

            }
        });
    }

    public void changePvCreature(final int numCreature, final Integer pv) {
        MainActivity.activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Cas pour créature 1
                if (numCreature == 1) {
                    pvCreature1.setText(pv.toString());
                }
                // Cas pour créature 2
                else {
                    pvCreature2.setText(pv.toString());
                }
            }
        });

    }

}
