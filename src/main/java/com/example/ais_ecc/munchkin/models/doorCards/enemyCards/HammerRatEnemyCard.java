package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class HammerRatEnemyCard extends EnemyCard {

    public HammerRatEnemyCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Молотая крысотка";
        text = "Тварь из ада. +3 против клирков";
        obscenityText = "лупит тебя кувалодой. Теряешь 1 уровень";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 1;
        unDead = false;
        iconPath = "/images/hammerRat.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) {
        player.lvlUp(-1);
    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {

        if (fight != null) {
            if (fight.getPlayer().isClass(ClassList.CLERIC))
                return level + 3;

            for (var helpPlayerOrder : fight.getFightOrders()) {
                if (helpPlayerOrder.getPlayer().isClass(ClassList.CLERIC))
                    return level + 3;
            }
        }
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }


}
