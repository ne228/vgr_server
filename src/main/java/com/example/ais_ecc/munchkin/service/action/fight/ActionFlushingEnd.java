package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionNextMove;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionFlushingEnd extends IAction {


    private Move move;

    private Fight fight;
    private MunchkinContext context;

    public ActionFlushingEnd() {

    }

    private ActionFlushingEnd(MunchkinContext context) {
        this.path = "flushing_end/" + context.getId(); //TODO
        this.name = "Flushing end";
        this.title = "Flushing end";

        try {
            canAmI(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ActionFlushingEnd createAction(MunchkinContext context) {
        return new ActionFlushingEnd(context);
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


        var currentPlayer = context.getCurrentPlayer();
        var flush = fight.getFlushings().stream().findFirst();

        if (flush.isEmpty())
            return false;

        if (!flush.get().isEndRolling())
            return false;

        currentPlayer = context.getCurrentPlayer();
        if (!flush.get().getPlayer().getId().equalsIgnoreCase(currentPlayer.getId()))
            return false;

        var success_text =  flush.get().getCubeNumber();
        this.name = "Закончить смывку от " + flush.get().getEnemyCard().getTitle() + " (кубик - " + success_text + ")";
        this.title = "Закончить смывку от " + flush.get().getEnemyCard().getTitle() + " (кубик - " + success_text + ")";

        return true;
    }

    @Override
    public String start() throws Exception {
        var currentPlayer = context.getCurrentPlayer();
        var flushing = fight.getFlushings().stream().findFirst().get();



        var flushSuccess = false;
        if (flushing.getPlayer().getId().equalsIgnoreCase(currentPlayer.getId())) {
            flushing.getEnemyCard().flushing(flushing.getPlayer());
            if (flushing.getCubeNumber() > flushing.getEnemyCard().getDefaultFlushValue())
                flushSuccess = true;
            else
                flushSuccess = false;
        }

        fight.getFlushings().remove(flushing);
        flushing.endFlushing(false);

        if (fight.getFlushings().size() == 0) {
            move.setEnd(true);
            context.getActionHandler().doAction(new ActionNextMove(context));
        }

        if (flushSuccess) {
            // ПОБЕДА!!!))

            if (fight.getFlushings().size() == 0) {
                move.setEnd(true);
                context.getActionHandler().doAction(new ActionNextMove(context));
            }
            return "Игрок " + currentPlayer.getUser().getUsername() + " смылся от " + flushing.getEnemyCard().getTitle();
        } else {

            flushing.getEnemyCard().obscenity(fight, currentPlayer);

            return "Игрок " + currentPlayer.getUser().getUsername() + " не смылся от " + flushing.getEnemyCard().getTitle() + "." +
                    "Получает непотребство: " + flushing.getEnemyCard().getObscenityText();
        }


    }
}
