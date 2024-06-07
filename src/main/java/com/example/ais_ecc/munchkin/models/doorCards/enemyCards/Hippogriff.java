package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionPicUpRandomHandCard;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionPickUpItemCard;

import java.util.ArrayList;
import java.util.UUID;

public class Hippogriff extends EnemyCard {
    public Hippogriff(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Гиппогриф";
        text = "Не преследует никого чей уровень ниже 3";
        obscenityText = "Непотребство: Тебя потоптали и " +
                "пожевали. Пытаясь смыться. ты " +
                "растерял горы добра. Начиная с " +
                "правого соседа, каждый может взять " +
                "одну из твоих карт Сокровищ либо " +
                "со стола, либо из \"руки\" (не глядя).";
        rewardLevel = 2;
        rewardTreasure = 4;
        level = 16;
        unDead = false;
        iconPath = "/images/hippogriff.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {

        var players = new ArrayList<Player>();
        players.addAll(getMunchkinContext().getPlayers());
        var fightPlayers = new ArrayList<Player>();
        fightPlayers.addAll(fight.getFightPlayers());
        players.removeAll(fightPlayers);


        for (var whoPickUpPlayer : players) {
            var scopeId = UUID.randomUUID().toString();
            if (player.getHeadItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, whoPickUpPlayer, player.getHeadItemCard(), player));

            if (player.getArmorItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, whoPickUpPlayer, player.getArmorItemCard(), player));


            if (player.getLegsItemCard() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, whoPickUpPlayer, player.getLegsItemCard(), player));

            if (player.getWeaponItemCard_1() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, whoPickUpPlayer, player.getWeaponItemCard_1(), player));

            if (player.getWeaponItemCard_2() != null)
                getMunchkinContext()
                        .getActionHandler()
                        .addRequiredAction(new ActionPickUpItemCard(scopeId, whoPickUpPlayer, player.getWeaponItemCard_2(), player));


            getMunchkinContext()
                    .getActionHandler()
                    .addRequiredAction(new ActionPicUpRandomHandCard(scopeId, player, whoPickUpPlayer, getMunchkinContext()));

        }

    }

    @Override
    public int getTotalPower(Fight fight) throws Exception {

        if (fight != null) {
            if (fight.getPlayer().isClass(ClassList.CLERIC))
                return level + 3;

            for (var helpPlayerOrder : fight.getFightOrders()) {
                if (helpPlayerOrder.getPlayer().isClass(ClassList.CLERIC))
                    return level + 3;
            }
        }
        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        // TODO вернуи лвлв
//        if (player.getLvl() < 3)
//            return false;

        return true;
    }
}
