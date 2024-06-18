package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UndeadHorse extends EnemyCard {
    public UndeadHorse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Андедный коник";
        text = "+5 против Дварфов.";
        obscenityText = "Лягает, кусает и " +
                "жутко воняет. Теряешь 2 Уровня.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 4;
        unDead = true;
        this.iconPath = "/images/undeadHorse.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        player.lvlUp(-2);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.DWARF)))
            return level + 5;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
