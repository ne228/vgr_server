package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.warriors;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class WarriorCard extends ClassesCard {
    public WarriorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Warrior";
        _class = ClassList.WARRIOR;
    }

    @Override
    public void accept(Player player) {

    }

    @Override
    public void discard(Player player) {

    }


}
