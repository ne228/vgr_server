package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropArmorAndLegs;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropItemCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class LikeLouse extends EnemyCard {

    public LikeLouse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Типа Вошки";
        text = "От них нельзя Смыться";
        obscenityText = "Сбрось Броник и все " +
                "шмотки, надетые ниже пояса.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 1;
        defaultFlushValue = 100;
        this.iconPath = "/images/likeLouse.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var act = new ActionDropArmorAndLegs(player);
        if (act.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doAction(act);

    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.ELF)))
            return level + 5;

        return level;
    }
}
