package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionDrop1000Gold extends RequiredAction {

    Player player;

    public ActionDrop1000Gold(Player player, String scopeId, MunchkinContext context) {
        this.player = player;
        this.scopeId = scopeId;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Отдать вещей на 1000 Голдов";
        setColor("green");
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();

        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;

        if (player.getGold() < 1000)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {

        player.setGold(player.getGold() - 1000);
        return "Игрок " + player.getUser().getUsername() + " отдал 1000 Голдов Страховому Агенту";

    }
}
