package com.example.ais_ecc.munchkin.service.actions.card;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.SuperMunchkin;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionPlaySuperMunchkin extends IAction {
    Player player;
    SuperMunchkin superMunchkin;

    public ActionPlaySuperMunchkin(Player player, SuperMunchkin superMunchkin) {
        this.player = player;
        this.superMunchkin = superMunchkin;

        this.name = "Сыграть  " + superMunchkin.getTitle();
        this.title = "Сыграть  " + superMunchkin.getTitle();
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
                .anyMatch(card -> card.getId().equalsIgnoreCase(superMunchkin.getId()));

        if (!playerHaveTargetCard)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        player.setSuperMunchkinCard(superMunchkin);
        player.getCards().remove(superMunchkin);
        return "Игрок " + player.getUser().getUsername() + " стал суперманчкиным";
    }
}
