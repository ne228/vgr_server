package com.example.ais_ecc.munchkin.models.doorCards.curseDoorImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.CurseDoorCard;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.actions.curse.ActionDropSmallItem;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionDropItemCard;
import com.example.ais_ecc.munchkin.service.actions.obscenity.ActionTakeOffAllItems;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class Lose1SmallItem extends CurseDoorCard {
    public Lose1SmallItem(MunchkinContext munchkinContext) {
        super(munchkinContext);
        this.title = "ПРОКЛЯТИЕ! " +
                "НЕВЕЛИКА ПОТЕРЯ";
        this.text = "Выбери и сбрось маленькую шмотку. Маленькая - это любая шмотка, на которой не написано, что она Большая.";
        this.iconPath = "/images/lose1SmallItem.png";

    }

    @Override
    public void curseDo(Player player) throws Exception {
        var takeOffAction = new ActionTakeOffAllItems(player);
        getMunchkinContext().getActionHandler().doAction(takeOffAction);


        var cards = player.getCards().stream().filter(card -> card instanceof ItemCard).collect(Collectors.toList());
        var itemCards = new ArrayList<ItemCard>();
        for (var card : cards) {
            var itemCard = (ItemCard) card;
            if (!itemCard.isBigSize())
                itemCards.add(itemCard);
        }
        var scopeId = UUID.randomUUID().toString();
        for (var itemCard : itemCards) {
            var actionDropSmallItem = new ActionDropSmallItem(scopeId, player, itemCard, getMunchkinContext());
            getMunchkinContext().getActionHandler().addRequiredAction(actionDropSmallItem);
        }

    }

    @Override
    public void curseRemove(Player player) throws Exception {

    }
}
