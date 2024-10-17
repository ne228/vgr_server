package com.example.ais_ecc.munchkin.models.treasureCards;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionSellCard;

import java.util.List;

public class TreasureCard extends Card {
    public int cost;

    public TreasureCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = super.getActions();
        try {
            if (cost > 0) {
                var currentPlayer = getMunchkinContext().getCurrentPlayer();
                PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), "null", false, "null");
                var action = new ActionSellCard(this, currentPlayer);

                if (action.canAmI(munchkinContext)) {
                    var sellCardAction = new CardAction(playCardRequest.toEndpointPath("sell_card", getMunchkinContext().getId()), "Sell");
                    res.add(sellCardAction);
                }
            }
        } catch (Exception e) {
            return res;
        }
        return res;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

}
