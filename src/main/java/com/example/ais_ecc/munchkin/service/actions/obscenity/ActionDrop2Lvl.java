package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionDrop2Lvl extends RequiredAction {

    private final Player player;

    public ActionDrop2Lvl(Player player, String scopeId, MunchkinContext context){
        setScopeId(scopeId);
        this.id = getId();
        this.player = player;
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Потерять 2 уровня";
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
        player.lvlUp(-2);
        return "Игрок " + player.getUser().getUsername() + "потерял 2 уровня";
    }
}
