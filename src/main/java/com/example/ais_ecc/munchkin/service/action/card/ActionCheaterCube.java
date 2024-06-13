package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.CheaterCube;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.IRollAction;

public class ActionCheaterCube extends IAction {

    CheaterCube card;
    IRollAction rollAction;
    Player player;
    int cubeNum;
    public ActionCheaterCube(CheaterCube card, int cubeNum) {
        this.card = card;
        this.cubeNum = cubeNum;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        player = currentPlayer;
        if (!currentPlayer.isHaveCard(card))
            return false;
        var endActions = context.getActionHandler().getEndActions();
        var lastAction = endActions.get(endActions.size() - 1);
        if (lastAction instanceof IRollAction) {
            rollAction = (IRollAction) lastAction;
            return true;
        }
        return false;
    }

    @Override
    public String start() throws Exception {


        rollAction.setRoll(cubeNum);
        context.discardCard(card.getId());
        return "Игрок " + player.getUser().getUsername() + " изменил значения брошенного кубика на " + cubeNum;
    }
}
