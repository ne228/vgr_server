package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionKickDoor extends IAction {

    private Player player;
    private Move move;
    private MunchkinContext context;

    public ActionKickDoor(Player player) {
        this.player = player;
    }

    private ActionKickDoor(MunchkinContext context) {
        this.path = "kick_door/" + context.getId();
        this.name = "Kick Door";
        this.title = "Kick Door";
    }

    public static ActionKickDoor createAction(MunchkinContext context) {
        return new ActionKickDoor(context);
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;
        if (!context.isYourMove())
            return false;

        move = context.getLastMove();

        return move.isKickDoor;
    }

    @Override
    public String start() throws Exception {

        var card = context.getRandomDoorCard();
        if (card instanceof EnemyCard) {
            move.setFight(new Fight(player, context));
            move.getFight().getEnemyCards().add((EnemyCard) card);
            move.endKickDoor();
            return "Player " + player.getUser().getUsername() + " start fight vs " + card.getTitle();
        }

        if (card instanceof CurseDoorCard) {
            var actionCurseDoor = new ActionCurseDoor(true, player, (CurseDoorCard) card);
            context.getActionHandler().doAction(actionCurseDoor);
            move.endKickDoor();
            return "Player " + player.getUser().getUsername() + " is cursed " + card.getTitle();
        }


        player.getCards().add(card);
        move.endKickDoor();
        return "Player " + player.getUser().getUsername() + " give the door card";
    }
}
