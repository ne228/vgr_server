package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Card;
import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;

public class ActionWarriorRampage extends IAction {
    ArrayList<Card> cards;
    Player player;
    Fight fight;


    public ActionWarriorRampage(ArrayList<Card> cards, Player player) {
        this.cards = cards;
        this.player = player;
        path = "warrior_rampage";
        name = "Воин: \"Буйство\"";
    }

    public ActionWarriorRampage() {
        path = "warrior_rampage";
        name = "Воин: \"Буйство\"";
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {

        context = munchkinContext;
        player = context.getCurrentPlayer();

        if (!player.isClass(ClassList.WARRIOR))
            return false;

        fight = context.getFight();
        if (fight == null)
            return false;

        if (fight.getFightPlayers().stream().noneMatch(fighter -> fighter.getId().equalsIgnoreCase(player.getId())))
            return false;

        if (fight.getFightCounter().getWarriorRampage() <= 0)
            return false;

        return true;
    }

    @Override
    public String start() throws Exception {
        for (var card : cards) {
            if (fight.getFightCounter().getWarriorRampage() <= 0)
                break;

            context.discardCard(card.getId());
            fight.addBonusPlayerPower(1);
            fight.getFightCounter().setWarriorRampage(fight.getFightCounter().getWarriorRampage() - 1);
        }

        return "Игрок " + player.getUser().getUsername() + " применил \"Буйство\" и получил Бонус в бою";
    }
}
