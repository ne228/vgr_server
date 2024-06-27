package com.example.ais_ecc.munchkin.models.doorCards.racesCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlayHalfBlood;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlaySuperMunchkin;

import java.util.List;

public abstract class HalfBlood extends DoorCard {
    public HalfBlood(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Полукровка";
        text = "Можешь иметь 2 Расовые карты, а в комплекте все радости и горести обеих Рас. Или возьми одну Расу, получи все ее преимущества и забудь о недостатках так монстры, ненавидящие Эльфов, не получат Бонус в бою против полуэльфа). Эта карта теряется, если ты теряешь все Расы.";

    }


    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var player = getMunchkinContext().getPlayerById(playCardRequest.getPlayerId());

        var actionPlayHalfBlood = new ActionPlayHalfBlood(player, this);
        return actionPlayHalfBlood.canAmI(getMunchkinContext()) ? actionPlayHalfBlood : null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = super.getActions();
        var currentPlayer = getMunchkinContext().getCurrentPlayer();
        var request = new PlayCardRequest(this.getId(), currentPlayer.getId(), false, "null");
        var player = getMunchkinContext().getPlayerById(request.getPlayerId());
        var actionPlayHalfBlood = new ActionPlayHalfBlood(player, this);
        if (actionPlayHalfBlood.canAmI(getMunchkinContext())) {
            var cardAction = new CardAction(request.toEndpointPath("play_card", getMunchkinContext().getId()), actionPlayHalfBlood.getName());
            res.add(cardAction);
        }

        return res;
    }
}
