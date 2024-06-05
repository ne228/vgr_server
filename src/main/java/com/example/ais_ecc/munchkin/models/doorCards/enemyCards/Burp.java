package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropAllHand;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Burp extends EnemyCard {


    public Burp(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Рыгачу";
        text = "Получи дополнительный Уровень, " +
                "если победил его без Бонусов и чужой " +
                "помощи.";
        obscenityText = "Непотребство: Дальнобойная атака" +
                "рвотными массами! Сбрось всю" +
                "' фуку“.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 6;
        this.iconPath = "/images/burp.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var dropAllHand = new ActionDropAllHand(player, "1", getMunchkinContext());
        getMunchkinContext().getActionHandler()
                .doAction(dropAllHand);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().size() == 1 && fight.getHelpTreasureCards().size() == 0)
            rewardLevel = 2;
        else
            rewardLevel = 1;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
