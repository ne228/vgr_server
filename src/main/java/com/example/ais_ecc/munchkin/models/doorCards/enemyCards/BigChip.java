package com.example.ais_ecc.munchkin.models.doorCards.enemyCards;

import com.example.ais_ecc.munchkin.models.Fight;
import com.example.ais_ecc.munchkin.models.Player;
import com.example.ais_ecc.munchkin.models.doorCards.EnemyCard;
import com.example.ais_ecc.munchkin.service.MunchkinContext;

public class BigChip extends EnemyCard {
    public BigChip(MunchkinContext munchkinContext) {
        super(munchkinContext);
        title = "Здоровенная бешенная цыпа";
        text = "Жареная цыплятина " +
                "это что-то." +
                "Получи 1 Уровень дополнительно" +
                "если завалил её огнём.";
        obscenityText = "Болезненная " +
                "поклёвка. " +
                "Теряешь " +
                "I Уровень.";
        rewardLevel = 1;
        rewardTreasure = 1;
        level = 2;
        this.iconPath = "/images/bigChip.png";
    }

    @Override
    public void obscenity(Fight fight, Player player) throws Exception {
        player.lvlUp(-1);
    }

}
