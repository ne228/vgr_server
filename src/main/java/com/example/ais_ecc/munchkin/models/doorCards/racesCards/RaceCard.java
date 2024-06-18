package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayRace;
import com.example.ais_ecc.munchkin.service.action.card.ActionTakeOffRace;

import java.util.ArrayList;
import java.util.List;

public abstract class RaceCard extends DoorCard {

    public boolean raceCard = true;
    public RaceList race;

    public RaceCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var card = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());

        var actionPlayRace = new ActionPlayRace(player, this);
        if (actionPlayRace.canAmI(getMunchkinContext()))
            return actionPlayRace;

        var actionTakeOffRace = new ActionTakeOffRace(player, this);
        if (actionTakeOffRace.canAmI(getMunchkinContext()))
            return actionTakeOffRace;
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var player = getMunchkinContext().getCurrentPlayer();

        PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), munchkinContext.getCurrentPlayer().getId(), false, "null");
        var res = new ArrayList<CardAction>();
        var actionPlayRace = new ActionPlayRace(player, this);


        var text = "Сыграть  " + race;
        var path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        var cardAction = new CardAction(path, text);

        try {
            if (actionPlayRace.canAmI(munchkinContext))
                res.add(cardAction);
        } catch (Exception e) {
            // do nothing
        }


        text = "Сбросить  " + race;
        path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        cardAction = new CardAction(path, text);
        var actionTakeOffRace = new ActionTakeOffRace(player, this);
        try {
            if (actionTakeOffRace.canAmI(munchkinContext))
                res.add(cardAction);
        } catch (Exception e) {
            // do nothing
        }


        res.addAll(super.getActions());
        return res;
    }

    public abstract void accept(Player player);

    public abstract void discard(Player player);

    public boolean isRaceCard() {
        return raceCard;
    }

    public void setRaceCard(boolean raceCard) {
        this.raceCard = raceCard;
    }

    public RaceList getRace() {
        return race;
    }

    public void setRace(RaceList race) {
        this.race = race;
    }
}
