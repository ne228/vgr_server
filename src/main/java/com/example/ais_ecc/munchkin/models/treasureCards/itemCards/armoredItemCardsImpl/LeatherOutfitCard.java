package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class LeatherOutfitCard extends ArmorItemCard {
    public LeatherOutfitCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Кожанный прикид";
        text = "+1 бонус";
        cost = 200;
        power = 1;
        iconPath = "/images/leatherOutfitCard.png";
    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }


}
