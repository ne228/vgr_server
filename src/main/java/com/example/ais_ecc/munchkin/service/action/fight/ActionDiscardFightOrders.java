package com.example.ais_ecc.munchkin.service.action.fight;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.OrderFight;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;

import java.util.ArrayList;
import java.util.List;

public class ActionDiscardFightOrders extends IAction {

    Fight fight;

    public ActionDiscardFightOrders(Fight fight) {
        this.fight = fight;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
//        fight.getFightOrders().removeAll(fight.getFightPlayers());
        fight.setFightOrders(new ArrayList<>());

        return "Вся помощь в бою аннулирована";
    }
}
