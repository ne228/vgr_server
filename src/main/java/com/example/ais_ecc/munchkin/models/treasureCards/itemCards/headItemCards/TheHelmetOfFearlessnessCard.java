package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.HeadItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class TheHelmetOfFearlessnessCard extends HeadItemCard {
    public TheHelmetOfFearlessnessCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Шлем Бесстрашия";
        text = "+1 БОНУС";
        cost = 200;
        power = 1;
        iconPath = "/images/theHelmetOfFearlessnessCard.png";

    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
