package com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.bonusTreasureCardsImpl;

import com.example.ais_ecc.munchkin.models.treasureCards.bonusTreasureCards.BonusTreasureCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class PotionIdiotCourage extends BonusTreasureCard {
    public PotionIdiotCourage(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Зелье идиотской храбрости";
        text = "Применить в любом бою. +2 к любой из сторон";
        bonus = 2;
        cost = 100;
        iconPath = "/images/potionIdiotCourage.png";
    }
}
