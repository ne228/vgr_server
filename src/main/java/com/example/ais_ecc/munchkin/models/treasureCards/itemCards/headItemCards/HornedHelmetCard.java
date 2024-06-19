package com.example.ais_ecc.munchkin.models.treasureCards.itemCards.headItemCards;

import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.racesCards.RaceList;
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

    Player player;

    @Override
    public int getPower() {
        if (player != null)
            if (player.isRace(RaceList.ELF))
                return power + 2;

        return power;
    }

    @Override
    public void accept(Player player) {
        this.player = player;
        super.accept(player);
    }

    @Override
    public void discard(Player player) {
        this.player = null;
        super.discard(player);
    }
}
