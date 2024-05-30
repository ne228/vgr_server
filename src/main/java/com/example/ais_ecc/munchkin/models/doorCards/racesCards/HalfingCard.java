package com.example.ais_ecc.munchkin.models.doorCards.racesCards;


import com.example.ais_ecc.munchkin.models.races.HalflingRace;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class HalfingCard extends RaceCard {

    public HalfingCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Halfing";
    }

    @Override
    public Races getRace() {
        return new HalflingRace();
    }

}
