package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.wizards;

import com.example.ais_ecc.munchkin.models.CardAction;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionClericExile;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionClericResurrection;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWizardFly;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWizardPacification;

import java.util.ArrayList;
import java.util.List;

public abstract class WizardCard extends ClassesCard {
    public WizardCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Wizard";
        _class = ClassList.WIZARD;
        text ="Заклинание Полёта: Можешь " +
                "сбросить до З карт после броска на " +
                "Смывку; каждая даёт +l к Смывке." +
                "Заклинание Усмирения: Можешь " +
                "сбросить всю ”руку” (мин. З карты), " +
                "чтобы усмирить одного монстра и " +
                "не драться с ним; ты получаешь " +
                "только его Сокровища, " +
                "но не Уровень.";

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
        var actionWizardFly = new ActionWizardFly();
        var actionWizardPacification = new ActionWizardPacification();

        res.add(actionWizardFly);
        res.add(actionWizardPacification);
        return res;
    }
}
