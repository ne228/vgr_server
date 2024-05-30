package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.weaponItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.WeaponItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class CharmingDudaCard extends WeaponItemCard {
    public CharmingDudaCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Чаруяющая дуда";
        text = "Дает тебе +1 к попытке Смыться.";
        cost = 300;
        iconPath = "/images/charmingDudaCard.png";
        setTwoHands(false);
        setBigSize(true);
    }

    @Override
    public void accept(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() + 1);
    }

    @Override
    public void discard(Player player) {
        player.setDefBonusFlushing(player.getDefBonusFlushing() - 1);
    }

    @Override
    public boolean canPutItem(Player player) {
        return true;
    }
}
