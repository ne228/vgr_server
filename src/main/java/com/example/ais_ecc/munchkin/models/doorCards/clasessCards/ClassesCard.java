package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayClasses;
import com.example.ais_ecc.munchkin.service.actions.card.ActionTakeOffClass;

import java.util.ArrayList;
import java.util.List;

public abstract class ClassesCard extends DoorCard {

    public boolean classCard = true;
    public ClassList _class;

    public ClassesCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var card = munchkinContext.getPlayerCardById(playCardRequest.getCardId());
        var player = munchkinContext.getPlayerById(playCardRequest.getPlayerId());

        var actionPlayClasses = new ActionPlayClasses(player, this);
        if (actionPlayClasses.canAmI(getMunchkinContext()))
            return actionPlayClasses;

        var actionTakeOffClass = new ActionTakeOffClass(player, this);
        if (actionTakeOffClass.canAmI(getMunchkinContext()))
            return actionTakeOffClass;
        return null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var player = getMunchkinContext().getCurrentPlayer();

        PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), munchkinContext.getCurrentPlayer().getId(), false, "null");
        var res = new ArrayList<CardAction>();
        var actionPlayRace = new ActionPlayClasses(player, this);


        var text = "Сыграть  " + get_class();
        var path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        var cardAction = new CardAction(path, text);

        try {
            if (actionPlayRace.canAmI(munchkinContext))
                res.add(cardAction);
        } catch (Exception e) {
            // do nothing
        }


        text = "Сбросить  " + get_class();
        path = playCardRequest.toEndpointPath(this.getEdnpointPlayCard(), munchkinContext.getId());
        cardAction = new CardAction(path, text);
        var actionTakeOffRace = new ActionTakeOffClass(player, this);
        try {
            if (actionTakeOffRace.canAmI(munchkinContext))
                res.add(cardAction);
        } catch (Exception e) {
            // do nothing
        }

        res.addAll(super.getActions());
        return res;
    }

    public List<IAction> getClassActions() {
        return new ArrayList<>();
    }

    public abstract void accept(Player player);

    public abstract void discard(Player player);


    public ClassList get_class() {
        return _class;
    }

    public void set_class(ClassList _class) {
        this._class = _class;
    }


}
