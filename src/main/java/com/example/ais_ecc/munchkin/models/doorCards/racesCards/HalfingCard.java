package com.example.ais_ecc.munchkin.models.doorCards.racesCards;


import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class HalfingCard extends RaceCard {

    public HalfingCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Halfing";
        this.race = RaceList.HALFING;
    }

    @Override
    public void accept(Player player) {
    }

    @Override
    public void discard(Player player) {

    }

}
