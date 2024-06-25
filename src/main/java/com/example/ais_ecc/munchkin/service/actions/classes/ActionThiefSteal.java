package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.*;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.IRollAction;

import java.util.Random;
import java.util.UUID;

public class ActionThiefSteal extends IAction implements IRollAction {
    Player player;
    Player whoLossCardPlayer;
    ItemCard card;
    int roll = 0;
    Move move;

    public ActionThiefSteal(Player whoLossCardPlayer, ItemCard card) {
        this.whoLossCardPlayer = whoLossCardPlayer;
        this.card = card;
        path = "thief_steal";
        name = "Вор: \"Кража\"";
    }

    public ActionThiefSteal() {
        path = "thief_steal";
        name = "Вор: \"Кража\"";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;


        if (!context.isGameStarted)
            return false;

        player = context.getCurrentPlayer();
        if (!player.isClass(ClassList.THIEF))
            return false;

        if (!context.isYourMove())
            return false;


        move = context.getLastMove();

        if (move.getMoveCounter().getThiefSteal() <= 0)
            return false;


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
            if (card instanceof BonusItemCard) {
                if (whoLossCardPlayer.getBonusItemCards()
                        .stream().noneMatch(bonusItemCard -> bonusItemCard.getId().equalsIgnoreCase(card.getId()))) {
                    return false;
                }
            }
            return true;
        }
        return true;


    }

    @Override
    public String start() throws Exception {

        Random random = new Random();
        roll = random.nextInt(6) + 1;
        var actionAcceptThiefSteal = new ActionAcceptThiefSteal(this, UUID.randomUUID().toString(), context);
        context.getActionHandler().addRequiredAction(actionAcceptThiefSteal);

        move.getMoveCounter().setThiefSteal(move.getMoveCounter().getThiefSteal() - 1);

        return "Игрок " + player.getUser().getUsername() + " использовал \"Кража\" - " + card.getTitle() + " Бросил кубик на " + roll;

    }

    @Override
    public void setRoll(int rollNum) {
        this.roll = rollNum;
    }

    @Override
    public int getRoll() {
        return roll;
    }

    public Player getWhoLossCardPlayer() {
        return whoLossCardPlayer;
    }

    public ItemCard getCard() {
        return card;
    }
}
