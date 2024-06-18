package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionTakeOffAllItems;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Mademonouselli extends EnemyCard {
    int countPlayer = 0;

    public Mademonouselli(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "МАДЕМОНУАЗЕЛИ";
        text = "Никакие шмотки против них не" +
                "помогают - бейся только уровнем.";
        obscenityText = "Непотребство: Твой Уровень " +
                "приравнивается к самому низкому " +
                "Уровню персонажа в игре.";
        rewardLevel = 1;
        rewardTreasure = 2;
        level = 8;
        this.iconPath = "/images/mademonouselli.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        var players = new ArrayList<Player>();
        players.addAll(getMunchkinContext().getPlayers());
        int minLvl = players.stream()
                .map(Player::getLvl)
                .min(Integer::compare)
                .get();

        player.setLvl(minLvl);
    }


    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        var players = fight.getFightPlayers();
        if (countPlayer != players.size()){
            countPlayer = players.size();
            for (var player : players) {
                var action = new ActionTakeOffAllItems(player);
                if (action.canAmI(getMunchkinContext()))
                    getMunchkinContext().getActionHandler()
                            .doAction(action);
            }

        }

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
