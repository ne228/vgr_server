package com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl.wishingRing;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.curse.ActionAcceptCurse;
import com.example.ais_ecc.munchkin.service.actions.curse.ActionRemoveCurse;

import java.util.List;

public abstract class WishingRing extends TreasureCard {
    public WishingRing(MunchkinContext munchkinContext) {
        super(munchkinContext);
        cost = 500;
        title = "Хотельное кольцо";
        text = "Снимает любое Проклятие. Применить в любой момент. Одноразовая.";

    }


    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        if (!playCardRequest.getPlayerId().equalsIgnoreCase("null")) {
            var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
            var wishingCard = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
            var reqCurseCard =  munchkinContext.getPlayerCardById(playCardRequest.getTargetCardId());
            for (var curseCard : player.getCurses())
                if (reqCurseCard.getId().equalsIgnoreCase(curseCard.getId()))
                    return new ActionRemoveCurse(player, curseCard, this);
        }


        var actions = getMunchkinContext().getActionHandler().getRequiredActions();
        var optAct = actions.stream().filter(action -> action instanceof ActionAcceptCurse).findFirst();
        if (optAct.isPresent())
            return new ActionRemoveCurse((ActionAcceptCurse) optAct.get(), this);

        return super.createAction(playCardRequest);
    }


    @Override
    public List<CardAction> getActions() throws Exception {
        List<CardAction> cardActions = super.getActions();

        for (var player : munchkinContext.getPlayers()) {

            for (var curseCard : player.getCurses()) {
                var playCardRequest = new PlayCardRequest(getId(), player.getId(), false, curseCard.getId());
                var path = playCardRequest.toEndpointPath(getEdnpointPlayCard(), munchkinContext.getId());
                var cardAction = new CardAction(path,
                        "Снять проклятие  " + curseCard.getText());
                cardActions.add(cardAction);
            }
        }
        var actions = getMunchkinContext().getActionHandler().getRequiredActions();
        var optAct = actions.stream().filter(action -> action instanceof ActionAcceptCurse).findFirst();
        if (optAct.isPresent()) {
            var curseCard = optAct.get();
            var playCardRequest = new PlayCardRequest(getId(), "null", false, null);
            var path = playCardRequest.toEndpointPath(getEdnpointPlayCard(), munchkinContext.getId());
            var cardAction = new CardAction(path,
                    "Снять текущее проклятие");
            cardActions.add(cardAction);
        }
        return cardActions;
    }
}
