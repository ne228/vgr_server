package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.classes.ThiefClass;
import com.example.ais_ecc.munchkin.models.classes.WarriorClass;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ThiefCard extends ClassesCard {
    public ThiefCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Thief";
    }

    @Override
    public Classes getClasses() {
        return new ThiefClass();
    }
}
