package com.example.ais_ecc.munchkin.models.doorCards.curseDoor;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionDropClass;
import com.example.ais_ecc.munchkin.service.actions.curse.curseImpl.ActionDropLvl;

import java.util.UUID;

public class DropClassCurse extends CurseDoorCard {
    public DropClassCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "Проклятие! Теряешь класс";
        this.text = "Сбрось Классовую карту, если есть такая. Если у тебя в игре 2 карты Класса. выбери какой Класс сбросить. Если Классов у тебя нет, ты теряешь 1 Уровень.";
        this.iconPath = "/images/dropClassCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var classes = player.getClasses();
        if (classes.size() == 0) {
            munchkinContext.getActionHandler().doAction(new ActionDropLvl(player, 1));
            return;
        }
        var scopeId = UUID.randomUUID().toString();
        for (var _class : classes) {
            var actionDropClass = new ActionDropClass(player, _class, scopeId, getMunchkinContext());
            getMunchkinContext().getActionHandler().addRequiredAction(actionDropClass);
        }
    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
