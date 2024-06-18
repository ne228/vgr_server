package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.thiefs;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class ThiefCard extends ClassesCard {
    public ThiefCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Thief";
        _class = ClassList.THIEF;
    }

    @Override
    public void accept(Player player) {

    }

    @Override
    public void discard(Player player) {

    }

}
