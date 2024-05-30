package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.races.ElfRace;
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
        if (player.isRace(new ElfRace()))
            power += 2;
    }

    @Override
    public void discard(Player player) {
        if (player.isRace(new ElfRace()))
            power -= 2;
    }
}
