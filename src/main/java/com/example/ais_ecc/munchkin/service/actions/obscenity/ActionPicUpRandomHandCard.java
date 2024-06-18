package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.ListExtensions;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionPicUpRandomHandCard extends RequiredAction {

    Player playerTarget;
    Player whoPickUpCardPlayer;

    public ActionPicUpRandomHandCard(String scopeId, Player playerTarget, Player whoPickUpCardPlayer, MunchkinContext context) {
        this.id = getId();
        setScopeId(scopeId);
        this.playerTarget = playerTarget;
        this.whoPickUpCardPlayer = whoPickUpCardPlayer;
        this.path = "/play_required/" +context.getId() + "?actionId=" + id;
        this.name = "Взять случайную карту с руки " + playerTarget.getUser().getUsername();
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!whoPickUpCardPlayer.getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        if (playerTarget.getCards().size() == 0)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        var extCard = ListExtensions.extractRandom(playerTarget.getCards());
        whoPickUpCardPlayer.getCards().add(extCard);

        return "Игрок " + whoPickUpCardPlayer.getUser() + "взял карту из руки у игрока " + playerTarget.getUser().getUsername();
    }
}
