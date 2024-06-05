package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDrop2Lvl;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropAllHand;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class Uticora extends EnemyCard {

    public Uticora(MunchkinContext munchkinContext) {

        super(munchkinContext);
        title = "Утикора";
        text = "Невосприимчив к магии.\n" +
                "+6 Против Волшебников.";
        obscenityText = "Либо сбрось всю" +
                "“руку“, либо потеряй 2 Уровня.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 6;
        this.iconPath = "/images/uticora.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var scopeId = UUID.randomUUID().toString();
        var actionDrop2Lvl = new ActionDrop2Lvl(player, scopeId, getMunchkinContext());
        var dropAllHand = new ActionDropAllHand(player, scopeId, getMunchkinContext());

        getMunchkinContext().getActionHandler()
                .addRequiredAction(actionDrop2Lvl);

        getMunchkinContext().getActionHandler()
                .addRequiredAction(dropAllHand);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {


        if (fight.getFightPlayers()
                .stream()
                .anyMatch(player -> player.isClass(ClassList.WIZARD)))
            return level + 6;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
