package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;


public class ActionDropArmorAndLegs extends IAction {

    Player player;

    public ActionDropArmorAndLegs(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        if (player.getArmorItemCard() != null) {
            var card = player.getArmorItemCard();
            card.discard(player);
            player.setArmorItemCard(null);
            player.getCards().add(card);
            context.discardCard(card.getId());
        }

        if (player.getLegsItemCard() != null) {
            var card = player.getLegsItemCard();
            card.discard(player);
            player.setLegsItemCard(null);
            player.getCards().add(card);
            context.discardCard(card.getId());
        }

        return "Игрок " + player.getUser().getUsername() + " потерял бронник и обувку";
    }
}
