package com.example.ais_ecc.munchkin.models.doorCards.racesCards.elfs;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public abstract class ElfCard extends RaceCard {
    public ElfCard(MunchkinContext munchkinContext) {
        super(munchkinContext);title = "Elf";
        this.race = RaceList.ELF;
    }

    @Override
    public void accept(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() + 1);
    }

    @Override
    public void discard(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() - 1);
    }

}
