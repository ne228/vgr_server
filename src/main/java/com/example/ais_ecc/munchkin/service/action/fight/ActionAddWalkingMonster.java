package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.doorCards.WalkingMonsterCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionAddWalkingMonster extends IAction {
    Fight fight;
    Move move;
    WalkingMonsterCard walkingMonsterCard;

    public ActionAddWalkingMonster(WalkingMonsterCard walkingMonsterCard) {
        this.walkingMonsterCard = walkingMonsterCard;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isGameStarted)
            return false;

        move = context.getLastMove();
        if (move == null)
            return false;

        fight = move.getFight();
        if (fight == null)
            return false;

        if (fight.isEnd())
            return false;

        if (fight.isLoss())
            return false;

        if (fight.isAgree())
            return false;

        var player = context.getCurrentPlayer();
        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(walkingMonsterCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        fight.getDoorCards().add(walkingMonsterCard);

        context.discardCard(walkingMonsterCard.getId());
        var player = context.getCurrentPlayer();

        return "Игрок " + player.getUser().getUsername() + " подкинул карту + " + walkingMonsterCard.getTitle();
    }
}
