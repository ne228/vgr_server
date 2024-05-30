package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionNextMove extends IAction {

    private User user;


    public ActionNextMove(MunchkinContext context) {
        this.path = "next_move/" + context.getId();
        this.name = "next_move";
        this.title = "next_move";
        this.color = "success";
    }

    public ActionNextMove(User user) {
        this.user = user;
    }

    public static ActionNextMove createAction(MunchkinContext context) {
        return new ActionNextMove(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        var countMoves = context.getMoves().size();
        var countPlayer = context.getPlayers().size();
        var indexPlayer = (countMoves % countPlayer);
        if (indexPlayer >= countPlayer)
            indexPlayer = 0;
        var playerWhoMove = context.getPlayers().get(indexPlayer);

        context.moves.add(new Move(playerWhoMove, context));
        return "Player " + playerWhoMove.getUser().getUsername() + " now is your move";
    }
}
