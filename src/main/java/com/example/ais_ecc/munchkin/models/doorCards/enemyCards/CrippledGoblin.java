package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class CrippledGoblin extends EnemyCard {

    public CrippledGoblin(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Увеченый гоблин";
        text = "+1 к Смывке";
        obscenityText = "Лупит тебя" +
                "костылем." +
                "теряешь I Уровень.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 1;
        iconPath = "/images/crippledGoblin.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-2);
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }

    @Override
    public void flushing(Flushing flushing) {
        flushing.setCubeNumber(flushing.getCubeNumber() + 1);
    }
}
