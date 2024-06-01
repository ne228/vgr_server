package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.classes.ClassList;
import com.example.ais_ecc.munchkin.models.classes.Classes;
import com.example.ais_ecc.munchkin.models.classes.ThiefClass;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class SwordSongDance extends WeaponItemCard {
    public SwordSongDance(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Меч Песни и Пляски";
        text = "Не применяется вором";
        power = 2;
        cost = 400;
        iconPath = "/images/swordSongDance.png";
        setTwoHands(false);
        setBigSize(false);
    }



    @Override
    public boolean canPutItem(Player player) {
        for(Classes _class : player.getClasses()){
            if (_class._class == ClassList.THIEF)
                return false;
        }
        return true;
    }
}
