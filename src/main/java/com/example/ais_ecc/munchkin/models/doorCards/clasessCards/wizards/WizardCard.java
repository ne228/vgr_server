package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class WizardCard extends ClassesCard {
    public WizardCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Wizard";
        _class = ClassList.WIZARD;
    }

    @Override
    public void accept(Player player) {

    }

    @Override
    public void discard(Player player) {

    }


}
