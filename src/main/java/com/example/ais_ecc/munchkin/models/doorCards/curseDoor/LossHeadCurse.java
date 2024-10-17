package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curse.ActionDropHead;

public class LossHeadCurse extends CurseDoorCard {
    public LossHeadCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие. Теряешь надетый головняк";
        this.text = "Теряешь надетый головняк";
        this.iconPath = "/images/lossHeadCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var act = new ActionDropHead(player);
        getMunchkinContext().getActionHandler().doAction(act);

    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
