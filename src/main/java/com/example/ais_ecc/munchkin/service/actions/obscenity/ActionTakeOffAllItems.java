package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.ActionNull;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.items.*;

import java.util.ArrayList;

public class ActionTakeOffAllItems extends IAction {
    Player player;

    public ActionTakeOffAllItems(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (player.getHeadItemCard() == null &
                player.getArmorItemCard() == null &
                player.getLegsItemCard() == null &
                player.getWeaponItemCard_1() == null &
                player.getWeaponItemCard_2() == null)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        if (player.getHeadItemCard() != null) {
            var card = player.getHeadItemCard();
            var act = new ActionTakeOffHead(player, card);
            context.getActionHandler().doRawAction(act);
        }
        if (player.getArmorItemCard() != null) {
            var card = player.getArmorItemCard();
            var act = new ActionTakeOffArmor(player, card);
            context.getActionHandler().doRawAction(act);

        }
        if (player.getLegsItemCard() != null) {
            var card = player.getLegsItemCard();
            var act = new ActionTakeOffLegs(player, card);
            context.getActionHandler().doRawAction(act);
        }
        if (player.getWeaponItemCard_1() != null) {
            var card = player.getWeaponItemCard_1();
            var act = new ActionTakeOffWeapon(player, card);
            context.getActionHandler().doRawAction(act);
        }

        if (player.getWeaponItemCard_2() != null) {
            var card = player.getWeaponItemCard_2();
            var act = new ActionTakeOffWeapon(player, card);
            context.getActionHandler().doRawAction(act);
        }

        var bonusItemCards = new ArrayList<BonusItemCard>();
        bonusItemCards.addAll(player.getBonusItemCards());

        for (var bonusItemCard : bonusItemCards)
            context.getActionHandler()
                    .doRawAction(new ActionTakeOffBonus(player, bonusItemCard));


        context.getActionHandler().doAction(new ActionNull("Игрок сняли все шмотки!!"));

        return "Игрок " + player.getUser().getUsername() + " снял все свои шмотки";
    }
}
