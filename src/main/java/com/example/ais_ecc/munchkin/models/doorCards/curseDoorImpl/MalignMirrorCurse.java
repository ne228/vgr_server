package com.example.ais_ecc.munchkin.models.doorCards.curseDoorImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.items.*;
import com.example.ais_ecc.munchkin.service.actions.fight.ActionFightEnd;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

import java.util.ArrayList;
import java.util.List;

public class MalignMirrorCurse extends CurseDoorCard {
    List<ISubscribe> subscribes;

    ISubscribe afterFightSubscribe;

    public MalignMirrorCurse(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "ПРОКЛЯТИЕ! КРИВЯЩЕЕ ЗЕРКАЛО";
        text = "Ты проклят! В твоём следующем бою (только в нем) ты не можешк получить бонусы от шмоток кроме Броника. Если ты используешь Хотельное Кольцо до своего следующ его боя, Проклятие спадёт.";
        this.iconPath = "/images/malignMirrorCurse.png";
    }

    @Override
    public void curseDo(Player player) throws Exception {
        var card = this;
        var actionSubscribe = new ArrayList<IAction>();
        actionSubscribe.add(new ActionPutHead(null, null));
        actionSubscribe.add(new ActionPutLegs(null, null));
        actionSubscribe.add(new ActionPutWeapon(null, null));
        actionSubscribe.add(new ActionPutBonus(null, null));
        subscribes = new ArrayList<>();

        for (var action : actionSubscribe) {
            var subscribe = new ISubscribe(action) {
                @Override
                public void afterUpdate() throws Exception {
                    var action = getAction();
                    if (player.getHeadItemCard() != null)
                        getMunchkinContext().getActionHandler().doRawAction(new ActionTakeOffHead(player, player.getHeadItemCard()));
                    if (player.getLegsItemCard() != null)
                        getMunchkinContext().getActionHandler().doRawAction(new ActionTakeOffLegs(player, player.getLegsItemCard()));
                    if (player.getWeaponItemCard_1() != null)
                        getMunchkinContext().getActionHandler().doRawAction(new ActionTakeOffWeapon(player, player.getWeaponItemCard_1()));

                    if (player.getWeaponItemCard_2() != null)
                        getMunchkinContext().getActionHandler().doRawAction(new ActionTakeOffWeapon(player, player.getWeaponItemCard_2()));

                    for (var bonusItemCard : player.getBonusItemCards())
                        getMunchkinContext().getActionHandler().doRawAction(new ActionTakeOffBonus(player, bonusItemCard));

                }
            };
            subscribes.add(subscribe);
            getMunchkinContext().getActionHandler().getSubscribeService().register(subscribe);
        }

        var action = new ActionFightEnd();
        afterFightSubscribe = new ISubscribe(action) {
            @Override
            public void afterUpdate() throws Exception {
                var fightEndAction = (ActionFightEnd) getAction();
                fightEndAction.canAmI(getMunchkinContext());
                var fight = fightEndAction.getFight();
                if (fight.getFightPlayers().stream().anyMatch(fightPlayer -> fightPlayer.getId().equalsIgnoreCase(player.getId()))) {
                    card.curseRemove(player);
                }
            }
        };

        getMunchkinContext().getActionHandler().getSubscribeService().register(afterFightSubscribe);

        player.getCurses().add(this);
    }

    @Override
    public void curseRemove(Player player) throws Exception {
        var context = getMunchkinContext();
        for (int i = 0; i < subscribes.size(); i++) {
            ISubscribe sub = subscribes.get(i);
            context.getActionHandler().getSubscribeService().unRegister(sub);
        }
        context.getActionHandler().getSubscribeService().unRegister(afterFightSubscribe);
        player.getCurses().remove(this);


    }
}
