package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class MaceOfSpikinessCard extends WeaponItemCard {
    public MaceOfSpikinessCard (MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Булава Остроконечности";
        text = "Только для Клириков";
        cost = 600;
        iconPath = "/images/maceOfSpikinessCard.png";
        power = 4;
        setTwoHands(false);
        setBigSize(false);
    }

    @Override
    public boolean canPutItem(Player player) {
        for(var _class : player.getClasses()){
            if (_class._class == ClassList.CLERIC)
                return true;
        }
        return false;
    }
}
