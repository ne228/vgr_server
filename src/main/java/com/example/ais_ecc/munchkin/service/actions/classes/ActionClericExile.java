package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

public class ActionClericExile extends IAction {

    Card card1;
    Card card2;
    Card card3;
    Player player;

    Fight fight;


    public ActionClericExile(Card card1, Card card2, Card card3, Player player) {
        this.card1 = card1;
        this.card2 = card2;
        this.card3 = card3;
        this.player = player;
        path = "cleric_exile";
        name = "Клерик: \"Изгнание\"";
    }

    public ActionClericExile() {
        path = "cleric_exile";
        name = "Клерик: \"Изгнание\"";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;
        player = context.getCurrentPlayer();
        fight = context.getFight();
        if (fight == null)
            return false;

        if (fight.getFightPlayers().stream().noneMatch(fighter -> fighter.getId().equalsIgnoreCase(player.getId())))
            return false;

        if (fight.getEnemyCards().stream().noneMatch(EnemyCard::isUnDead))
            return false;

        if (player.getCards().size() < 3)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        context.discardCard(card1.getId());
        context.discardCard(card2.getId());
        context.discardCard(card3.getId());

        fight.addBonusPlayerPower(3);
        return "Игрок " + player.getUser().getUsername() + " применил \"Изгнание\" и получил +3 Бонус в бою";
    }
}
