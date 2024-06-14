package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class FlyingFrogs  extends EnemyCard {
    public FlyingFrogs(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Летучие лягушки";
        text = "-1 к Смывке";
        obscenityText = "Непотребство: Они " +
                "Потеряй 2 Уровня. " +
                "кусаются!";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 2;
        iconPath = "/images/flyingFrogs.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-2);
    }




    @Override
    public void flushing(Flushing flushing) {
        flushing.setCubeNumber(flushing.getCubeNumber() - 1);
    }
}
