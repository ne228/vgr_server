package com.example.ais_ecc.munchkin.models.doorCards.racesCards.dwarfs;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class DwarfCard extends RaceCard {

    public DwarfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Дварф";
        text = "Ты можешь нести любое количество " +
                "Больших шмоток. \n" +
                "Ты можешь держать в \"руке\" б карт.";
        this.race = RaceList.DWARF;
    }

    @Override
    public void accept(Player player) {
        player.setCanHandCardCount(player.getCanHandCardCount() + 1);
        player.setCanUseBigСlothesCount(player.getCanUseBigСlothesCount() + 10);
    }

    @Override
    public void discard(Player player) {
        player.setCanHandCardCount(player.getCanHandCardCount() - 1);
        player.setCanUseBigСlothesCount(player.getCanUseBigСlothesCount() - 10);
    }

}
