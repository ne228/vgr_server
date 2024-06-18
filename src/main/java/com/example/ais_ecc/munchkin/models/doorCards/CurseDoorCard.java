package com.example.ais_ecc.munchkin.models.doorCards;

//import com.bezkoder.vgr.munchkin.models.Player;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayCurse;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;
import java.util.List;

public abstract class CurseDoorCard extends DoorCard {

    public CurseDoorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    public abstract void curseDo(Player player);



    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
        var CurseCard = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        ActionPlayCurse action = new ActionPlayCurse(player, this);
        return action;

    }

    @Override
    public List<CardAction> getActions() throws Exception {
        List<CardAction> cardActions = new ArrayList<>();
        cardActions.addAll(super.getActions());
        for (var player : munchkinContext.getPlayers()) {
            PlayCardRequest playCardRequest = new PlayCardRequest(getId(), player.getId(), false, null);
            var path = playCardRequest.toEndpointPath(getEdnpointPlayCard(), munchkinContext.getId());
            CardAction cardAction = new CardAction(path,
                    "Curse player: " + player.getUser().getUsername());
            cardActions.add(cardAction);
        }
        return cardActions;
    }

}
