package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.List;
import java.util.Random;

public class ActionFlushingRoll extends IAction {


    private Player currentPlayer;
    private Move move;

    private Fight fight;
    private MunchkinContext context;

    public ActionFlushingRoll() {

    }

    private ActionFlushingRoll(MunchkinContext context) {
        this.path = "flushing_roll/" + context.getId(); //TODO
        this.name = "Flushing roll";
        this.title = "Flushing roll";
    }

    public static ActionFlushingRoll createAction(MunchkinContext context) {
        return new ActionFlushingRoll(context);
    }

    private Flushing getUnrolledFlush(List<Flushing> flushingList) {

        for (var flushing : flushingList) {
            if (flushing.isEnd()) {
                continue;
            }

            if (!flushing.isEndRolling())
                if (flushing.getPlayer().getId().equalsIgnoreCase(currentPlayer.getId())) {
                    return flushing;
                }
        }
        return null;
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


        if (!fight.isLoss())
            return false;


        var flush = fight.getFlushings().stream().findFirst();
        if (flush.isEmpty())
            return false;
        if (flush.get().isEndRolling())
            return false;

        currentPlayer = context.getCurrentPlayer();
        if (!flush.get().getPlayer().getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;


        return true;
    }

    @Override
    public String start() {
        currentPlayer = context.getCurrentPlayer();
        var flushing = fight.getFlushings().stream().findFirst().get();


        if (flushing.getPlayer().getId().equalsIgnoreCase(currentPlayer.getId())) {
            Random random = new Random();
            int roll = random.nextInt(6) + 1;
            roll += currentPlayer.getDefBonusFlushing();
            flushing.setCubeNumber(roll);
            flushing.setEndRolling(true);
//            fight.getFlushings().remove(flushing);
            return "Player " + currentPlayer.getUser().getUsername() + "  has rolled a die with a value of " + roll;
        }


        return "TODO";
    }
}
