package com.example.ais_ecc.munchkin.service.actions.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionTakeOffLegs extends IAction {


    private Player player;
    private LegsItemCard legsItemCard;

    private MunchkinContext context;

    public ActionTakeOffLegs(Player player, LegsItemCard legsItemCard) {
        this.player = player;
        this.legsItemCard = legsItemCard;

        this.path = "Take off";
        this.name = "Take off";
        this.title = "Take off";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getLegsItemCard() == null)
            return false;

        if (!player.getLegsItemCard().getId().equalsIgnoreCase(legsItemCard.getId()))
            return false;

        if (!player.isHaveCard(legsItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        if (legsItemCard == null)
            return "";
        player.setLegsItemCard(null);
        legsItemCard.discard(player);
        player.getCards().add(legsItemCard);

        return "Player " + player.getUser().getUsername() + " take off " + legsItemCard.getTitle();   //TODO
    }
}
