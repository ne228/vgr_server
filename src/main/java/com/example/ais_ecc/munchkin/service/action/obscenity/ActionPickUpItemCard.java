package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.RequiredAction;

public class ActionPickUpItemCard extends RequiredAction {

    private String scopeId;
    private Player whoPickUpPlayer;
    private Card card;
    private Player loserPlayer;

    public ActionPickUpItemCard(String scopeId, Player whoPickUpPlayer, Card card, Player loserPlayer) {
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.whoPickUpPlayer = whoPickUpPlayer;
        this.card = card;
        this.loserPlayer = loserPlayer;
        this.id = getId();
        this.path = "/play_required/" + card.getMunchkinContext().getId() + "?actionId=" + id;
        this.name = "Забрать карту " + card.getTitle() + " у игрока " + loserPlayer.getUser().getUsername();
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!whoPickUpPlayer.getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        if (loserPlayer.getHeadItemCard() != null)
            if (loserPlayer.getHeadItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (loserPlayer.getArmorItemCard() != null)
            if (loserPlayer.getArmorItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (loserPlayer.getLegsItemCard() != null)
            if (loserPlayer.getLegsItemCard().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (loserPlayer.getWeaponItemCard_1() != null)
            if (loserPlayer.getWeaponItemCard_1().getId().equalsIgnoreCase(card.getId()))
                return true;
        if (loserPlayer.getWeaponItemCard_2() != null)
            if (loserPlayer.getWeaponItemCard_2().getId().equalsIgnoreCase(card.getId()))
                return true;

        if (card instanceof BonusItemCard) {
            var bonusCard = loserPlayer.getPuttedBonusCard((BonusItemCard) card);
            if (bonusCard != null)
                return true;
        }

        return false;
    }

    @Override
    public String start() throws Exception {

        if (loserPlayer.getHeadItemCard() != null)
            if (loserPlayer.getHeadItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = loserPlayer.getHeadItemCard();
                loserPlayer.setHeadItemCard(null);
                card.discard(loserPlayer);
                whoPickUpPlayer.getCards().add(card);
                return "Игрок " + whoPickUpPlayer.getUser().getUsername() + " забрал карту " + card.getTitle();
            }
        if (loserPlayer.getArmorItemCard() != null)
            if (loserPlayer.getArmorItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = loserPlayer.getArmorItemCard();
                loserPlayer.setArmorItemCard(null);
                card.discard(loserPlayer);
                whoPickUpPlayer.getCards().add(card);
                return "Игрок " + whoPickUpPlayer.getUser().getUsername() + " забрал карту " + card.getTitle();
            }
        if (loserPlayer.getLegsItemCard() != null)
            if (loserPlayer.getLegsItemCard().getId().equalsIgnoreCase(card.getId())) {
                var card = loserPlayer.getLegsItemCard();
                loserPlayer.setLegsItemCard(null);
                card.discard(loserPlayer);
                whoPickUpPlayer.getCards().add(card);
                return "Игрок " + whoPickUpPlayer.getUser().getUsername() + " забрал карту " + card.getTitle();
            }
        if (loserPlayer.getWeaponItemCard_1() != null)
            if (loserPlayer.getWeaponItemCard_1().getId().equalsIgnoreCase(card.getId())) {
                var card = loserPlayer.getWeaponItemCard_1();
                loserPlayer.setWeaponItemCard_1(null);
                card.discard(loserPlayer);
                whoPickUpPlayer.getCards().add(card);
                return "Игрок " + whoPickUpPlayer.getUser().getUsername() + " забрал карту " + card.getTitle();
            }
        if (loserPlayer.getWeaponItemCard_2() != null)
            if (loserPlayer.getWeaponItemCard_2().getId().equalsIgnoreCase(card.getId())) {
                var card = loserPlayer.getWeaponItemCard_2();
                loserPlayer.setWeaponItemCard_2(null);
                card.discard(loserPlayer);
                whoPickUpPlayer.getCards().add(card);
                return "Игрок " + whoPickUpPlayer.getUser().getUsername() + " забрал карту " + card.getTitle();
            }
        if (card instanceof BonusItemCard) {
            var bonusCard = loserPlayer.getPuttedBonusCard((BonusItemCard) card);
            loserPlayer.getBonusItemCards().remove(bonusCard);
            bonusCard.discard(loserPlayer);
            whoPickUpPlayer.getCards().add(bonusCard);
            return "Игрок сбросил карту " + bonusCard.getTitle();
        }
        return null;
    }
}
