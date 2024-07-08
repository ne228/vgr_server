package com.example.ais_ecc.munchkin.service.actions.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.models.Move;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionPutLegs extends IAction {

    String nameRace;
    private Player player;
    private LegsItemCard legsItemCard;
    private Move move;
    private MunchkinContext context;

    public ActionPutLegs(Player player, LegsItemCard legsItemCard) {
        this.player = player;
        this.legsItemCard = legsItemCard;

        this.path = "Put";
        this.name = "Put";
        this.title = "Put";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getLegsItemCard() != null)
            return false;

        if (!legsItemCard.canPutItem(player))
            return false;

        if (!player.isHaveCard(legsItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setLegsItemCard(legsItemCard);
        legsItemCard.accept(player);
//        context.discardCard(legsItemCard.getId());

        return "Player " + player.getUser().getUsername() + " put on " + legsItemCard.getTitle() ;   //TODO
    }
}
