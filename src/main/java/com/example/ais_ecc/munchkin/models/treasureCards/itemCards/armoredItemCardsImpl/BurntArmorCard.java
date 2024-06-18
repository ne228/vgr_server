package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class BurntArmorCard extends ArmorItemCard {
    public BurntArmorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Паленые доспехи";
        text = "+2 бонус";
        cost = 400;
        power = 2;
        iconPath = "/images/burntArmorCard.png";
    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }


}
