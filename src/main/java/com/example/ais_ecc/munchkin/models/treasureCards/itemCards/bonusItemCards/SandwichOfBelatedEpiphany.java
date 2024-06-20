package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.bonusItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class SandwichOfBelatedEpiphany extends BonusItemCard {

    public SandwichOfBelatedEpiphany(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Сэндвич запоздалого прозрения с сыром и селедкой";
        text = "+3 БОНУС";
        cost = 400;
        power = 3;
        iconPath = "/images/sandwichOfBelatedEpiphany.png";
    }
    @Override
    public boolean canPutItem(Player player) {
        if (player.isRace(RaceList.HALFING))
            return true;
        else
            return false;
    }
}
