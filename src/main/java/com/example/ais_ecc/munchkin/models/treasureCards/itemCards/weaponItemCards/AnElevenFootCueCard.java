package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class AnElevenFootCueCard extends WeaponItemCard {
    public AnElevenFootCueCard (MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Одиннадцатифутовый Rий";
        text = "";
        cost = 200;
        iconPath = "/images/anElevenFootCueCard.png";
        power = 1;
        setTwoHands(true);
        setBigSize(false);

    }
    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
