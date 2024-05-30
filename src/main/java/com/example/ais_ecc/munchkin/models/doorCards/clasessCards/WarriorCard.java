package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.classes.WarriorClass;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class WarriorCard extends ClassesCard {
    public WarriorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Warrior";
    }




    @Override
    public Classes getClasses() {
        return new WarriorClass();
    }
}
