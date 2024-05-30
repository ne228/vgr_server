package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionConnect;
import com.example.ais_ecc.munchkin.service.action.card.ActionPlayCurse;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public class BurntArmorCard extends ArmorItemCard {
    public BurntArmorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Паленые доспехи";
        text = "+2 бонус";
        cost = 400;
        power = 2;
        iconPath = "/images/burntArmorCard.png";
    }
    ISubscribe subscribe;
    @Override
    public void accept(Player player) {

    }

    @Override
    public void discard(Player player) {

    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }


}
