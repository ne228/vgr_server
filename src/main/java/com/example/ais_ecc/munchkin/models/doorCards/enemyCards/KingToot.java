package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropAllHand;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionTakeOffAllItems;

public class KingToot extends EnemyCard {

    public KingToot(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Король тут";
        text = "Не преследует никого чей уровень 3 или ниже. Остальные в случае поражения теряют 2 уровня, даже если смываюьтся";
        obscenityText = "Теряешь все свои " +
                "шмотки и всю \"руку\".";
        rewardLevel = 2;
        rewardTreasure = 4;
        level = 16;
        unDead = true;
        iconPath = "/images/kingToot.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var actionTakeOffAllItems = new ActionTakeOffAllItems(player);
        getMunchkinContext().getActionHandler().doAction(actionTakeOffAllItems);

        var actionDropAllHand = new ActionDropAllHand(player, "1", getMunchkinContext());
        getMunchkinContext().getActionHandler().doAction(actionDropAllHand);

    }

    @Override
    public boolean canChaise(Player player) {
        if (player.getLvl() <= 3)
            return false;
        return true;
    }


    @Override
    public void flushing(Flushing flushing) {
        munchkinContext.getMessages().add("Игрок " + flushing.getPlayer().getUser().getUsername() + " теряет 2 уровень");
        flushing.getPlayer().lvlUp(-2);
    }
}
