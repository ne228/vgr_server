package com.example.ais_ecc.munchkin.models.doorCards.walkingDeadCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionAddWalkingMonster;

import java.util.ArrayList;
import java.util.List;

public abstract class WalkingMonsterCard extends DoorCard {
    public WalkingMonsterCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Бродячая тварь";
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) {
        var action = new ActionAddWalkingMonster(this);
        return action;
    }


    @Override
    public List<CardAction> getActions() throws Exception {
        var res = new ArrayList<CardAction>();
        res.addAll(super.getActions());

        PlayCardRequest playCardRequest = new PlayCardRequest(this.getId(), "null", false, "null");
        if (createAction(playCardRequest).canAmI(getMunchkinContext())) {
            var cardAction = new CardAction(playCardRequest.toEndpointPath("play_card", getMunchkinContext().getId()), "Сыграть");
            res.add(cardAction);
        }

        return res;
    }
}
