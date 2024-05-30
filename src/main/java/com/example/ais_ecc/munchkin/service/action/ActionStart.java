package com.example.ais_ecc.munchkin.service.action;

import com.example.ais_ecc.entity.User;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class ActionStart extends IAction {

    private User user;


    public ActionStart(MunchkinContext context) {
        this.path = "start/" + context.getId();
        this.name = "Start";
        this.title = "Start";
        this.color = "success";
    }

    public ActionStart(User user) {
        this.user = user;
    }

    public static ActionStart createAction(MunchkinContext context) {
        return new ActionStart(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isConnected())
            return false;

        if (context.getPlayers().size() < context.getNeedPlayersForStart())
            return false;
//            throw new Exception("Need more then "+ context.get1NeedPlayersForStart() +"players. Current count " + context.getPlayers().size());

        return !context.isGameStarted;
    }

    @Override
    public String start() throws Exception {
//        if (context.isGameStarted) throw new Exception("Game already started");
        try {
            context.getCardInit().initDeckDoor();
            context.getCardInit().initDeckTreasure();
            context.getCardInit().initPlayerDeck();
            var player = context.getPlayers().get(0);
            var move = new Move(player, context);
            context.getMoves().add(move);
            context.isGameStarted = true;

        } catch (Exception exc) {
            throw new Exception("Start game error: " + exc.getMessage());
        }
        return "Game context success start";
    }
}
