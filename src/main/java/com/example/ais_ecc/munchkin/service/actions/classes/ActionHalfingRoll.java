package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionFlushingRoll;

import java.util.Random;

public class ActionHalfingRoll extends IAction {

    ActionFlushingRoll actionFlushingRoll;
    Player player;
    Card discardCard;
    int cubeNum;

    public ActionHalfingRoll(Player player,
                             Card discardCard) {
        this.player = player;
        this.discardCard = discardCard;
        path = "halfing_roll";
        name = "Халфинг: \"Перебросить кубик на смывку\"";

    }

    public ActionHalfingRoll() {
        path = "halfing_roll";
        name = "Халфинг: \"Перебросить кубик на смывку\"";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        player = context.getCurrentPlayer();
        if (!player.isRace(RaceList.HALFING))
            return false;

        if (discardCard != null)
            if (!player.isHaveCard(discardCard))
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

        Random random = new Random();
        int roll = random.nextInt(6) + 1;
        roll += player.getDefBonusFlushing();

        actionFlushingRoll.setRoll(roll);

        return "Игрок " + player.getUser().getUsername() + " сброисл карту " + discardCard.getTitle() + ". И перебросил кубинк на смывку" +
                " на значение: " + roll;
    }
}
