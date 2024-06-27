package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;
import java.util.List;

public class ActionDropRaceAndClass extends IAction {

    private Player player;

    public ActionDropRaceAndClass(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
       var classes = player.getClasses();
        for (var _class : classes) {
            _class.discard(player);
            player.getCards().add(_class);
            context.discardCard(_class.getId());
        }

        var races = player.getRaces();
        for (var race : races) {
            race.discard(player);
            player.getCards().add(race);
            context.discardCard(race.getId());
        }

        var superMunchkinCard = player.getSuperMunchkinCard();
        player.getCards().add(superMunchkinCard);
        player.setSuperMunchkinCard(null);
        context.discardCard(superMunchkinCard.getId());

        var halfBloodCard = player.getHalfBloodCard();
        player.getCards().add(halfBloodCard);
        player.setHalfBloodCard(null);
        context.discardCard(halfBloodCard.getId());

        return "Игрок " + player.getUser().getUsername() + " сбросил все карты расы и класса";
    }
}
