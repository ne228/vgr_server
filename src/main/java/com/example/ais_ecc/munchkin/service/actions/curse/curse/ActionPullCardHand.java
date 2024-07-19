package com.example.ais_ecc.munchkin.service.actions.curse.curse;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.ListExtensions;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionPullCardHand extends RequiredAction {

    Player loserPlayer;
    Player whoPullPlayer;

    public ActionPullCardHand(Player loserPlayer, Player whoPullPlayer, String scopeId, MunchkinContext context) {
        this.loserPlayer = loserPlayer;
        this.whoPullPlayer = whoPullPlayer;

        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Вытянуть карту у игрока " + loserPlayer.getUser().getUsername();
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!whoPullPlayer.getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        var extCard = ListExtensions.extractRandom(loserPlayer.getCards());
        whoPullPlayer.getCards().add(extCard);
        return "Игрок " + whoPullPlayer.getUser().getUsername() + " вытянул карту с руки у " + loserPlayer.getUser().getUsername();
    }
}
