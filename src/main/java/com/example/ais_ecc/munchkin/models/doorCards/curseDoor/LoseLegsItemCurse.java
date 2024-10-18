package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionDropLegs;

public class LoseLegsItemCurse extends LossArmorCurse {
    public LoseLegsItemCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие. Теряешь надетую обувку";
        this.text = "Теряешь надетую обувку";
        this.iconPath = "/images/lossLegsItemCures.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var act = new ActionDropLegs(player);
        getMunchkinContext().getActionHandler().doAction(act);

    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
