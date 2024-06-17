package com.example.ais_ecc.munchkin.service.action.obscenity;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.IAction;
import com.example.ais_ecc.munchkin.service.action.card.items.*;

import java.util.stream.Collectors;

public class ActionDropAllBigItems extends IAction {

    private Player player;

    public ActionDropAllBigItems(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;
        return true;
    }

    @Override
    public String start() throws Exception {
        if (player.getHeadItemCard() != null)
            if (player.getHeadItemCard().isBigSize()) {
                context.getActionHandler()
                        .doRawAction(new ActionTakeOffHead(player, player.getHeadItemCard()));
                context.discardCard(player.getHeadItemCard().getId());
            }
        if (player.getArmorItemCard() != null)
            if (player.getArmorItemCard().isBigSize()) {
                context.getActionHandler()
                        .doRawAction(new ActionTakeOffArmor(player, player.getArmorItemCard()));
                context.discardCard(player.getArmorItemCard().getId());
            }
        if (player.getLegsItemCard() != null)
            if (player.getLegsItemCard().isBigSize()) {
                context.getActionHandler()
                        .doRawAction(new ActionTakeOffLegs(player, player.getLegsItemCard()));
                context.discardCard(player.getLegsItemCard().getId());
            }
        if (player.getWeaponItemCard_1() != null)
            if (player.getWeaponItemCard_1().isBigSize()) {
                context.getActionHandler()
                        .doRawAction(new ActionTakeOffWeapon(player, player.getWeaponItemCard_1()));
                context.discardCard(player.getWeaponItemCard_1().getId());
            }

        if (player.getWeaponItemCard_2() != null)
            if (player.getWeaponItemCard_2().isBigSize()) {
                context.getActionHandler()
                        .doRawAction(new ActionTakeOffWeapon(player, player.getWeaponItemCard_2()));
                context.discardCard(player.getWeaponItemCard_2().getId());
            }

        var bigBonusItemCards = player.getBonusItemCards()
                .stream().filter(card -> card.isBigSize()).collect(Collectors.toList());

        for (var bigBonusItemCard : bigBonusItemCards) {
            context.getActionHandler()
                    .doRawAction(new ActionTakeOffBonus(player, bigBonusItemCard));
            context.discardCard(bigBonusItemCard.getId());
        }

        return "Игрок " + player.getUser().getUsername() + " сбросил все свои болшие шмотки";
    }
}
