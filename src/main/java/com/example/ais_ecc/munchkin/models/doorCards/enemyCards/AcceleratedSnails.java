package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropAllHand;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class AcceleratedSnails extends EnemyCard {
    // TODO доделай карточку
    public AcceleratedSnails(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Ускоренные улитки";
        text = "-2 к смывке";
        obscenityText = "Непотребство: Дальнобойная атака" +
                "рвотными массами! Сбрось всю" +
                "“руку“.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 6;
        this.iconPath = "/images/acceleratedSnails.png";
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

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }

    @Override
    public void flushing(Flushing flushing) {
        flushing.setCubeNumber(flushing.getCubeNumber() - 2);
    }
}
