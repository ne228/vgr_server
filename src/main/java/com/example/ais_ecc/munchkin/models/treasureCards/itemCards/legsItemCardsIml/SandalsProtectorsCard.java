package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.share.ActionKickDoor;
import com.example.ais_ecc.munchkin.service.actions.share.ActionNull;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public class SandalsProtectorsCard extends LegsItemCard {

    ISubscribe subscribe;
    public SandalsProtectorsCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Сандали-Протекторы";
        text = "Карты Проклятия, входящие в игру" +
                "при вынесении дверей, не имеют" +
                "силы (Проклятия, наложенные дру" +
                "тими игроками, действуют на вас).";
        cost = 700;
        iconPath = "/images/sandalsProtectorsCard.png";

    }

    @Override
    public void accept(Player player) {
        super.accept(player);
        // TODO
        var context = getMunchkinContext();
        subscribe = new ISubscribe(ActionKickDoor.createAction(context)) {
            @Override
            public void update() {
                var action = getAction();
                action = new ActionNull(title + "отменил действие проклятия");
            }
        };

        context.getActionHandler().getSubscribeService().register(subscribe);
    }

    @Override
    public void discard(Player player) {
        super.discard(player);
        var context = getMunchkinContext();
        context.getActionHandler().getSubscribeService().unRegister(subscribe);
    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
