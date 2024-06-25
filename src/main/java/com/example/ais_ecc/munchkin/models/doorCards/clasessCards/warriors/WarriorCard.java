package com.example.ais_ecc.munchkin.models.doorCards.clasessCards.warriors;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassesCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWarriorRampage;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWizardFly;
import com.example.ais_ecc.munchkin.service.actions.classes.ActionWizardPacification;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionFightEnd;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

import java.util.ArrayList;
import java.util.List;

public abstract class WarriorCard extends ClassesCard {

    ISubscribe subscribe;
    public WarriorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Воин";
        _class = ClassList.WARRIOR;
        text = "Буйство: Можешь скинуть до З карт в бою, каждая даёт тебе +1" +
                "При ничьей в бою ты побеждаешь.";
    }

    @Override
    public void accept(Player player) {
        subscribe = new ISubscribe(new ActionFightEnd(player)) {
            @Override
            public void update() throws Exception {
                var fight = getMunchkinContext().getFight();

                if (fight != null)
                    fight.addBonusPlayerPower(1);
            }
        };
        getMunchkinContext().getActionHandler().getSubscribeService().register(subscribe);
    }

    @Override
    public void discard(Player player) {
        getMunchkinContext().getActionHandler().getSubscribeService().unRegister(subscribe);
    }

    @Override
    public List<IAction> getClassActions() {
        var res = new ArrayList<IAction>();
        var actionWarriorRampage = new ActionWarriorRampage();

        res.add(actionWarriorRampage);
        return res;
    }


}
