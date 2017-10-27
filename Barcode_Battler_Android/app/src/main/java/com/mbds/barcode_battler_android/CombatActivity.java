package com.mbds.barcode_battler_android;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Service.Serializable_Service;

import java.io.IOException;


public class CombatActivity extends AppCompatActivity implements NfcAdapter.CreateNdefMessageCallback {
    NfcAdapter mNfcAdapter;
    //TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.affichage_combat);

        //textView = (TextView) findViewById(R.id.textcombat);

        // Check for available NFC Adapter
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        // Register callback
        mNfcAdapter.setNdefPushMessageCallback(this, this);

    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {

        // com.mbds.barcode_battler_android

        Creature c = new Creature("aNom", "aTitre", "aRace", 10, 10, 10, "aCode");
        String text = ("");
        /*NdefMessage msg = new NdefMessage(
                new NdefRecord[] { NdefRecord.createMime(
                        "application/vnd.com.example.android.beam", text.getBytes())

                        //,NdefRecord.createApplicationRecord("com.example.android.beam")
                });
        */

        NdefMessage msg = null;

        try {
            msg = new NdefMessage( new NdefRecord[] {
                    NdefRecord.createExternal("com.mbds.barcode_battler_android", "externalType", Serializable_Service.serialize(c))
            });
        } catch (IOException e) {
            System.err.println("ERREUR dans la serialisation de la créature : " + c);
            e.printStackTrace();
        }

        return msg;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Check to see that the Activity started due to an Android Beam
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            processIntent(getIntent());
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        // onResume gets called after this to handle the intent
        setIntent(intent);
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

        Creature c = null;
        try {
            c = (Creature) Serializable_Service.deserialize(msg.getRecords()[0].getPayload()) ;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Log.v("NFCCCCCCCCCCCCCCCCCCCCC", c.toString());
    }
}