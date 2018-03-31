package com.mbds.barcode_battler_android;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mbds.barcode_battler_android.Modele.Creature;
import com.mbds.barcode_battler_android.Modele.Equipement;
import com.mbds.barcode_battler_android.Modele.Joueur;
import com.mbds.barcode_battler_android.Modele.TypeButin;
import com.mbds.barcode_battler_android.Service.HashService;
import com.mbds.barcode_battler_android.Service.TagLog;
import com.mbds.barcode_battler_android.Service.TraitementHash;
import com.mbds.barcode_battler_android.fragment.AboutFragment;
import com.mbds.barcode_battler_android.fragment.CombatFragment;
import com.mbds.barcode_battler_android.fragment.CreatureScanFragment;
import com.mbds.barcode_battler_android.fragment.CreaturesFragment;
import com.mbds.barcode_battler_android.fragment.EquipementScanFragment;
import com.mbds.barcode_battler_android.fragment.EquipementsFragment;
import com.mbds.barcode_battler_android.fragment.ScannerFragment;

public class MainActivity extends AppCompatActivity implements ScannerFragment.OnScanEffectue {


    private static Context context;
    public static Activity activity;
    public static final String TAG = "NfcDemo";
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On récupére les permissions nécessaires de l'appli
        int droitCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int droitWriteExtStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int droitNfc = ContextCompat.checkSelfPermission(this, Manifest.permission.NFC);

        if (droitCamera != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION : ", "CAMERA REFUSER");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    0);
        }
        if (droitWriteExtStorage != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION : ", "WRITE_EXTERNAL_STORAGE REFUSER");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);
        }
        if (droitNfc != PackageManager.PERMISSION_GRANTED) {
            Log.i("PERMISSION : ", "NFC REFUSER");
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.NFC},
                    0);
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC NOT supported on this devices!",
                    Toast.LENGTH_LONG).show();
            finish();
        }

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MainActivity.context = getApplicationContext();
        MainActivity.activity = this;

        lancerFragment(AboutFragment.class, true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_button_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                lancerFragment(AboutFragment.class, true);
                return true;

            case R.id.action_scanner:
                lancerFragment(ScannerFragment.class, true);
                return true;

            case R.id.action_ouvrir_creatures:
                lancerFragment(CreaturesFragment.class, true);
                // lancerFragmentCreatures();
                return true;

            case R.id.action_ouvrir_equipements:
                lancerFragment(EquipementsFragment.class, true);
                return true;

            case R.id.action_lancer_combat:
                lancerFragment(CombatFragment.class, true);
                // Intent intent = new Intent(this, CombatActivity.class);
                // startActivity(intent);

                Toast.makeText(getApplicationContext(), "Cliquez sur les deux épées pour commencer le combat !", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void lancerFragment(Class fragmentClass, boolean disallowAddToBackStack) {
        try {
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            lancerFragment(fragment, disallowAddToBackStack);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void lancerFragment(Fragment fragment, boolean disallowAddToBackStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, fragment);

        if (disallowAddToBackStack) {
            transaction.disallowAddToBackStack();
        } else {
            transaction.addToBackStack(null);
        }

        // Commit the transaction
        transaction.commit();
    }

    public static Context getAppContext() {
        return MainActivity.context;
    }

    @Override
    public void scanEffectue(String codeBarre) {
        // On hash le barcode en SHA1
        String hash = HashService.hash(codeBarre);

        // Récupération du type de butin
        TypeButin typeButin = TraitementHash.getTypeOfHash(hash);

        switch (typeButin) {

            case CREATURE:
                lancerFragmentCreatureScan(hash);
                break;

            case EQUIPEMENT:
                lancerFragmentEquipementScan(hash);
                break;
        }
    }

    private void lancerFragmentCreatureScan(String hash) {

        // On récupère la créature générée
        Creature creature = TraitementHash.getCreature(hash);
        Log.i(TagLog.SHOW_CREATURE, creature.toString());

        // Controle pour voir si la créature a déja été scannée :
        for (Creature c : Joueur.getInstance().getListCreatures()) {
            if (creature.getCodeBarreUtilise().equals(c.getCodeBarreUtilise())) {
                Toast.makeText(getApplicationContext(), "Déja scanné tricheur", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Création du fragment qui affiche la créature
        CreatureScanFragment creatureScanFragment = new CreatureScanFragment();
        creatureScanFragment.setCreature(creature);

        lancerFragment(creatureScanFragment, true);
    }

    private void lancerFragmentEquipementScan(String hash) {

        // On récupère l'équipement généré
        Equipement equipement = TraitementHash.getEquipement(hash);
        Log.i(TagLog.SHOW_CREATURE, equipement.toString());

        // Controle pour voir si l'équipement a déja été scannée :
        for (Equipement e : Joueur.getInstance().getListEquipement()) {
            if (equipement.getCodeBarreUtilise().equals(e.getCodeBarreUtilise())) {
                Toast.makeText(getApplicationContext(), "Déja scanné tricheur", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // Création du fragment
        EquipementScanFragment equipementScanFragment = new EquipementScanFragment();
        equipementScanFragment.setEquipement(equipement);

        lancerFragment(equipementScanFragment, true);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {
            Toast.makeText(this,
                    "onResume() - ACTION_TAG_DISCOVERED",
                    Toast.LENGTH_SHORT).show();

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag != null) {
                String tagInfo = tag.toString() + "\n";

                tagInfo += "\nTag Id: \n";
                byte[] tagId = tag.getId();
                tagInfo += "length = " + tagId.length + "\n";
                for (int i = 0; i < tagId.length; i++) {
                    tagInfo += Integer.toHexString(tagId[i] & 0xFF) + " ";
                }
                tagInfo += "\n";

                String[] techList = tag.getTechList();
                tagInfo += "\nTech List\n";
                tagInfo += "length = " + techList.length + "\n";
                for (int i = 0; i < techList.length; i++) {
                    tagInfo += techList[i] + "\n ";
                }

                // Appel du scan des créatures
                scanEffectue(tagInfo);

                Log.i("eee", tagInfo);
            }
        } else {
            Toast.makeText(this,
                    "onResume() : " + action,
                    Toast.LENGTH_SHORT).show();
        }

    }
}