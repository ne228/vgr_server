package com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.treasureCards.TreasureCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.ActionCheaterCube;

import java.util.List;

public class CheaterCube extends TreasureCard {
    public CheaterCube(MunchkinContext munchkinContext) {
        super(munchkinContext);

        title = "Читерский кубик";
        text = "Применить после любого твоего " +
                "броска кубика. Поверни кубик " +
                "нужной тебе стороной вверх. " +
                "Одноразовая.";

        iconPath = "/images/cheaterCube.png";
    }

    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var cubeNum = Integer.parseInt(playCardRequest.getPlayerId());
        var act = new ActionCheaterCube(this, cubeNum);

        if (act.canAmI(getMunchkinContext()))
            getMunchkinContext().getActionHandler().doRawAction(act);

        return act;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = super.getActions();

        var act = new ActionCheaterCube(this, 0);
        if (!act.canAmI(getMunchkinContext()))
            return res;

        for (int i = 1; i <= 6; i++) {
            var request = new PlayCardRequest(this.getId(), "" + i, false, "null");
            var action = new CardAction(request.toEndpointPath("play_card", getMunchkinContext().getId()), "Изменить кубик на " + i);
            res.add(action);
        }

        return res;
    }
}
