package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionAcceptLossLevel extends RequiredAction {

    private Player player;

    private IAction nextAction;
    private int cubeNum;

    public ActionAcceptLossLevel(Player player, int cubeNum, String scopeId, MunchkinContext context) {
        this.player = player;
        this.scopeId = scopeId;
        this.cubeNum = cubeNum;
        setScopeId(scopeId);
        this.id = getId();
        this.color = "green";
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Потерять " + cubeNum + " уровней";
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
        player.lvlUp(-cubeNum);
        return "Игрок " + player.getUser().getUsername() + " потерял " + cubeNum + " уровней";
    }
}
