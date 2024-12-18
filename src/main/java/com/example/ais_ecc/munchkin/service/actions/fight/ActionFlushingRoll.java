package com.example.ais_ecc.munchkin.service.actions.fight;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Flushing;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.IRollAction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;
import java.util.Random;

public class ActionFlushingRoll extends IAction implements IRollAction {


    Flushing flushing;
    private Player currentPlayer;
    private Move move;
    private Fight fight;
    private MunchkinContext context;

    public ActionFlushingRoll() {

    }

    private ActionFlushingRoll(MunchkinContext context) throws Exception {
        this.path = "flushing_roll/" + context.getId(); //TODO
        this.name = "Flushing roll";
        this.title = "Flushing roll";
        canAmI(context);
    }

    public static ActionFlushingRoll createAction(MunchkinContext context) throws Exception {
        return new ActionFlushingRoll(context);
    }

    private Flushing getUnrolledFlush(List<Flushing> flushingList) {

        for (var flushing : flushingList) {
            if (flushing.isEnd()) {
                continue;
            }

            if (!flushing.isEndRolling())
                if (flushing.getPlayer().getId().equalsIgnoreCase(currentPlayer.getId())) {
                    this.name = "Кинуть кубик на смывку от " + flushing.getEnemyCard().getTitle();
                    this.title = "Кинуть кубик на смывку от " + flushing.getEnemyCard().getTitle();
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


        this.name = "Бросить кубик на смывку от " + flush.get().getEnemyCard().getTitle();
        this.title = "Бросить кубик на смывку от " + flush.get().getEnemyCard().getTitle();

        return true;
    }

    @Override
    public String start() {
        currentPlayer = context.getCurrentPlayer();
        flushing = fight.getFlushings().stream().findFirst().get();


        if (flushing.getPlayer().getId().equalsIgnoreCase(currentPlayer.getId())) {
            Random random = new Random();
            int roll = random.nextInt(6) + 1;
            roll += currentPlayer.getDefBonusFlushing();
            flushing.setCubeNumber(roll);
            flushing.setEndRolling(true);

            return "Игрок " + currentPlayer.getUser().getUsername() + " бросил кубинк на смывку от " + flushing.getEnemyCard().getTitle() +
                    " на значение: " + roll;
        }


        return "TODO";
    }

    @Override
    public void setRoll(int rollNum) {
        flushing.setCubeNumber(rollNum);
    }

    @JsonIgnore
    @Override
    public int getRoll() {
        return flushing.getCubeNumber();
    }
}
