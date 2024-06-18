package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.clerics;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionClericExile;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionClericResurrection;

import java.util.ArrayList;
import java.util.List;

public abstract class ClericCard extends ClassesCard {
    public ClericCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Клерик";
        text = "<b>Воскрешение:</b> когда надо вытя" +
                "нуть карту лицом вверх, ты можешь" +
                "вместо этого взять верхнюю карту" +
                "из соответствующей кучи сброса. За" +
                "тем ты должен сбросить одну карту с" +
                "«руки».<br/>" +
                "<strong>Изгнание:</strong> можешь сбросить до З" +
                "карт в бою против Андедов. Каждый" +
                "сброс дает тебе +3 Бонус.";
        _class = ClassList.CLERIC;

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
        var actionClericExile = new ActionClericExile();
        var actionClericResurrection = new ActionClericResurrection();

        res.add(actionClericExile);
        res.add(actionClericResurrection);
        return res;
    }
}
