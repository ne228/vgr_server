package com.example.ais_ecc.munchkin.models.treasureCards.itemCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayRace;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionPutArmor;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffArmor;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffBonus;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public abstract class ArmorItemCard extends ItemCard {
    public ArmorItemCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.itemType = "Броник";
    }

    @Override
    public void accept(Player player) {
        var context = getMunchkinContext();
        var card = this;
        var target_player = context.getCurrentPlayer();
        subscribes = new ArrayList<>();
        for (var action : actionSubscribe) {
            var subscribe = new ISubscribe(action) {
                @Override
                public void afterUpdate() {
                    var action = getAction();
                    if (!card.canPutItem(target_player)) {
                        var takeOffAction = new ActionTakeOffArmor(target_player, card);
                        try {
                            context.getActionHandler().doAction(takeOffAction);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            };
            subscribes.add(subscribe);
            context.getActionHandler().getSubscribeService().register(subscribe);
        }
    }

    @Override
    public void discard(Player player) {
        var context = getMunchkinContext();
        for (var sub : subscribes)
            context.getActionHandler().getSubscribeService().unRegister(sub);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {

        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());

        var actionTakeOff = new ActionTakeOffArmor(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            return actionTakeOff;
        else {
            var actionPut = new ActionPutArmor(player, this);
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

        var actionTakeOff = new ActionTakeOffArmor(player, this);

        if (actionTakeOff.canAmI(getMunchkinContext()))
            putted = true;
        else {
            var actionPut = new ActionPutArmor(player, this);
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
