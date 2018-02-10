package com.mbds.barcode_battler_android.Service;

import android.widget.EditText;

/**
 * Created by PGNem on 10/02/2018.
 */

public class Combat_Log_Thread extends Thread{

    public EditText ezEditText;

    public Combat_Log_Thread(EditText et){
        this.ezEditText = et;
    }

    @Override
    public void run() {
        System.out.println("Mon traitement");
    }

}
