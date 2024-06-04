package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionLevelUp extends IAction {

    private Player player;
    private MunchkinContext context;

    public ActionLevelUp(MunchkinContext context) {
        this.context = context;
        this.player = context.getCurrentPlayer();
        this.path = "level_up/" + context.getId();
        this.name = "Level Up";
        this.title = "Level Up";
    }

    public static ActionLevelUp createAction(MunchkinContext context) {

        return new ActionLevelUp(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        player = context.getCurrentPlayer();


        if (player.getGold() < 1000)
            return false;

        if (player.getLvl() == 9)
            return false;

        if (context.isAllNotFight())
            return true;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setGold(player.getGold() - 1000);
        player.lvlUp(1);
        return "Player " + player.getUser().getUsername() + " up " + player.getLvl() + " lvl";
    }
}
