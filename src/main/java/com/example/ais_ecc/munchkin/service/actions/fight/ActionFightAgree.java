package com.example.ais_ecc.munchkin.service.actions.fight;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionFightAgree extends IAction {


    private Player player;
    private Move move;

    private Fight fight;
    private MunchkinContext context;

    public ActionFightAgree(Player player) {
        this.player = player;
    }

    private ActionFightAgree(MunchkinContext munchkinContext) {
        this.path = "agree_fight/" + munchkinContext.getId();
        this.name = "Fight Agree";
    }

    public static ActionFightAgree createAction(MunchkinContext munchkinContext) {
        return new ActionFightAgree(munchkinContext);
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isGameStarted)
            return false;

        move = context.getLastMove();
        if (move == null)
            return false;

        fight = move.getFight();
        if (fight == null)
            return false;

        if (fight.isEnd())
            return false;

        if (fight.isLoss())
            return false;

        var agree = context.getLastMove().getFight().getAgree();
        if (agree) {
            this.name = "Disagree fight";
            color = "red";
        } else {
            this.name = "Agree fight";
            color = "blue";
        }

        return true;
    }

    @Override
    public String start() {
        var isAgree = fight.getAgree(player);

        fight.setAgree(player, !isAgree);
        var agreeStr = !isAgree ? "is agree" : "is disagree";
        return player.getUser().getUsername() + " " + agreeStr + " with this fight ";
    }
}
