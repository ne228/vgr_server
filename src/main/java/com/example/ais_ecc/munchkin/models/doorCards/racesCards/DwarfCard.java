package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class DwarfCard extends RaceCard {

    public DwarfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Dwarf";
        this.race = RaceList.DWARF;
    }

    @Override
    public void accept(Player player) {
    }

    @Override
    public void discard(Player player) {

    }

}
