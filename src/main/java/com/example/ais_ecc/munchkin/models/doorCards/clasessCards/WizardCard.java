package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.classes.WizardClass;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class WizardCard extends ClassesCard {
    public WizardCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Wizard";
    }


    @Override
    public Classes getClasses() {
        return new WizardClass();
    }
}
