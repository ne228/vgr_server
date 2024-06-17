package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.bonusItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.BonusItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class SpikyKnees extends BonusItemCard {

    public SpikyKnees(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Острые коленки";
        text = "+1 БОНУС";
        cost = 200;
        power = 1;
        iconPath = "/images/spikyKnees.png";

    }

    @Override
    public void accept(Player player) {
        super.accept(player);
    }

    @Override
    public void discard(Player player) {
        super.discard(player);
    }
}
