package com.example.ais_ecc.munchkin.models.doorCards.clasessCards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.doorCards.DoorCard;
import com.example.ais_ecc.munchkin.payload.request.PlayCardRequest;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.ActionPlaySuperMunchkin;

import java.util.List;

public abstract class SuperMunchkin extends DoorCard {
    public SuperMunchkin(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Суперманчкин";
        text = "Можешь иметь 2 Классовые карты, а в комплекте все радости и горести обоих Классов. Или возьми один Класс, получи все его преимущества и забудь о недостатках (так монстры, ненавидящие Клириков, не получат Бонус в бою против Суперклирика). Эта карта теряется, если ты теряешь все Классы.";
        iconPath = "/images/superMunchkin1.png";
    }


    @Override
    public IAction createAction(PlayCardRequest playCardRequest) throws Exception {
        var player = getMunchkinContext().getPlayerById(playCardRequest.getPlayerId());

        var actionPlaySuperMunchkin = new ActionPlaySuperMunchkin(player, this);
        return actionPlaySuperMunchkin.canAmI(getMunchkinContext()) ? actionPlaySuperMunchkin : null;
    }

    @Override
    public List<CardAction> getActions() throws Exception {
        var res = super.getActions();
        var currentPlayer = getMunchkinContext().getCurrentPlayer();
        var request = new PlayCardRequest(this.getId(), currentPlayer.getId(), false, "null");
        var player = getMunchkinContext().getPlayerById(request.getPlayerId());
        var actionPlaySuperMunchkin = new ActionPlaySuperMunchkin(player, this);
        if (actionPlaySuperMunchkin.canAmI(getMunchkinContext())) {
            var cardAction = new CardAction(request.toEndpointPath("play_card", getMunchkinContext().getId()), actionPlaySuperMunchkin.getName());
            res.add(cardAction);
        }

        return res;
    }
}
