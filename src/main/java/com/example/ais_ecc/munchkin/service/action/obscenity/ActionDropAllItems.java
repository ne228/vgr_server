package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffArmor;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffHead;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffLegs;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffWeapon;

import java.util.ArrayList;
import java.util.List;

public class ActionDropAllItems extends RequiredAction {

    Player player;

    public ActionDropAllItems(Player player, String scopeId, MunchkinContext context) {
        this.player = player;
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Отдать все вещи";
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();

        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
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

        List<Card> cards = new ArrayList<>();
        cards.addAll(player.getCards());
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            context.discardCard(card.getId());
        }

        return "Игрок " + player.getUser().getUsername() + " отдал все свои вещи Страховому Агенту";

    }
}
