package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionCleaningNooks extends IAction {
    private Player player;
    private Move move;
    private MunchkinContext context;

    public ActionCleaningNooks(Player player) {
        this.player = player;
    }

    private ActionCleaningNooks(MunchkinContext context) {
        this.path = "cleaning_nooks/" + context.getId();
        this.name = "Чистить нычки";
        this.title = "Чистить нычки";
    }

    public static ActionCleaningNooks createAction(MunchkinContext context) {
        return new ActionCleaningNooks(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;
        player = context.getCurrentPlayer();
        if (!context.isYourMove())
            return false;

        move = context.getLastMove();
        return move.isCleaningNooks();
    }

    @Override
    public String start() throws Exception {
        var card = context.getRandomDoorCard();
        player.getCards().add(card);
        move.endCleaningNooks();

        // TODO
        var nextMove = new ActionNextMove(context);
        context.getActionHandler().doAction(nextMove);

        return "Игрок " + player.getUser().getUsername() + " почистил нычки";
    }
}
