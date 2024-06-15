package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;

public class ActionDropMyItemCard extends RequiredAction {

    Player player;
    Card card;

    public ActionDropMyItemCard(String scopeId, Player player, Card card) {
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.player = player;
        this.card = card;

        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + card.getMunchkinContext().getId() + "?actionId=" + id;
        this.name = "Сбросить карту  " + card.getTitle();
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!player.getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        if (player.getHeadItemCard() != null)
            if (player.getHeadItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (player.getArmorItemCard() != null)
            if (player.getArmorItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (player.getLegsItemCard() != null)
            if (player.getLegsItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (player.getWeaponItemCard_1() != null)
            if (player.getWeaponItemCard_1().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (player.getWeaponItemCard_2() != null)
            if (player.getWeaponItemCard_2().getId().equalsIgnoreCase(card.getId()))
                return true;

        return false;
    }

    @Override
    public String start() throws Exception {


        if (player.getHeadItemCard() != null)
            if (player.getHeadItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = player.getHeadItemCard();
                player.setHeadItemCard(null);
                card.discard(player);
                player.getCards().add(card);
                context.discardCard(card.getId());
                return "Игрок сбросил карту " + card.getTitle();
            }
        if (player.getArmorItemCard() != null)
            if (player.getArmorItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = player.getArmorItemCard();
                player.setArmorItemCard(null);
                card.discard(player);
                player.getCards().add(card);
                context.discardCard(card.getId());
                return "Игрок сбросил карту " + card.getTitle();
            }
        if (player.getLegsItemCard() != null)
            if (player.getLegsItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = player.getLegsItemCard();
                player.setLegsItemCard(null);
                card.discard(player);
                player.getCards().add(card);
                context.discardCard(card.getId());
                return "Игрок сбросил карту " + card.getTitle();
            }
        if (player.getWeaponItemCard_1() != null)
            if (player.getWeaponItemCard_1().getId().equalsIgnoreCase(card.getId())) {
                var card = player.getWeaponItemCard_1();
                player.setWeaponItemCard_1(null);
                card.discard(player);
                player.getCards().add(card);
                context.discardCard(card.getId());
                return "Игрок сбросил карту " + card.getTitle();
            }
        if (player.getWeaponItemCard_2() != null)
            if (player.getWeaponItemCard_2().getId().equalsIgnoreCase(card.getId())) {
                var card = player.getWeaponItemCard_2();
                player.setWeaponItemCard_2(null);
                card.discard(player);

                player.getCards().add(card);
                context.discardCard(card.getId());
                return "Игрок сбросил карту " + card.getTitle();
            }


        return null;
    }
}
