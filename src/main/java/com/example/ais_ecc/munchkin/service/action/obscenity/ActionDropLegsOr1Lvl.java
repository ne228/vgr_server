package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

public class ActionDropLegsOr1Lvl extends IAction {
    Player player;

    public ActionDropLegsOr1Lvl(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return false;
    }

    @Override
    public String start() throws Exception {
        if (player.getLegsItemCard() != null) {
            var card = player.getLegsItemCard();
            card.discard(player);
            player.setLegsItemCard(null);
            player.getCards().add(card);
            context.discardCard(card.getId());
            return "Игрок " + player.getUser().getUsername() + " потерял обувку";
        } else{
            player.lvlUp(-1);
            return "Игрок " + player.getUser().getUsername() + " потерял 1 Уровень";
        }
    }
}
