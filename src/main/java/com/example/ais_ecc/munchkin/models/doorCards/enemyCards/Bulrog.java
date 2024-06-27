package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.share.ActionDie;

public class Bulrog extends EnemyCard {


    public Bulrog(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Бульрог";
        text = "Не преследует никого, чей Уровень 4 " +
                "или ниже.";
        obscenityText = "Непотребство: Застегал тебя до " +
                "смерти.";
        rewardLevel = 2;
        rewardTreasure = 5;
        level = 18;
        this.iconPath = "/images/bulrog.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var act = new ActionDie(player);
        if (act.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doAction(act);
    }

    @Override
    public boolean canChaise(Player player) {
        if (player.getLvl() <= 4)
            return false;

        return true;
    }

}
