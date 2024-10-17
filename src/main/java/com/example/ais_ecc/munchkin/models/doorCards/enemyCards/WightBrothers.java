package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class WightBrothers extends EnemyCard {
    public WightBrothers(MunchkinContext munchkinContext) {

        super(munchkinContext);
        title = "Бледные братья";
        text = "Не преследует никого, чей Уровень З " +
                "или ниже. Остальные в случае " +
                "поражения теряют 2 Уровня, даже " +
                "если смываются.";
        obscenityText = "Непотребство: Низведён до 1 Уровня.";
        rewardLevel = 2;
        rewardTreasure = 4;
        level = 16;
        unDead = true;
        this.iconPath = "/images/wightBrothers.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        player.lvlUp(-10);
    }

    @Override
    public void flushing(Flushing flushing) {
        flushing.getPlayer().lvlUp(-2);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        if (player.getLvl() <=3)
            return false;
        return true;
    }
}
