package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayClasses;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayRace;
import com.example.ais_ecc.munchkin.service.action.card.items.ActionTakeOffWeapon;
import com.example.ais_ecc.munchkin.service.action.fight.ActionConfirmFightOrder;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public class SwissArmyHalberdCard extends WeaponItemCard {
    ISubscribe subscribe;

    public SwissArmyHalberdCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Швейцарская Армейская Алебарда";
        text = "Применяется только человеком";
        power = 4;
        cost = 600;
        iconPath = "/images/swissArmyHalberdCard.png";
        setTwoHands(true);
        setBigSize(true);
    }

    @Override
    public void accept(Player player) {
        var context = getMunchkinContext();
        var card = this;
        var target_player = context.getCurrentPlayer();
        subscribe = new ISubscribe(ActionPlayRace.createAction()) {
            @Override
            public void update() {
                var action = getAction();
                if (target_player.getRaces().size() >= 0) {
                    var takeOffAction = new ActionTakeOffWeapon(target_player, card);
                    try {
                        context.getActionHandler().doAction(takeOffAction);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        context.getActionHandler().getSubscribeService().register(subscribe);
    }

    @Override
    public void discard(Player player) {
        var context = getMunchkinContext();
        context.getActionHandler().getSubscribeService().unRegister(subscribe);
    }

    @Override
    public boolean canPutItem(Player player) {
        if (player.getRaces().size() == 0)
            return true;
        else
            return false;
    }
}
