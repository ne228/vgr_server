package com.example.ais_ecc.munchkin.models;

import java.util.ArrayList;

public class FightCounter {

    int clericExile = 3;
    int wizardFly = 3;
    int warriorRampage = 3;



    public int getWizardFly() {
        return wizardFly;
    }

    public void setWizardFly(int wizardFly) {
        this.wizardFly = wizardFly;
    }

    public int getClericExile() {
        return clericExile;
    }

    public void setClericExile(int clericExile) {
        this.clericExile = clericExile;
    }

    public ArrayList<Player> thiefCuts = new ArrayList<>();


    public ArrayList<Player> getThiefCuts() {
        return thiefCuts;
    }

    public void setThiefCuts(ArrayList<Player> thiefCuts) {
        this.thiefCuts = thiefCuts;
    }

    public int getWarriorRampage() {
        return warriorRampage;
    }

    public void setWarriorRampage(int warriorRampage) {
        this.warriorRampage = warriorRampage;
    }
}
