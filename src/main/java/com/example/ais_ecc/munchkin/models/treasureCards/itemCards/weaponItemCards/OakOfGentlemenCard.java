package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Gender;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class OakOfGentlemenCard extends WeaponItemCard {
    public OakOfGentlemenCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Дуб Джентельменов";
        text = "Только для мужщин";
        power = 3;
        cost = 400;
        iconPath = "/images/oakOfGentlemenCard.png";
        setTwoHands(false);
        setBigSize(false);
    }



    @Override
    public boolean canPutItem(Player player) {
        if (player.getGender() == Gender.MAN)
            return true;
        else return false;
    }
}