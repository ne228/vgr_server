package com.example.ais_ecc.munchkin.models.treasureCards.itemCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionPutWeapon;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffWeapon;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class WeaponItemCard extends ItemCard {
    private boolean TwoHands = false;

    public WeaponItemCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.itemType = "Оружие";
    }

    public boolean isTwoHands() {
        return TwoHands;
    }

    public void setTwoHands(boolean twoHands) {
        TwoHands = twoHands;
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {

        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());

        var actionTakeOff = new ActionTakeOffWeapon(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            return actionTakeOff;
        else {
            var actionPut = new ActionPutWeapon(player, this);
            if (actionPut.canAmI(getMunchkinContext()))
                return actionPut;
        }

        return null;
    }

    @Override
    @JsonIgnore
    public List<CardAction> getActions() throws Exception {
        boolean putted = false;

        List<CardAction> cardActions = new ArrayList<>();
        cardActions.addAll(super.getActions());
        var player = munchkinContext.getPlayerById(munchkinContext.getCurrentPlayer().getId());

        var actionTakeOff = new ActionTakeOffWeapon(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            putted = true;
        else {
            var actionPut = new ActionPutWeapon(player, this);
            if (actionPut.canAmI(getMunchkinContext()))
                putted = false;
            else {
                return cardActions;
            }
        }


        var text = !putted ? "Put" : "Take Off";

        var request = new PlayCardRequest(this.id, munchkinContext.getCurrentPlayer().getId(), false, "null");
        var cardAction = new CardAction(request.toEndpointPath("play_card", munchkinContext.getId()), text);
        cardActions.add(cardAction);

        return cardActions;
    }
}
