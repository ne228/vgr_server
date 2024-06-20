package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionFlushingRoll;

import java.util.ArrayList;
import java.util.Random;

public class ActionWizardFly extends IAction {
    ActionFlushingRoll actionFlushingRoll;
    Player player;
    ArrayList<Card> cards;
    int cubeNum;
    Fight fight;

    public ActionWizardFly(Player player,
                           ArrayList<Card> cards) {
        this.player = player;
        this.cards = cards;
        path = "wizard_fly";
        name = "Волебник: \"Заклинание полета\"";

    }

    public ActionWizardFly() {
        path = "wizard_fly";
        name = "Волебник: \"Заклинание полета\"";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        player = context.getCurrentPlayer();
        fight = context.getFight();
        if (fight == null)
            return false;

        if (!player.isClass(ClassList.WIZARD))
            return false;

        if (cards != null)
            if (cards.stream().noneMatch(card -> player.isHaveCard(card)))
                return false;

        var endActions = context.getActionHandler().getEndActions();
        var lastAction = endActions.get(endActions.size() - 1);
        if (lastAction instanceof ActionFlushingRoll) {
            actionFlushingRoll = (ActionFlushingRoll) lastAction;
            return true;
        }
        return false;
    }

    @Override
    public String start() throws Exception {

        for (var card : cards) {
            if (fight.getFightCounter().getWizardFly() <= 0)
                break;

            context.discardCard(card.getId());
            actionFlushingRoll.setRoll(actionFlushingRoll.getRoll() + 1);
            fight.getFightCounter().setWizardFly(fight.getFightCounter().getWizardFly() - 1);
        }


        return "Игрок " + player.getUser().getUsername() + " применил заклинание полета";
    }
}
