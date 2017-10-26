package com.mbds.barcode_battler_android;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
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
import com.mbds.barcode_battler_android.fragment.CreatureScanFragment;
import com.mbds.barcode_battler_android.fragment.CreaturesFragment;
import com.mbds.barcode_battler_android.fragment.EquipementScanFragment;
import com.mbds.barcode_battler_android.fragment.EquipementsFragment;
import com.mbds.barcode_battler_android.fragment.ScannerFragment;

public class MainActivity extends AppCompatActivity implements ScannerFragment.OnScanEffectue {


    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        MainActivity.context = getApplicationContext();
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
            case R.id.action_scanner:
                // Create a new Fragment to be placed in the activity layout
                ScannerFragment scannerFragment = new ScannerFragment();
                scannerFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragment_container, scannerFragment).commit();
                return true;

            case R.id.action_ouvrir_creatures:
                lancerFragmentCreatures();
                return true;

            case R.id.action_ouvrir_equipements:
                lancerFragmentEquipements();
                return true;

            case R.id.action_lancer_combat:
                Toast.makeText(getApplicationContext(), "La bagarre !", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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

    private void lancerFragmentEquipements() {
        EquipementsFragment equipementsFragment = new EquipementsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, equipementsFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void lancerFragmentCreatures() {
        CreaturesFragment creaturesFragment = new CreaturesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, creaturesFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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

        Bundle args = new Bundle();
        args.putParcelable("creature", creature);
        creatureScanFragment.setArguments(args);

        // Début de la transaction pour ajouter ce fragment et lui passer la Créature
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, creatureScanFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
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

        Bundle args = new Bundle();
        args.putParcelable("equipement", equipement);
        equipementScanFragment.setArguments(args);

        // Début de la transaction pour ajouter ce fragment et lui passer l'équipement
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, equipementScanFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
