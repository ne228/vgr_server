package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

//import com.bezkoder.vgr.munchkin.models.Player;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.curse.ActionPlayCurse;

import java.util.ArrayList;
import java.util.List;

public abstract class CurseDoorCard extends DoorCard {

    public CurseDoorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    public abstract void curseDo(Player player) throws Exception;

    public abstract void curseRemove(Player player) throws Exception;


    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());
        var CurseCard = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var action = new ActionPlayCurse(player, this);
        return action;
    }


    @Override
    public List<CardAction> getActions() throws Exception {
        List<CardAction> cardActions = new ArrayList<>();
        cardActions.addAll(super.getActions());
        for (var player : munchkinContext.getPlayers()) {
            PlayCardRequest playCardRequest = new PlayCardRequest(getId(), player.getId(), false, null);
            var path = playCardRequest.toEndpointPath(getEdnpointPlayCard(), munchkinContext.getId());

            if (!createAction(playCardRequest).canAmI(getMunchkinContext()))
                continue;

            CardAction cardAction = new CardAction(path,
                    "Проклянуть  " + player.getUser().getUsername());

            cardActions.add(cardAction);
        }
        return cardActions;
    }

}
