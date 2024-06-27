package com.example.ais_ecc.munchkin.service.actions.classes;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.share.ActionKickDoor;
import com.example.ais_ecc.munchkin.service.actions.IAction;

import java.util.ArrayList;

public class ActionClericResurrection extends IAction {
    Player player;

    public ActionClericResurrection(Player player) {
        this.player = player;
    }

    public ActionClericResurrection() {

    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        if (!context.isYourMove())
            return false;


        var getOpenCardAction = new ArrayList<IAction>();
        getOpenCardAction.add(new ActionKickDoor(player));

        return false;
    }

    @Override
    public String start() throws Exception {
        return null;
    }
}
