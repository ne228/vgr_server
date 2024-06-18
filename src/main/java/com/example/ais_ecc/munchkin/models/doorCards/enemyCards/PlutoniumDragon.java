package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionDie;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class PlutoniumDragon extends EnemyCard {

    public PlutoniumDragon(MunchkinContext munchkinContext) {

        super(munchkinContext);
        title = "Плутониевый дракон";
        text = "Не преследует никого чей Уровень 5 или ниже";
        obscenityText = "Ты изжарен и съеден. Ты мертв";
        rewardLevel = 2;
        rewardTreasure = 5;
        level = 20;
        this.iconPath = "/images/plutoniumDragon.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var actionDie = new ActionDie(player);
        getMunchkinContext().getActionHandler()
                .doAction(actionDie);
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
        // TODO верни
        if (player.getLvl() <= 5)
            return false;

        return true;
    }
}
