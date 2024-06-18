package com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayBonusTreasureCard;

import java.util.List;

public class BonusTreasureCard extends TreasureCard {

    public int bonus = 0;
    public BonusTreasureCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {

        var action = new ActionPlayBonusTreasureCard(this, playCardRequest.isHarm());

        return action;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = super.getActions();

        var action = new ActionPlayBonusTreasureCard(this, true);
        if (action.canAmI(getMunchkinContext())) {
            var requestNotHarm = new PlayCardRequest(this.getId(), "null", false, "null");
            var requestIsHarm = new PlayCardRequest(this.getId(), "null", true, "null");

            var notHarmAction = new CardAction(requestNotHarm.toEndpointPath("play_card", getMunchkinContext().getId()), "Help fight");
            var harmAction = new CardAction(requestIsHarm.toEndpointPath("play_card", getMunchkinContext().getId()), "Harm fight");
            res.add(notHarmAction);
            res.add(harmAction);
        }

        return res;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }
}
