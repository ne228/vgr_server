package com.example.ais_ecc.munchkin.service.actions.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;


public class ActionPlayCurse extends IAction {

    private Player player;
    private CurseDoorCard curseDoorCard;
    private Move move;
    private MunchkinContext context;

    public ActionPlayCurse(Player player, CurseDoorCard curseDoorCard) {
        this.player = player;
        this.curseDoorCard = curseDoorCard;
    }

    private ActionPlayCurse() {
        this.path = "playCurse";
        this.name = "Play Curse";
        this.title = "Play Curse";
    }

    public static ActionPlayCurse createAction() {
        return new ActionPlayCurse();
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        if (!context.isGameStarted)
            return false;

        var player = context.getCurrentPlayer();
        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(curseDoorCard.getId()));
        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        curseDoorCard.curseDo(player);
        context.discardCard(curseDoorCard.getId());
        return player.getUser().getUsername() + " is cursed by card: " + curseDoorCard.getTitle();
    }
}
