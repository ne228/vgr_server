package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.races.RaceList;
import com.example.ais_ecc.munchkin.models.treasureCards.itemCards.HeadItemCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class HornedHelmetCard extends HeadItemCard {
    public HornedHelmetCard(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Шлем Рогач";
        text = "+1 БОНУС" +
                "(+3 для эльфов)";
        cost = 600;
        power = 1;
        iconPath = "/images/hornedHelmetCard.png";

    }

    @Override
    public void accept(Player player) {
        super.accept(player);
        if (player.isRace(RaceList.ELF))
            power += 2;
    }

    @Override
    public void discard(Player player) {
        super.discard(player);
        if (player.isRace(RaceList.ELF))
            power -= 2;
    }
}
