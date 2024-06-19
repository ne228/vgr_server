package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;

public class ActionClericExile extends IAction {

    ArrayList<Card> cards;
    Player player;
    Fight fight;


    public ActionClericExile(ArrayList<Card> cards, Player player) {
        this.cards = cards;
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

        if (fight.getFightCounter().getClericExile() <= 0)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        for (var card : cards) {
            if (fight.getFightCounter().getClericExile() <= 0)
                break;

            context.discardCard(card.getId());
            fight.addBonusPlayerPower(3);
            fight.getFightCounter().setClericExile(fight.getFightCounter().getClericExile() - 1);
        }

        return "Игрок " + player.getUser().getUsername() + " применил \"Изгнание\" и получил Бонус в бою";
    }
}
