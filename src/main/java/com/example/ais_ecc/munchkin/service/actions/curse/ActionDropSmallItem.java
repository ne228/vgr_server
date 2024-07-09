package com.example.ais_ecc.munchkin.service.actions.curse;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.RequiredAction;

public class ActionDropSmallItem extends RequiredAction {

    Player player;
    ItemCard itemCard;

    public ActionDropSmallItem(String scopeId, Player player, ItemCard itemCard, MunchkinContext context) {
        setScopeId(scopeId);
        this.id = getId();
        this.itemCard = itemCard;
        this.player = player;
        this.path = "/play_required/" + context.getId() + "?actionId=" + id;
        this.name = "Сбросить " + itemCard.getTitle();
        this.color = "green";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        var currentPlayer = context.getCurrentPlayer();
        if (!currentPlayer.getId().equalsIgnoreCase(player.getId()))
            return false;

        if (!player.isHaveCard(itemCard))
            return false;

        if (itemCard.isBigSize())
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {

        context.discardCard(itemCard.getId());
        return "Игрок " + player.getUser().getUsername() + " сбросил маленькую шмотку";
    }
}
