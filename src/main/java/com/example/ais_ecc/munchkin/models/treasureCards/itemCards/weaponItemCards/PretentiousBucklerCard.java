package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class PretentiousBucklerCard extends WeaponItemCard {
    public PretentiousBucklerCard (MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Пафосный Баклер";
        text = "";
        cost = 400;
        iconPath = "/images/pretentiousBucklerCard.png";
        power = 2;
        setTwoHands(false);
        setBigSize(false);

    }
    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
