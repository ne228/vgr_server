package com.example.ais_ecc.munchkin.service.actions.curse.curseImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.*;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.IAction;
import com.example.ais_ecc.munchkin.service.actions.card.items.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ActionDropMaxBonusItem extends IAction {
    private final Player player;

    public ActionDropMaxBonusItem(Player player) {
        this.player = player;
    }

    @Override
    public boolean canAmI(MunchkinContext munchkinContext) throws Exception {
        context = munchkinContext;

        return true;
    }

    @Override
    public String start() throws Exception {

        List<ItemCard> itemCardList = new ArrayList<>();

        if (player.getHeadItemCard() != null)
            itemCardList.add(player.getHeadItemCard());

        if (player.getArmorItemCard() != null)
            itemCardList.add(player.getArmorItemCard());

        if (player.getLegsItemCard() != null)
            itemCardList.add(player.getLegsItemCard());

        if (player.getWeaponItemCard_1() != null)
            itemCardList.add(player.getWeaponItemCard_1());

        if (player.getWeaponItemCard_2() != null)
            itemCardList.add(player.getWeaponItemCard_2());

        itemCardList.addAll(player.getBonusItemCards());

        var maxBonusItem = itemCardList.stream().max(Comparator.comparing(ItemCard::getPower)).orElse(null);

        if (maxBonusItem != null) {
            if (maxBonusItem instanceof HeadItemCard)
                context.getActionHandler().doRawAction(new ActionTakeOffHead(player, (HeadItemCard) maxBonusItem));

            if (maxBonusItem instanceof ArmorItemCard)
                context.getActionHandler().doRawAction(new ActionTakeOffArmor(player, (ArmorItemCard) maxBonusItem));

            if (maxBonusItem instanceof LegsItemCard)
                context.getActionHandler().doRawAction(new ActionTakeOffLegs(player, (LegsItemCard) maxBonusItem));

            if (maxBonusItem instanceof WeaponItemCard)
                context.getActionHandler().doRawAction(new ActionTakeOffWeapon(player, (WeaponItemCard) maxBonusItem));

            if (maxBonusItem instanceof BonusItemCard)
                context.getActionHandler().doRawAction(new ActionTakeOffBonus(player, (BonusItemCard) maxBonusItem));

            context.discardCard(maxBonusItem.getId());
            return "Игрок " + player.getUser().getUsername() + " потерял карту " + maxBonusItem.getTitle();
        }

        return "Игрок " + player.getUser().getUsername() + " не имеет шмоток";
    }
}
