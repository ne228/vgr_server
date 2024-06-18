package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropHead;

public class Bigfoot extends EnemyCard {

    public Bigfoot(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Бигфут";
        text = "+3 если в бою есть " +
                "Дварфы или " +
                "Халфлинги.";
        obscenityText = "Непотребство: Наступил-то слегка - " +
                "да затрещали бока: хоть остался " +
                "живой, да с пустой головой. Теряешь " +
                "надетый Головняк.";
        rewardLevel = 1;
        rewardTreasure = 3;
        level = 12;
        this.iconPath = "/images/bigfoot.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var act = new ActionDropHead(player);
        if (act.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doAction(act);
    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(
                player -> (player.isRace(RaceList.DWARF)
                        ||
                        player.isRace(RaceList.HALFING)))
        )
            return level + 3;

        return level;
    }
}
