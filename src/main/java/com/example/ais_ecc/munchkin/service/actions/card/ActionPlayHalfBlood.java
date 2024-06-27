package com.example.ais_ecc.munchkin.service.actions.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.SuperMunchkin;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.HalfBlood;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionPlayHalfBlood extends IAction {
    Player player;
    HalfBlood halfBlood;

    public ActionPlayHalfBlood(Player player, HalfBlood halfBlood) {
        this.player = player;
        this.halfBlood = halfBlood;

        this.name = "Сыграть  " + halfBlood.getTitle();
        this.title = "Сыграть  " + halfBlood.getTitle();
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isAllNotFight())
            return false;

        if (player.isSuperMunchkin())
            return false;

        var player = context.getCurrentPlayer();
        var playerHaveTargetCard = player
                .getCards().stream()
                .anyMatch(card -> card.getId().equalsIgnoreCase(halfBlood.getId()));

        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setHalfBloodCard(halfBlood);
        player.getCards().remove(halfBlood);
        return "Игрок " + player.getUser().getUsername() + " стал полукровкой";
    }
}
