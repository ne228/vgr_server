package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;

import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class NapalmStaffCard extends WeaponItemCard {
    public NapalmStaffCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Посох Напалма";
        text = "Только для волшебника";
        power = 5;
        cost = 800;
        iconPath = "/images/napalmStaffCard.png";
        setTwoHands(false);
        setBigSize(false);
    }



    @Override
    public boolean canPutItem(Player player) {
        for(var _class : player.getClasses()){
            if (_class._class == ClassList.WIZARD)
                return true;
        }
        return false;
    }
}
