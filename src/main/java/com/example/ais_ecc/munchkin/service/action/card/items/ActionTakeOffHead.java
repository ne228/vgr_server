package com.example.ais_ecc.munchkin.service.action.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.HeadItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionTakeOffHead extends IAction {


    private Player player;
    private HeadItemCard headItemCard;

    private MunchkinContext context;

    public ActionTakeOffHead(Player player, HeadItemCard headItemCard) {
        this.player = player;
        this.headItemCard = headItemCard;

        this.path = "Take off";
        this.name = "Take off";
        this.title = "Take off";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getHeadItemCard() == null)
            return false;

        if (!player.getHeadItemCard().getId().equalsIgnoreCase(headItemCard.getId()))
            return false;

        if (!player.isHaveCard(headItemCard))
            return false;
        return true;
    }

    @Override
    public String start() throws Exception {

        if (headItemCard == null)
            return "";
        player.setHeadItemCard(null);
        headItemCard.discard(player);
        player.getCards().add(headItemCard);

        return "Player " + player.getUser().getUsername() + " take off " + headItemCard.getTitle();   //TODO
    }
}
