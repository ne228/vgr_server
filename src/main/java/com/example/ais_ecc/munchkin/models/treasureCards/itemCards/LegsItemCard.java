package com.example.ais_ecc.munchkin.models.treasureCards.itemCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayRace;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionPutLegs;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffLegs;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class LegsItemCard extends ItemCard {
    public LegsItemCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.itemType = "Обувка";
    }
    ISubscribe subscribe;

    @Override
    public void accept(Player player) {
        var context = getMunchkinContext();
        var card = this;
        var target_player = context.getCurrentPlayer();
        subscribe = new ISubscribe(ActionPlayRace.createAction()) {
            @Override
            public void update() {
                var action = getAction();
                if (card.canPutItem(target_player)) {
                    var takeOffAction = new ActionTakeOffLegs(target_player, card);
                    try {
                        context.getActionHandler().doAction(takeOffAction);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        context.getActionHandler().getSubscribeService().register(subscribe);
    }

    @Override
    public void discard(Player player) {
        var context = getMunchkinContext();
        context.getActionHandler().getSubscribeService().unRegister(subscribe);
    }
    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {

        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());

        var actionTakeOff = new ActionTakeOffLegs(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            return actionTakeOff;
        else {
            var actionPut = new ActionPutLegs(player, this);
            if (actionPut.canAmI(getMunchkinContext()))
                return actionPut;
        }

        return null;
    }

    @Override
    @JsonIgnore
    public List<CardAction> getActions() throws Exception {
        List<CardAction> cardActions = new ArrayList<>();
        cardActions.addAll(super.getActions());
        boolean putted = false;
        var player = munchkinContext.getPlayerById(munchkinContext.getCurrentPlayer().getId());

        var actionTakeOff = new ActionTakeOffLegs(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            putted = true;
        else {
            var actionPut = new ActionPutLegs(player, this);
            if (actionPut.canAmI(getMunchkinContext()))
                putted = false;
            else
                return cardActions;
        }

        var text = !putted ? "Put" : "Take Off";

        var request = new PlayCardRequest(this.id, munchkinContext.getCurrentPlayer().getId(), false, "null");
        var cardAction = new CardAction(request.toEndpointPath("play_card", munchkinContext.getId()), text);
        cardActions.add(cardAction);

        return cardActions;
    }

}
