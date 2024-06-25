package com.example.ais_ecc.munchkin.models.doorCards.racesCards.haflings;


import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceCard;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionHalfingRoll;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionHalfingSell;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public abstract class HalfingCard extends RaceCard {

    public HalfingCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Халфинг";
        text = "На каждом ходу можешь продать одну шмотку за двойную цену (цена других шмоток не меняется).\n" +
                "Если твой бросок на Смывку не удался, ты можешь сбросить карту и попробовать ещё раз.";
        this.race = RaceList.HALFING;
    }

    @JsonIgnore
    @Override
    public List<IAction> getRaceActions() throws Exception {
        var res = super.getRaceActions();

        var actionHalfingSell = new ActionHalfingSell();
        if (actionHalfingSell.canAmI(getMunchkinContext()))
            res.add(actionHalfingSell);

        var actionHalfingReRoll = new ActionHalfingRoll();
        if (actionHalfingReRoll.canAmI(getMunchkinContext()))
            res.add(actionHalfingReRoll);

        return res;
    }

    @Override
    public void accept(Player player) {
    }

    @Override
    public void discard(Player player) {

    }

}
