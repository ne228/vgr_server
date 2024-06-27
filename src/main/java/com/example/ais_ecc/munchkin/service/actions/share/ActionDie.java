package com.example.ais_ecc.munchkin.service.actions.share;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffArmor;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffHead;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffLegs;
import com.example.ais_ecc.munchkin.service.actions.card.items.ActionTakeOffWeapon;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropAllHand;

import java.util.stream.Collectors;

public class ActionDie extends IAction {

    private Player player;

    public ActionDie(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        var actionTakeOffArmor = new ActionTakeOffArmor(player, player.getArmorItemCard());
        var actionTakeOffHead = new ActionTakeOffHead(player, player.getHeadItemCard());
        var actionTakeOffLegs = new ActionTakeOffLegs(player, player.getLegsItemCard());
        var actionTakeOffWeapon = new ActionTakeOffWeapon(player, player.getWeaponItemCard_1());
        var actionTakeOffWeapon1 = new ActionTakeOffWeapon(player, player.getWeaponItemCard_2());
        var actionDropAllHand = new ActionDropAllHand(player, "1", context);
        context.getActionHandler().doRawAction(actionTakeOffArmor);
        context.getActionHandler().doRawAction(actionTakeOffHead);
        context.getActionHandler().doRawAction(actionTakeOffLegs);
        context.getActionHandler().doRawAction(actionTakeOffWeapon);
        context.getActionHandler().doRawAction(actionTakeOffWeapon1);
        context.getActionHandler().doRawAction(actionDropAllHand);
        player.setGold(0);

        var fight = context.getFight();
        if (fight != null) {
            var flushingsPlayer = fight.getFlushings().stream().filter(flush -> flush.getPlayer().getId().equalsIgnoreCase(player.getId()))
                    .collect(Collectors.toList());
            fight.getFlushings().removeAll(flushingsPlayer);

            if (fight.getFlushings().size() == 0)
                context.getActionHandler()
                        .doAction(new ActionNextMove(context));
        }

        return "Игрок " + player.getUser().getUsername() + " умер";

    }
}
