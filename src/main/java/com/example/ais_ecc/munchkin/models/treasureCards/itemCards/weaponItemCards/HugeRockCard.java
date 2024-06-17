package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class HugeRockCard extends WeaponItemCard {
    public HugeRockCard (MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Огромная Каменюга";
        text = "";
        cost = 0;
        iconPath = "/images/hugeRockCard.png";
        power = 3;
        setTwoHands(true);
        setBigSize(true);

    }
    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
