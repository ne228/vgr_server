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

public class Harpists extends EnemyCard {
    public Harpists(MunchkinContext munchkinContext) {

        super(munchkinContext);
        title = "Гарпистки";
        text = "Невосприимчив к магии.\n" +
                "+5 Против Волшебников.";
        obscenityText = "Их музыука просто ужасна. Потеряй 2 уровня.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 4;
        this.iconPath = "/images/harpists.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        player.lvlUp(-2);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {


        if (fight.getFightPlayers()
                .stream()
                .anyMatch(player -> player.isClass(ClassList.WIZARD)))
            return level + 5;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
