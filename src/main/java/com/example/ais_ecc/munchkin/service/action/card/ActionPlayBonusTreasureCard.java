package com.example.ais_ecc.munchkin.service.action.card;

import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.BonusTreasureCard;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionPlayBonusTreasureCard extends IAction {

    private Move move;

    private Fight fight;
    private MunchkinContext context;
    private BonusTreasureCard bonusTreasureCard;
    private boolean isHarm;

    public ActionPlayBonusTreasureCard(BonusTreasureCard bonusTreasureCard, boolean isHarm) {
        this.bonusTreasureCard = bonusTreasureCard;
        this.isHarm = isHarm;
        this.name = "Bonus";
        this.title = "Bonus";
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
                .anyMatch(card -> card.getId().equalsIgnoreCase(bonusTreasureCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        var player = context.getCurrentPlayer();
        var text = "";
        if (isHarm){
            fight.getHarmTreasureCards().add(bonusTreasureCard);
            text = "Player " + player.getUser().getUsername() + " play card " + bonusTreasureCard.getTitle() + " to the detriment of the munchkins";
        }
        else {
            fight.getHelpTreasureCards().add(bonusTreasureCard);
            text = "Player " + player.getUser().getUsername() + " play card " + bonusTreasureCard.getTitle() + " in favor of the munchkins";
        }

        context.discardCard(bonusTreasureCard.getId());
        return text;
    }
}
