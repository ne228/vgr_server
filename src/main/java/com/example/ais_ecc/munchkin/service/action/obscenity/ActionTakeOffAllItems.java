package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionNull;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffArmor;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffHead;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffLegs;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffWeapon;

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
//            card.discard(player);
//            player.getCards().add(card);
//            player.setHeadItemCard(null);
        }
        if (player.getArmorItemCard() != null) {
            var card = player.getArmorItemCard();
            var act = new ActionTakeOffArmor(player, card);
            context.getActionHandler().doRawAction(act);
//            card.discard(player);
//            player.getCards().add(card);
//            player.setArmorItemCard(null);
        }
        if (player.getLegsItemCard() != null) {
            var card = player.getLegsItemCard();
            var act = new ActionTakeOffLegs(player, card);
            context.getActionHandler().doRawAction(act);
//            card.discard(player);
//            player.getCards().add(card);
//            player.setLegsItemCard(null);
        }
        if (player.getWeaponItemCard_1() != null) {
            var card = player.getWeaponItemCard_1();
            var act = new ActionTakeOffWeapon(player, card);
            context.getActionHandler().doRawAction(act);
//            card.discard(player);
//            player.getCards().add(card);
//            player.setWeaponItemCard_1(null);
        }

        if (player.getWeaponItemCard_2() != null) {
            var card = player.getWeaponItemCard_2();
            var act = new ActionTakeOffWeapon(player, card);
            context.getActionHandler().doRawAction(act);
//            card.discard(player);
//            player.getCards().add(card);
//            player.setWeaponItemCard_2(null);
        }

        context.getActionHandler().doAction(new ActionNull("Игрок сняли все шмотки!!"));

        return "Игрок " + player.getUser().getUsername() + " снял все свои шмотки";
    }
}
