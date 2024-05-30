package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.races.ElfRace;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ElfCard extends RaceCard {
    public ElfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Elf";
    }

    @Override
    public Races getRace() {
        return new ElfRace();
    }

}
