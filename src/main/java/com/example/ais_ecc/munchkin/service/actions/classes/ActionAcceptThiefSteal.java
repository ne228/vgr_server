package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.*;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;
import com.example.ais_ecc.munchkin.service.actions.card.items.*;

public class ActionAcceptThiefSteal extends RequiredAction {

    ActionThiefSteal actionThiefSteal;
    int needCubNum = 4;


    Player player;

    public ActionAcceptThiefSteal(ActionThiefSteal actionThiefSteal, String scopeId, MunchkinContext context) {
        this.actionThiefSteal = actionThiefSteal;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        if (actionThiefSteal.getRoll() >= needCubNum)
            this.name = "Украсть карту " + actionThiefSteal.getCard().getTitle();
        else
            this.name = "Тебя лупят. Потеряй уровень.";


        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (actionThiefSteal.getRoll() >= needCubNum)
            this.name = "Украсть карту " + actionThiefSteal.getCard().getTitle();
        else
            this.name = "Тебя лупят. Потеряй уровень.";
        if (!context.isGameStarted)
            return false;

        player = context.getCurrentPlayer();
        if (!player.isClass(ClassList.THIEF))
            return false;
//        var lastAction = context.getActionHandler().getActions().get(context.getActionHandler().getActions().size() - 1);
//        if (!(lastAction instanceof ActionThiefSteal))
//            return false;

        var whoLossCardPlayer = actionThiefSteal.getWhoLossCardPlayer();
        var card = actionThiefSteal.getCard();

        if (whoLossCardPlayer != null) {
            if (card.isBigSize())
                return false;

            if (card instanceof HeadItemCard)
                if (whoLossCardPlayer.getHeadItemCard() != null)
                    if (!whoLossCardPlayer.getHeadItemCard().getId().equalsIgnoreCase(card.getId()))
                        return false;

            if (card instanceof ArmorItemCard)
                if (whoLossCardPlayer.getArmorItemCard() != null)
                    if (!whoLossCardPlayer.getArmorItemCard().getId().equalsIgnoreCase(card.getId()))
                        return false;

            if (card instanceof LegsItemCard)
                if (whoLossCardPlayer.getLegsItemCard() != null)
                    if (!whoLossCardPlayer.getLegsItemCard().getId().equalsIgnoreCase(card.getId()))
                        return false;

            if (card instanceof WeaponItemCard) {
                if (whoLossCardPlayer.getWeaponItemCard_1() != null)
                    if (!whoLossCardPlayer.getWeaponItemCard_1().getId().equalsIgnoreCase(card.getId()))
                        return false;

                if (whoLossCardPlayer.getWeaponItemCard_2() != null)
                    if (!whoLossCardPlayer.getWeaponItemCard_2().getId().equalsIgnoreCase(card.getId()))
                        return false;
            }

            if (card instanceof BonusItemCard)
                if (whoLossCardPlayer.getBonusItemCards()
                        .stream().noneMatch(bonusItemCard -> bonusItemCard.getId().equalsIgnoreCase(card.getId())))
                    return false;
        }

        return true;
    }

    @Override
    public String start() throws Exception {
        if (actionThiefSteal.getRoll() >= needCubNum) {
            if (actionThiefSteal.getCard() instanceof HeadItemCard)
                context.getActionHandler().doAction(new ActionTakeOffHead(actionThiefSteal.getWhoLossCardPlayer(), (HeadItemCard) actionThiefSteal.getCard()));

            if (actionThiefSteal.getCard() instanceof ArmorItemCard)
                context.getActionHandler().doAction(new ActionTakeOffArmor(actionThiefSteal.getWhoLossCardPlayer(), (ArmorItemCard) actionThiefSteal.getCard()));

            if (actionThiefSteal.getCard() instanceof LegsItemCard)
                context.getActionHandler().doAction(new ActionTakeOffLegs(actionThiefSteal.getWhoLossCardPlayer(), (LegsItemCard) actionThiefSteal.getCard()));

            if (actionThiefSteal.getCard() instanceof WeaponItemCard)
                context.getActionHandler().doAction(new ActionTakeOffWeapon(actionThiefSteal.getWhoLossCardPlayer(), (WeaponItemCard) actionThiefSteal.getCard()));

            if (actionThiefSteal.getCard() instanceof BonusItemCard)
                context.getActionHandler().doAction(new ActionTakeOffBonus(actionThiefSteal.getWhoLossCardPlayer(), (BonusItemCard) actionThiefSteal.getCard()));

            actionThiefSteal.getWhoLossCardPlayer().getCards().remove(actionThiefSteal.getCard());
            player.getCards().add(actionThiefSteal.getCard());

            return "Игрок " + player.getUser().getUsername() + " украл карту " + actionThiefSteal.getCard().getTitle();
        } else {
            player.lvlUp(-1);
            return "Игрока " + player.getUser().getUsername() + " лупят. Теряет 1 Уровень";
        }

    }
}
