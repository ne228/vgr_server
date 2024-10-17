package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropCubeOrcs;

import java.util.UUID;

public class Orcs extends EnemyCard {

    public Orcs(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "3872 Орка";
        text = "+6 против Дварфов из-за старых обид";
        obscenityText = "Брось кубик. На 2 или " +
                "ниже они затопчут тебя до смерти. В " +
                "остальных случаях теряешь столько " +
                "Уровней, сколько показал кубик.";
        rewardLevel = 1;
        rewardTreasure = 3;
        level = 10;
        this.iconPath = "/images/orcs.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var act = new ActionDropCubeOrcs(player, UUID.randomUUID().toString(),getMunchkinContext());
        getMunchkinContext().getActionHandler().addRequiredAction(act);
    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (fight.getFightPlayers().stream().anyMatch(player -> player.isRace(RaceList.DWARF)))
            return level + 6;

        return level;
    }
}
