package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml;


import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public class TheShoesOfTheMightyPendelCard extends LegsItemCard {
    ISubscribe subscribe;

    public TheShoesOfTheMightyPendelCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Башмаки могучего пендаля";
        text = "+2 бонус";
        cost = 400;
        iconPath = "/images/theShoesOfTheMightyPendelCard.png";
        power = +2;

    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
