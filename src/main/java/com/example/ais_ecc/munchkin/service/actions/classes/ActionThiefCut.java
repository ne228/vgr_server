package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionThiefCut extends IAction {
    Player player;
    Card card;
    Player playerCut;
    Fight fight;

    public ActionThiefCut(Player playerCut, Card card) {
        this.playerCut = playerCut;
        this.card = card;
        path = "thief_cut";
        name = "Вор: \"Подрезка\"";
    }

    public ActionThiefCut() {
        path = "thief_cut";
        name = "Вор: \"Подрезка\"";
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;


        if (!context.isGameStarted)
            return false;

        player = context.getCurrentPlayer();
        if (!player.isClass(ClassList.THIEF))
            return false;

        fight = context.getFight();
        if (fight == null)
            return false;

        if (fight.isEnd())
            return false;

        if (fight.isLoss())
            return false;

        if (fight.getFightPlayers().size() == fight.getFightCounter().getThiefCuts().size())
            return false;


        if (playerCut != null){
            if (fight.getFightPlayers().stream().noneMatch(playerFight -> playerFight.getId().equalsIgnoreCase(playerCut.getId())))
                return false;

            if (fight.getFightCounter().getThiefCuts().stream().anyMatch(playerFight -> playerFight.getId().equalsIgnoreCase(playerCut.getId())))
                return false;
        }
        if (card != null){
            if (!player.isHaveCard(card))
                return false;
        }


        return true;
    }

    @Override
    public String start() throws Exception {
        context.discardCard(card.getId());
        fight.getFightCounter().getThiefCuts().add(playerCut);
        fight.addBonusPlayerPower(-2);
        return "Игрок " + player.getUser().getUsername() + " подрезал игрока " + playerCut.getUser().getUsername();
    }
}
