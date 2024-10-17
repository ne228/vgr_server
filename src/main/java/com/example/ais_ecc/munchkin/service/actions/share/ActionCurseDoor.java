package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.curseDoor.CurseDoorCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.curse.ActionAcceptCurse;

public class ActionCurseDoor extends IAction {

    private Player player;

    private  CurseDoorCard card;
    private boolean can = false;

    public ActionCurseDoor(boolean can, Player player, CurseDoorCard card) {
//        this.path = "Curse/" + context.getId();
        this.name = "Curse door";
        this.title = "Curse door";
        this.color = "Curse door";
        this.can = can;
        this.player = player;
        this.card = card;
    }


    public static ActionConnect createAction(MunchkinContext context) {
        return new ActionConnect(context);
    }


    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        return can;
    }

    @Override
    public String start() throws Exception {
        var act = new ActionAcceptCurse(player, card, context);
        context.getActionHandler().addRequiredAction(act);
        card.curseDo(player);

        return player.getUser().getUsername() + " проклят картой: " + card.getTitle();
    }
}
