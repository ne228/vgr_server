package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropAllBigItems;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class GelatinOctahedron extends EnemyCard {
    public GelatinOctahedron(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Желатиновый октаэдр";
        text = "+1 к смывке";
        obscenityText = "Непотребство: Сбрось все свои " +
                "Большие шмотки.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 2;
        this.iconPath = "/images/gelatinOctahedron.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var dropAllHand = new ActionDropAllBigItems(player);
        getMunchkinContext().getActionHandler()
                .doAction(dropAllHand);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        return level;
    }

    @Override
    public void flushing(Flushing flushing) {
        flushing.setCubeNumber(flushing.getCubeNumber() + 1);
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
