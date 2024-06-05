package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PottedGrass extends EnemyCard {
    private boolean elfPowerUp = false;

    public PottedGrass(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Трава в горшке";
        text = "Эльфы берут добавочное сокровише после победы над ним";
        obscenityText = "Никакого. Смывка автоматически";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 1;
        this.iconPath = "/images/pottedGrass.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {

    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {

        if (fight.getFightPlayers()
                .stream()
                .anyMatch(player -> player.isRace(RaceList.ELF))) {
            if (!elfPowerUp)
                rewardTreasure += 1;
        }
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return false;
    }
}
