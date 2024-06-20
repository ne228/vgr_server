package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class HammerOfKneecapCard extends WeaponItemCard {

    public HammerOfKneecapCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Коленеотбойный молоточек";
        text = "Только для Дварфов ";
        power = 4;
        cost = 600;
        iconPath = "/images/hammerOfKneecapCard.png";
        setTwoHands(false);
        setBigSize(false);
    }

    @Override
    public boolean canPutItem(Player player) {
        if (player.isRace(RaceList.DWARF))
            return true;
        else
            return false;
    }
}
