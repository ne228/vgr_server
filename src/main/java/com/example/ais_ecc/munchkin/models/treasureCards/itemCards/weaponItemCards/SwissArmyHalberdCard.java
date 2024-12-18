package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class SwissArmyHalberdCard extends WeaponItemCard {


    public SwissArmyHalberdCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Швейцарская Армейская Алебарда";
        text = "Применяется только человеком";
        power = 4;
        cost = 600;
        iconPath = "/images/swissArmyHalberdCard.png";
        setTwoHands(true);
        setBigSize(true);
    }



    @Override
    public boolean canPutItem(Player player) {
        if (player.getRaces().size() == 0)
            return true;
        else
            return false;
    }
}
