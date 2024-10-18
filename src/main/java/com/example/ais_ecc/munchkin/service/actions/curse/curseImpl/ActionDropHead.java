package com.example.ais_ecc.munchkin.service.actions.curse.curseImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionDropHead extends IAction {
    Player player;

    public ActionDropHead(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {

        if (player.getHeadItemCard() != null) {
            var card = player.getHeadItemCard();
            card.discard(player);
            player.setHeadItemCard(null);
            player.getCards().add(card);
            context.discardCard(card.getId());
        }

        return "Игрок " + player.getUser().getUsername() + " потерял головняк";
    }
}
