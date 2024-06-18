package com.example.ais_ecc.munchkin.service.actions.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;

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
        player.setClasses(new ArrayList<>());
        player.setRaces(new ArrayList<>());
        player.setSuperMunchkin(false);
        player.setHalfBreed(false);
        return "Игрок " + player.getUser().getUsername() + " сбросил все карты расы и класса";
    }
}
