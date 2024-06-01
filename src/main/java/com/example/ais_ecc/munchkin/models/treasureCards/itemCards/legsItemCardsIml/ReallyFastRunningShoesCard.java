package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.legsItemCardsIml;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.LegsItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;
import com.example.ais_ecc.munchkin.service.action.ActionKickDoor;
import com.example.ais_ecc.munchkin.service.action.ActionNull;
import com.example.ais_ecc.munchkin.service.observer.ISubscribe;

public class ReallyFastRunningShoesCard extends LegsItemCard {
    ISubscribe subscribe;

    public ReallyFastRunningShoesCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Башмаки реально быстрого бега";
        text = "Даёт +2 к смывке";
        cost = 400;
        iconPath = "/images/reallyFastRunningShoesCard.png";

    }

    @Override
    public void accept(Player player) {
        super.accept(player);
       player.setDefBonusFlushing(player.getDefBonusFlushing() + 2 );
    }

    @Override
    public void discard(Player player) {
        super.discard(player);
        player.setDefBonusFlushing(player.getDefBonusFlushing() - 2 );
    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
