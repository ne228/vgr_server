package com.example.ais_ecc.munchkin.models.doorCards.bonusDoorCards;

import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class PsychoCard extends BonusDoorCard {
    public PsychoCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        bonusPower = 5;
        bonusTreasure = 1;
        title = "Психованный";
        iconPath = "/images/psycho.png";
        subTitle = "+5 к Уровню Монстра";
        text = "Применять в бою. Если монстр по" +
                "бежден, возьми 1 дополнительное " +
                "Сокровище.";
    }
}
