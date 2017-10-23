package com.mbds.barcode_battler_android;

/**
 * Created by barnini on 23/10/2017.
 */

public class Creature {

    private int PV; // Vie
    private int PA; // Attaque
    private int PB; // Bouclier, pas défense héhé

    public Creature(int pv, int pa, int pb){
        this.setPV(pv);
        this.setPA(pa);
        this.setPB(pb);
    }

    public int getPV() {
        return PV;
    }

    public void setPV(int PV) {
        this.PV = PV;
    }

    public int getPA() {
        return PA;
    }

    public void setPA(int PA) {
        this.PA = PA;
    }

    public int getPB() {
        return PB;
    }

    public void setPB(int PB) {
        this.PB = PB;
    }
}
