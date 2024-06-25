package com.example.ais_ecc.munchkin.models;

public class MoveCounter {

    int halfingSell = 1;
    int thiefSteal = 1;

    public int getThiefSteal() {
        return thiefSteal;
    }

    public void setThiefSteal(int thiefSteal) {
        this.thiefSteal = thiefSteal;
    }

    public int getHalfingSell() {
        return halfingSell;
    }

    public void setHalfingSell(int halfingSell) {
        this.halfingSell = halfingSell;
    }
}
