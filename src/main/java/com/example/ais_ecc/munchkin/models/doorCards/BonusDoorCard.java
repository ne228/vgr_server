package com.example.ais_ecc.munchkin.models.doorCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.ActionBonusDoorCard;

import java.util.ArrayList;
import java.util.List;

public abstract class BonusDoorCard extends DoorCard {
    public BonusDoorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }


    public int bonusPower = 0;
    public int bonusTreasure = 0;

    public boolean bonusDoorCard = true;

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
        var bonusCard = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var enemyCard =  munchkinContext.getPlayerCardById(playCardRequest.getTargetCardId());
        var action = new ActionBonusDoorCard((BonusDoorCard) bonusCard, (EnemyCard) enemyCard, player);
        return action;

    }

    @Override
    public List<CardAction> getActions() throws Exception {
        List<CardAction> cardActions = new ArrayList<>();
        cardActions.addAll(super.getActions());

        var lastMove = munchkinContext.getLastMove();
        if (lastMove == null)
            return cardActions;

        if (lastMove.getFight() == null)
            return cardActions;

        if (lastMove.getFight().isEnd())
            return cardActions;

        for (var enemyCard : lastMove.getFight().getEnemyCards()) {
            PlayCardRequest playCardRequest = new PlayCardRequest(getId(), "null", false, enemyCard.getId());
            var path = playCardRequest.toEndpointPath(getEdnpointPlayCard(), munchkinContext.getId());
            CardAction cardAction = new CardAction(path,
                    "Monster Bonus card: " + enemyCard.getTitle());
            cardActions.add(cardAction);
        }
        return cardActions;
    }
}
