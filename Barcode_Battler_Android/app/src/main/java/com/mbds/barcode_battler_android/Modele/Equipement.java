package com.mbds.barcode_battler_android.Modele;

/**
 * Created by PGNem on 23/10/2017.
 */

public class Equipement {

    private int bonusPA;
    private int bonusPV;
    private int bonusPB;

    public Equipement(int bonusPA, int bonusPV, int bonusPB) {
        this.setBonusPA(bonusPA);
        this.setBonusPV(bonusPV);
        this.setBonusPB(bonusPB);
    }

    public int getBonusPA() {
        return bonusPA;
    }

    public void setBonusPA(int bonusPA) {
        this.bonusPA = bonusPA;
    }

    public int getBonusPV() {
        return bonusPV;
    }

    public void setBonusPV(int bonusPV) {
        this.bonusPV = bonusPV;
    }

    public int getBonusPB() {
        return bonusPB;
    }

    public void setBonusPB(int bonusPB) {
        this.bonusPB = bonusPB;
    }
}
