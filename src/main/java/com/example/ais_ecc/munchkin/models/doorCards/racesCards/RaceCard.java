package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.models.races.Races;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayRace;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class RaceCard extends DoorCard {

    public boolean raceCard = true;

    public RaceCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var card = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
        if (card instanceof RaceCard) {
            var action = new ActionPlayRace(player, (RaceCard) card);
            return action;
        }
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), munchkinContext.getCurrentPlayer().getId(), false, "null");
        var res = new ArrayList<CardAction>();
        var action = createAction(playCardRequest);

        var text = "Play " + this.getRace().getName();
        var path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        var cardAction = new CardAction(path, text);

        try {
            if (action.canAmI(munchkinContext))
                res.add(cardAction);
        } catch (Exception e) {
            // do nothing
        }


        res.addAll(super.getActions());
        return res;
    }

    @JsonIgnore
    public abstract Races getRace();
}
