package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.obscenity.ActionDropMyItemCard;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class TongueDemon extends EnemyCard {

    boolean dropCard = true;

    public TongueDemon(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Языческий демон";
        text = "Тварь из Ада! +4 против Клириков. " +
                "Ты должен сбросить шмотку (какую " +
                "хочешь) перед боем.";
        obscenityText = "Омерзительный " +
                "поцелуй лишает " +
                "тебя 2 Уровней " +
                "(З для " +
                "Эльфов).";
        rewardLevel = 1;
        rewardTreasure = 3;
        level = 12;
        this.iconPath = "/images/tongueDemon.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        if (player.isRace(RaceList.ELF))
            player.lvlUp(-3);
        else
            player.lvlUp(-2);
    }

    @JsonIgnore
    @Override
    public int getTotalPower(Fight fight) throws Exception {
        if (dropCard) {
            var player = fight.getPlayer();
            var scopeId = UUID.randomUUID().toString();
            if (player.getHeadItemCard() != null) {
                var act = new ActionDropMyItemCard(scopeId, player, player.getHeadItemCard());
                getMunchkinContext().getActionHandler().addRequiredAction(act);
            }

            if (player.getArmorItemCard() != null) {
                var act = new ActionDropMyItemCard(scopeId, player, player.getArmorItemCard());
                getMunchkinContext().getActionHandler().addRequiredAction(act);
            }

            if (player.getLegsItemCard() != null) {
                var act = new ActionDropMyItemCard(scopeId, player, player.getLegsItemCard());
                getMunchkinContext().getActionHandler().addRequiredAction(act);
            }

            if (player.getWeaponItemCard_1() != null) {
                var act = new ActionDropMyItemCard(scopeId, player, player.getWeaponItemCard_1());
                getMunchkinContext().getActionHandler().addRequiredAction(act);
            }

            if (player.getWeaponItemCard_2() != null) {
                var act = new ActionDropMyItemCard(scopeId, player, player.getWeaponItemCard_2());
                getMunchkinContext().getActionHandler().addRequiredAction(act);
            }
            dropCard = false;
        }

        if (fight.getFightPlayers().stream().anyMatch(player -> player.isClass(ClassList.CLERIC)))
            return level + 4;

        return level;
    }

    @Override
    public boolean canChaise(Player player) {
        return true;
    }
}
