package com.example.ais_ecc.munchkin.models.doorCards.curseDoorImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curse.ActionDropArmor;

public class LossArmorCurse extends CurseDoorCard {
    public LossArmorCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие";
        this.text = "Теряешь надетый броник";
        this.iconPath = "/images/lossArmorCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var act = new ActionDropArmor(player);
        getMunchkinContext().getActionHandler().doAction(act);

    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
