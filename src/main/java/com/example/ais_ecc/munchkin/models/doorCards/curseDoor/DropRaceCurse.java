package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionDropAllRaces;

public class DropRaceCurse extends CurseDoorCard {
    public DropRaceCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие! Теряешь расу";
        this.text = "Сбрось все свои Расовые карты, находящиеся в игре. Стань уже человеком.";
        this.iconPath = "/images/dropRaceCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        munchkinContext.getActionHandler().doAction(new ActionDropAllRaces(player));
    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
