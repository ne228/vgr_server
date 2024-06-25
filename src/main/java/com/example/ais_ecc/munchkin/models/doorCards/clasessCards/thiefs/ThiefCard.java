package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.thiefs;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionThiefCut;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionThiefSteal;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWarriorRampage;

import java.util.ArrayList;
import java.util.List;

public abstract class ThiefCard extends ClassesCard {
    public ThiefCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Вор";
        text = "Можешь скинуть I карту, чтобы подрезать другого игрока (-2 в бою). Можешь делать это только один раз в отношении одной жертвы в одном бою: если 2 игрока совместно валят монстра, можешь подрезать их обоих." +
                "Кража: Можешь скинуть I карту, чтобы попытаться стырить маленькую шмотку у другого игрока. Брось кубик: 4 и больше удачная кража, иначе тебя лупят, и ты теряешь Уровень.";
        _class = ClassList.THIEF;
    }

    @Override
    public void accept(Player player) {

    }

    @Override
    public void discard(Player player) {

    }

    @Override
    public List<IAction> getClassActions() {
        var res = new ArrayList<IAction>();
        var actionThiefCut = new ActionThiefCut();
        var actionThiefSteal = new ActionThiefSteal();

        res.add(actionThiefCut);
        res.add(actionThiefSteal);
        return res;
    }

}
