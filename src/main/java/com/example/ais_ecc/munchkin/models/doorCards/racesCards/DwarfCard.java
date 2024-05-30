package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.races.DwarfRace;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class DwarfCard extends RaceCard {
    public DwarfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Dwarf";
    }

    @Override
    public Races getRace() {
        return new DwarfRace();
    }

}
