package com.example.ais_ecc.munchkin.service.actions.card.items;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.HeadItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionPutHead extends IAction {


    private Player player;
    private HeadItemCard headItemCard;

    private MunchkinContext context;

    public ActionPutHead(Player player, HeadItemCard headItemCard) {
        this.player = player;
        this.headItemCard = headItemCard;

        this.path = "Put";
        this.name = "Put";
        this.title = "Put";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.getHeadItemCard() != null)
            return false;

        if (!headItemCard.canPutItem(player))
            return false;

        if (!player.isHaveCard(headItemCard))
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setHeadItemCard(headItemCard);
        headItemCard.accept(player);
        context.discardCard(headItemCard.getId());

        return "Player " + player.getUser().getUsername() + " put on " + headItemCard.getTitle();   //TODO
    }
}
