package com.mbds.barcode_battler_android.fragment;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.MainActivity;
import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.R;
import com.mbds.barcode_battler_android.Service.Combat_Log_Thread;
import com.mbds.barcode_battler_android.Service.Serializable_Service;
import com.mbds.barcode_battler_android.Service.TagLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Karl on 27/10/2017.
 */

public class CombatFragment extends Fragment implements NfcAdapter.CreateNdefMessageCallback {

    private static Creature creature1;
    private static Creature creature2;

    private NfcAdapter mNfcAdapter;


    public static CHOIX_CREATURE choix_creature;


    public enum CHOIX_CREATURE {
        CREATURE_1, CREATURE_2;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.affichage_combat, container, false);

        view.findViewById(R.id.image_creature_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TagLog.CHOOSE_CREATURE, "Choix créature 1");

                choix_creature = CHOIX_CREATURE.CREATURE_1;

                CreaturesFragment creaturesFragment = new CreaturesFragment();
                creaturesFragment.setChoixCreature(true);
                ((MainActivity) getActivity()).lancerFragment(creaturesFragment, false);

            }
        });

        view.findViewById(R.id.image_creature_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TagLog.CHOOSE_CREATURE, "Choix créature 2");

                choix_creature = CHOIX_CREATURE.CREATURE_2;

                CreaturesFragment creaturesFragment = new CreaturesFragment();
                creaturesFragment.setChoixCreature(true);
                ((MainActivity) getActivity()).lancerFragment(creaturesFragment, false);
            }
        });

        view.findViewById(R.id.lancer_nfc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check for available NFC Adapter
                mNfcAdapter = NfcAdapter.getDefaultAdapter(MainActivity.activity);
                if (mNfcAdapter == null) {
                    Toast.makeText(MainActivity.activity, "NFC is not available", Toast.LENGTH_LONG).show();
                    MainActivity.activity.finish();
                    return;
                }
                // Register callback
                mNfcAdapter.setNdefPushMessageCallback(CombatFragment.this, MainActivity.activity);
            }
        });


        view.findViewById(R.id.image_combat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creature1 != null && creature2 != null) {

                    Creature cUNavecEquip = new Creature(creature1);
                    Creature cDEUXavecEquip = new Creature(creature2);

                    /*
                        ArrayList<Equipement> ae = new ArrayList<Equipement>( );
                        ae. add(new Equipement("TEST", 20, 20, 20 , "koko"));
                        cUNavecEquip.setListEquipement(ae);
                    */

                    for (Equipement e : cUNavecEquip.getListEquipement()) {
                        cUNavecEquip.setPV(cUNavecEquip.getPV() + e.getBonusPV());
                        cUNavecEquip.setPA(cUNavecEquip.getPA() + e.getBonusPA());
                        cUNavecEquip.setPB(cUNavecEquip.getPB() + e.getBonusPB());
                    }

                    for (Equipement e : cDEUXavecEquip.getListEquipement()) {
                        cDEUXavecEquip.setPV(cDEUXavecEquip.getPV() + e.getBonusPV());
                        cDEUXavecEquip.setPA(cDEUXavecEquip.getPA() + e.getBonusPA());
                        cDEUXavecEquip.setPB(cDEUXavecEquip.getPB() + e.getBonusPB());
                    }

                    setCreature1(cUNavecEquip);
                    setCreature2(cDEUXavecEquip);

                    commencerLaBagarre();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        creature1 = null;
        creature2 = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (creature1 != null) {
            ImageView imageRace = (ImageView) getView().findViewById(R.id.image_creature_1);
            imageRace.setImageResource(getContext().getResources().getIdentifier(creature1.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                    MainActivity.getAppContext().getPackageName()));

            TextView txNom = (TextView) getView().findViewById(R.id.nom_creature_1);
            txNom.setText(creature1.getNom());

            TextView txPV = (TextView) getView().findViewById(R.id.pv_creature_1);
            txPV.setText("" + creature1.getPV());

        }

        if (creature2 != null) {
            ImageView imageRace = (ImageView) getView().findViewById(R.id.image_creature_2);
            imageRace.setImageResource(getContext().getResources().getIdentifier(creature2.getRace().toLowerCase().replaceAll("é", "e"), "drawable",
                    MainActivity.getAppContext().getPackageName()));

            TextView txNom = (TextView) getView().findViewById(R.id.nom_creature_2);
            txNom.setText(creature2.getNom());

            TextView txPV = (TextView) getView().findViewById(R.id.pv_creature_2);
            txPV.setText("" + creature2.getPV());
        }

        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getActivity().getIntent().getAction())) {
            processIntent(getActivity().getIntent());
        }
    }

    private void commencerLaBagarre() {
        // Chiffre random entre 0 et 1 pour déterminer qui commence le combat
        Random rn = new Random();
        int randomNum = rn.nextInt(2);

        Log.i(TagLog.COMBAT, "/////////////////////////");
        Log.i(TagLog.COMBAT, "Début du combat !");


        Log.i(TagLog.COMBAT, (randomNum == 0) ? "C1 commence" : "C2 commence");

        int tour = 1;

        Combat_Log_Thread clt = new Combat_Log_Thread(((EditText) getView().findViewById(R.id.logs_combat)));
        clt.start();


        // Tant qu'aucun est mort on attaque
        while (creature1.getPV() > 0 && creature2.getPV() > 0) {

            Log.i(TagLog.COMBAT, "////////// TOUR " + tour);

            // Si on a 0 c'est c1 qui commence
            if (randomNum == 0) {

                Log.i(TagLog.COMBAT, "C1 ATTAQUE");
                clt.addCombatMsg("\n|" + tour + "| " + creature1.getNom() + " attaque : ");

                creature1.attaque(creature2, clt);

                // Maj PV
                ((TextView) getView().findViewById(R.id.pv_creature_2)).setText(creature2.getPV() + "");

                // Si c2 n'est pas mort il attaque
                if (creature2.getPV() > 0) {
                    Log.i(TagLog.COMBAT, "C2 ATTAQUE");
                    clt.addCombatMsg("\n|" + tour + "| " + creature2.getNom() + " attaque : ");

                    creature2.attaque(creature1, clt);

                    // Maj PV
                    ((TextView) getView().findViewById(R.id.pv_creature_1)).setText(creature1.getPV() + "");

                }

            } else {    // Si on a 1 c'est c2 qui commence
                Log.i(TagLog.COMBAT, "C2 ATTAQUE");
                clt.addCombatMsg("\n|" + tour + "| " + creature2.getNom() + " attaque : ");

                creature2.attaque(creature1, clt);

                // Maj PV
                ((TextView) getView().findViewById(R.id.pv_creature_1)).setText(creature1.getPV() + "");

                // Si c1 n'est pas mort il attaque
                if (creature1.getPV() > 0) {
                    Log.i(TagLog.COMBAT, "C1 ATTAQUE");
                    clt.addCombatMsg("\n|" + tour + "| " + creature1.getNom() + " attaque : ");

                    creature1.attaque(creature2, clt);

                    // Maj PV
                    ((TextView) getView().findViewById(R.id.pv_creature_2)).setText(creature2.getPV() + "");


                }
            }
            tour++;
        }

        Log.i(TagLog.COMBAT, "Le gagnant est : " + ((creature1.getPV() <= 0) ? creature2.getNom() : creature1.getNom()));
        clt.addCombatMsg("\n\nLe gagnant est : " + ((creature1.getPV() <= 0) ? creature2.getNom() : creature1.getNom()));

        Joueur.getInstance().resetListCreatures();

        // return (creature1.getPV() <= 0) ? creature2 : creature1;
    }

    public static void setCreature1(Creature creature1) {
        CombatFragment.creature1 = creature1;

        Log.i(TagLog.CHOOSE_CREATURE, creature1.toString());

    }

    public static void setCreature2(Creature creature2) {
        CombatFragment.creature2 = creature2;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        // com.mbds.barcode_battler_android

        Creature c = null;

        if(creature1 != null){
            c = creature1;
        }
        else if(creature2 != null){
            c = creature2;
        } else {
            return null;
        }

        NdefMessage msg = null;
        try {
            msg = new NdefMessage(
                    new NdefRecord[] { NdefRecord.createMime(
                            "application/vnd.com.example.android.beam", Serializable_Service.serialize(c))

                            //,NdefRecord.createApplicationRecord("com.example.android.beam")
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }


        Log.i("NFC", "Creature : " + c);
        Log.i("NFC", "msg : " + msg.getRecords());

/*
        NdefMessage msg = null;

        try {
            msg = new NdefMessage( new NdefRecord[] {
                    NdefRecord.createExternal("application/vnd.com.example.android.beam", "externalType", Serializable_Service.serialize(c))
            });
        } catch (IOException e) {
            System.err.println("ERREUR dans la serialisation de la créature : " + c);
            e.printStackTrace();
        }*/

        return msg;
    }


    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    void processIntent(Intent intent) {
        //textView = (TextView) findViewById(R.id.textcombat);
        Parcelable[] rawMsgs = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        // only one message sent during the beam
        NdefMessage msg = (NdefMessage) rawMsgs[0];
        // record 0 contains the MIME type, record 1 is the AAR, if present
        //textView.setText("J'AVAIS RAISON HEHE");

        Log.v("NFC", "payload : " + msg.getRecords()[0].getPayload());

        Creature c = null;
        try {
            c = (Creature) Serializable_Service.deserialize(msg.getRecords()[0].getPayload()) ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.v("NFCCCCCCCCCCCCCCCCCCCCC", c.toString());

        // A tester
        setCreature2(c);
    }
}
