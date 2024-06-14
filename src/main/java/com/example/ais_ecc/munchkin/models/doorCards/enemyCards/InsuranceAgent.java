package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDrop1000Gold;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropAllItems;

import java.util.UUID;

public class InsuranceAgent extends EnemyCard {

    public InsuranceAgent(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Страховой агент";
        text = "Твой уровень прибавляется к силе " +
                "Страхового Агента. (Победи его только " +
                "бонусами)";
        obscenityText = "Непотребство " +
                "Придётся купить " +
                "страховку. Отдай " +
                "1000 голдов. " +
                "Если такой суммы " +
                "не набирается, " +
                "теряешь все, " +
                "что есть.";
        rewardLevel = 1;
        rewardTreasure = 4;
        level = 14;
        unDead = false;
        iconPath = "/images/insuranceAgent.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var scopeId = UUID.randomUUID().toString();

        var actionDrop1000Gold = new ActionDrop1000Gold(player, scopeId, getMunchkinContext());
        var actionDropAllItems = new ActionDropAllItems(player, scopeId, getMunchkinContext());

        getMunchkinContext().getActionHandler().addRequiredAction(actionDrop1000Gold);
        getMunchkinContext().getActionHandler().addRequiredAction(actionDropAllItems);
    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {

        int bonusPlayerLvl = 0;
        if (fight != null) {
            for (var player : fight.getFightPlayers())
                bonusPlayerLvl += player.getLvl();
        }
        return level + bonusPlayerLvl;
    }

    @Override
    public boolean canChaise(Player player) {

        return true;
    }
}
