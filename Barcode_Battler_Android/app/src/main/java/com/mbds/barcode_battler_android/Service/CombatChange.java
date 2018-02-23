package com.mbds.barcode_battler_android.Service;

import android.text.Html;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.mbds.barcode_battler_android.MainActivity;

import org.w3c.dom.Text;

/**
 * Created by PGNem on 10/02/2018.
 */

public class CombatChange {

    public static final String COLOR_RED = "<font color='#EE0000'>";
    public EditText textLogs;
    public TextSwitcher pvCreature1;
    public TextSwitcher pvCreature2;

    public CombatChange(EditText et, TextSwitcher textCreature1, TextSwitcher textCreature2) {
        textLogs = et;
        pvCreature1 = textCreature1;
        pvCreature2 = textCreature2;

        // load an animation by using AnimationUtils class
        Animation in = AnimationUtils.loadAnimation(MainActivity.activity, android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(MainActivity.activity, android.R.anim.slide_out_right);

        pvCreature1.setInAnimation(in);
        pvCreature1.setOutAnimation(out);

        pvCreature2.setInAnimation(in);
        pvCreature2.setOutAnimation(out);

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

                    TextView textView = (TextView) pvCreature1.getCurrentView();

                    // Check si y'a eu des dégats
                    if (!textView.getText().equals(pv.toString())) {
                        String pvColor = COLOR_RED + pv.toString() + "</font>";
                        pvCreature1.setText(Html.fromHtml(pvColor));
                    }
                }
                // Cas pour créature 2
                else {

                    TextView textView = (TextView) pvCreature2.getCurrentView();

                    // Check si y'a eu des dégats
                    if (!textView.getText().equals(pv.toString())) {
                        String pvColor = COLOR_RED + pv.toString() + "</font>";
                        pvCreature2.setText(Html.fromHtml(pvColor));
                    }
                }
            }
        });
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
