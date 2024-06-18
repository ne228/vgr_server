package com.example.ais_ecc.munchkin.service.actions;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

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
        card.curseDo(player);
//        card.getMunchkinContext().discardCard(card.getId());
        return player.getUser().getUsername() + " is cursed by card: " + card.getTitle();
    }
}
