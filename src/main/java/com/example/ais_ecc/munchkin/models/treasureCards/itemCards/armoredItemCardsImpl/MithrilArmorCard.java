package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.armoredItemCardsImpl;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.clasessCards.ClassList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.ArmorItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class MithrilArmorCard extends ArmorItemCard {
    public MithrilArmorCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Мифриловый доспех";
        text = "+3 бонус" + " не для Волшебников ";
        cost = 600;
        power = 3;
        iconPath = "/images/mithrilArmorCard.png";
        setBigSize(true);
    }

    @Override
    public boolean canPutItem(Player player) {
        for(var _class : player.getClasses()){
            if (_class._class == ClassList.WIZARD)
                return false;
        }
        return true;
    }
}
