package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionDropMaxBonusItem;

public class TrulyObnoxiousCurse extends CurseDoorCard {
    public TrulyObnoxiousCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "В конец мерзкое проклятие!";
        text = "Теряешь шмотку дающюю тебе наибольший бонус";
        this.iconPath = "/images/trulyObnoxiousCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        munchkinContext.getActionHandler().doAction(new ActionDropMaxBonusItem(player));
    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
