package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayClasses;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassesCard extends DoorCard {

    public boolean classCard = true;

    public ClassesCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var card = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
        if (card instanceof ClassesCard) {
            var action = new ActionPlayClasses(player, (ClassesCard) card);
            return action;
        }
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = new ArrayList<CardAction>();


        PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), munchkinContext.getCurrentPlayer().getId(), false, "null");
        var path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        var text = "Play " + this.getClasses().getName();
        var cardAction = new CardAction(path, text);

        var action = createAction(playCardRequest);
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
    public abstract Classes getClasses();
}
