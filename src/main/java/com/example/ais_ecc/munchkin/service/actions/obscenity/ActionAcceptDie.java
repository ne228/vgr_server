package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.share.ActionDie;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionAcceptDie extends RequiredAction {

    private Player player;

    private IAction nextAction;


    public ActionAcceptDie(Player player, String scopeId, MunchkinContext context) {
        this.player = player;
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Принять смерть :(";
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();

        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;
        return true;
    }

    @Override
    public String start() throws Exception {
        var die = new ActionDie(player);
        context.getActionHandler().doAction(die);
        return "Игрок " + player.getUser().getUsername() + " умер от 3872 Орка";
    }
}

